package org.example.command;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.GameCommandRequest;
import org.example.ioc.IoCContainer;
import org.example.threads.EventLoop;

@Slf4j
public class InterpretCommand {
    private final EventLoop eventLoop;

    public InterpretCommand(EventLoop eventLoop) {
        this.eventLoop = eventLoop;
    }

    public void process(GameCommandRequest request) {
        log.info("Создание команды для игры {}: объект {}, операция {}",
                request.getGameId(), request.getObjectId(), request.getOperationId());

        Command resolvedCommand = resolveCommand(request);

        if (resolvedCommand != null) {
            eventLoop.submit(resolvedCommand);
            log.info("Команда {} добавлена в очередь.", request.getOperationId());
        } else {
            log.warn("Команда с операцией {} не найдена.", request.getOperationId());
        }
    }

    private Command resolveCommand(GameCommandRequest request) {
        return (Command) IoCContainer.Resolve(request.getOperationId(), request.getObjectId(), request.getArgs());
    }
}
