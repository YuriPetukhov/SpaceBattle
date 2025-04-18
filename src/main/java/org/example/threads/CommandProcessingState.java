package org.example.threads;

import org.example.command.Command;
import java.util.concurrent.BlockingQueue;

public interface CommandProcessingState {
    /**
     * Обрабатывает команду и возвращает следующее состояние.
     *
     * @param command     Команда из очереди
     * @param mainQueue   Основная очередь (если требуется)
     * @param targetQueue Целевая очередь для перенаправления команды
     * @return Следующее состояние, или null, если поток должен завершиться
     */
    CommandProcessingState handle(Command command, BlockingQueue<Command> mainQueue,
                                  BlockingQueue<Command> targetQueue) throws Exception;
}
