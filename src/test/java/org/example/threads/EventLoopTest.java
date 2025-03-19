package org.example.threads;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class EventLoopTest {
    /**
     * Тест: проверяет, что после команды `start()` поток действительно запущен.
     */
    @Test
    void testStartThread() throws InterruptedException {
        EventLoop eventLoop = new EventLoop(2); // Запуск 2 потоков
        CountDownLatch latch = new CountDownLatch(1);

        // Добавляем команду в очередь
        eventLoop.submit(latch::countDown); // Подтверждаем, что поток обработал команду

        eventLoop.start(); // Запуск EventLoop

        // Проверяем, что команда действительно выполнена
        assertTrue(latch.await(2, TimeUnit.SECONDS), "Поток не обработал команду за отведенное время");
    }

    /**
     * Тест: проверяет, что после `HardStopCommand` поток завершается.
     */
    @Test
    void testHardStop() throws InterruptedException {
        EventLoop eventLoop = new EventLoop(2); // Запуск 2 потоков
        CountDownLatch latch = new CountDownLatch(1);

        eventLoop.submit(() -> {
            try {
                Thread.sleep(500); // Имитация работы
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            latch.countDown(); // После завершения задачи уменьшаем latch
        });

        eventLoop.start(); // Запуск EventLoop

        // Даем время потоку начать выполнение задачи
        assertTrue(latch.await(1, TimeUnit.SECONDS), "Поток не начал выполнение задачи за отведенное время");

        // Теперь вызываем жесткую остановку, когда задача в процессе выполнения
        eventLoop.stop(); // Жесткая остановка

        // Проверяем, что задача завершена
        assertTrue(latch.await(1, TimeUnit.SECONDS), "Поток не завершил выполнение задачи за отведенное время");

        // Очистка очереди после завершения
        assertEquals(0, eventLoop.getQueue().size(), "Очередь должна быть пустой после остановки");
    }

    /**
     * Тест: проверяет, что после `SoftStopCommand` поток завершится после выполнения всех задач.
     */
    @Test
    void testSoftStop() throws InterruptedException {
        EventLoop eventLoop = new EventLoop(2);
        CountDownLatch tasksCompleted = new CountDownLatch(3);

        // Добавляем задачи в очередь
        eventLoop.submit(() -> {
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            tasksCompleted.countDown();
        });
        eventLoop.submit(() -> {
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            tasksCompleted.countDown();
        });
        eventLoop.submit(() -> {
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            tasksCompleted.countDown();
        });

        eventLoop.start(); // Запуск EventLoop

        eventLoop.softStop(); // Мягкая остановка, ждем завершения всех задач

        tasksCompleted.await(); // Ожидаем завершения всех задач

        // Проверяем, что все задачи завершены и очередь пуста
        assertEquals(0, eventLoop.getQueue().size(), "Очередь должна быть пустой после завершения Soft Stop");
    }
}
