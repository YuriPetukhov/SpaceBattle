package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.command.InterpretCommand;
import org.example.dto.GameCommandRequest;
import org.example.entity.Game;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class GameCommandConsumer {
    private final InterpretCommand interpretCommand;
    private final GameService gameService;

    public GameCommandConsumer(InterpretCommand interpretCommand, GameService gameService) {
        this.interpretCommand = interpretCommand;
        this.gameService = gameService;
    }

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

    @KafkaListener(topics = "test-commands", groupId = "test-group")
    public void listen(GameCommandRequest request) {
        log.info("Получено сообщение: {}", request);
        interpretCommand.process(request);
    }
}



