package org.example.threads;

import org.example.command.Command;
import org.example.command.MoveToCommand;
import org.example.command.RunCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class CommandProcessingThreadTest {

    private BlockingQueue<Command> mainQueue;
    private BlockingQueue<Command> secondaryQueue;
    EventLoop mockLoop = Mockito.mock(EventLoop.class);

    @BeforeEach
    void setUp() {
        mainQueue = new LinkedBlockingQueue<>();
        secondaryQueue = new LinkedBlockingQueue<>();
        DummyCommand.reset();
    }

    @Test
    void testHardStopTerminatesThread() throws InterruptedException {
        CommandProcessingThread thread = new CommandProcessingThread(mainQueue, secondaryQueue);
        thread.start();

        mainQueue.put(new HardStopCommand(mockLoop));
        thread.join(1000);

        assertFalse(thread.isAlive(), "Поток должен завершиться после HardStopCommand");
    }

    @Test
    void testTransitionToMoveToState() throws InterruptedException {
        CommandProcessingThread thread = new CommandProcessingThread(mainQueue, secondaryQueue);
        thread.start();

        mainQueue.put(new MoveToCommand());
        mainQueue.put(new DummyCommand());
        mainQueue.put(new HardStopCommand(mockLoop));

        thread.join(1000);

        assertEquals(1, secondaryQueue.size(), "Команда должна быть перенаправлена в другую очередь");
    }

    @Test
    void testTransitionBackToNormalState() throws InterruptedException {
        CommandProcessingThread thread = new CommandProcessingThread(mainQueue, secondaryQueue);
        thread.start();

        mainQueue.put(new MoveToCommand());
        mainQueue.put(new RunCommand());
        mainQueue.put(new DummyCommand());
        mainQueue.put(new HardStopCommand(mockLoop));

        thread.join(1000);

        assertTrue(DummyCommand.wasExecuted(), "Команда должна была выполниться в NormalState");
    }

    public static class DummyCommand implements Command {
        private static boolean executed = false;

        @Override
        public void execute() {
            executed = true;
        }

        public static boolean wasExecuted() {
            return executed;
        }

        public static void reset() {
            executed = false;
        }
    }
}