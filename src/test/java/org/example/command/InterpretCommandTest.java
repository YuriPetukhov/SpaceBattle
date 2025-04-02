package org.example.command;

import org.example.dto.GameCommandRequest;
import org.example.ioc.IoCContainer;
import org.example.threads.EventLoop;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterpretCommandTest {

    @Mock
    private EventLoop eventLoop;

    @InjectMocks
    private InterpretCommand interpretCommand;

    @Test
    void testProcessCommand() {
        GameCommandRequest request = new GameCommandRequest();
        request.setGameId("game1");
        request.setObjectId("obj1");
        request.setOperationId("move");
        request.setArgs(Map.of("speed", 5));

        Command mockCommand = mock(Command.class);

        try (MockedStatic<IoCContainer> mockedIoC = mockStatic(IoCContainer.class)) {
            mockedIoC.when(() -> IoCContainer.Resolve(eq("move"), any(), any()))
                    .thenReturn(mockCommand);

            interpretCommand.process(request);

            verify(eventLoop).submit(mockCommand);
        }
    }

    @Test
    void testProcess_CommandNotFound() {
        GameCommandRequest request = new GameCommandRequest();
        request.setGameId("game1");
        request.setObjectId("obj1");
        request.setOperationId("unknownCommand");
        request.setArgs(Collections.emptyMap());

        try (MockedStatic<IoCContainer> mockedIoC = mockStatic(IoCContainer.class)) {
            mockedIoC.when(() -> IoCContainer.Resolve(eq("unknownCommand"), eq("obj1"), any()))
                    .thenReturn(null);

            interpretCommand.process(request);

            verify(eventLoop, never()).submit(any());
        }
    }

}


