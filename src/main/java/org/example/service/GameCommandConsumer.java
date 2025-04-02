package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.InterpretCommand;
import org.example.dto.GameCommandRequest;
import org.example.entity.Game;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameCommandConsumer {
    private final InterpretCommand interpretCommand;
    private final GameService gameService;

    @KafkaListener(topics = "game-commands", groupId = "game-server-group")
    public void consumeCommand(GameCommandRequest request) {
        log.info("Получена команда: {}", request);

        Game game = gameService.getGameById(request.getGameId());
        if (game == null) {
            log.error("Игра с ID {} не найдена.", request.getGameId());
            return;
        }

        interpretCommand.process(request);
    }

    @KafkaListener(topics = "game-commands", groupId = "test-group")
    public void listen(GameCommandRequest request) {
        log.info("Получено сообщение: {}", request);
        interpretCommand.process(request);
    }
}



