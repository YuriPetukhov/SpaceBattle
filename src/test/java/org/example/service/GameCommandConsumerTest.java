package org.example.service;

import org.example.command.InterpretCommand;
import org.example.dto.GameCommandRequest;
import org.example.entity.Game;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.Map;

import static org.mockito.Mockito.*;

@SpringBootTest
@EmbeddedKafka(topics = "game-commands", partitions = 1)
class GameCommandConsumerTest {

    @Autowired
    private KafkaTemplate<String, GameCommandRequest> kafkaTemplate;

    @SpyBean
    private GameCommandConsumer gameCommandConsumer;

    @MockBean
    private InterpretCommand interpretCommand;

    @MockBean
    private GameService gameService;

    @Test
    void testKafkaListenerReceivesMessage() throws Exception {
        GameCommandRequest request = new GameCommandRequest("game1", "obj1", "move", Map.of("speed", 5));
        kafkaTemplate.send("test-commands", request).get();

        verify(interpretCommand, timeout(2000)).process(request);
    }

    @Test
    void testWhenGameNotFound() throws Exception {
        when(gameService.getGameById("unknown-game")).thenReturn(null);

        GameCommandRequest request = new GameCommandRequest("unknown-game", "obj1", "move", Map.of());

        kafkaTemplate.send("game-commands", request).get();

        verify(interpretCommand, timeout(2000).times(0)).process(request);
    }

    @Test
    void testWhenInterpretCommandFails() throws Exception {
        Game mockGame = new Game();
        when(gameService.getGameById("game1")).thenReturn(mockGame);
        doThrow(new RuntimeException("Processing error"))
                .when(interpretCommand).process(any());

        GameCommandRequest request = new GameCommandRequest("game1", "obj1", "move", Map.of());

        kafkaTemplate.send("game-commands", request).get();

        verify(interpretCommand, timeout(2000).times(1)).process(request);
    }

    @Test
    void testInvalidCommandData() throws Exception {
        Game mockGame = new Game();
        when(gameService.getGameById("game1")).thenReturn(mockGame);

        GameCommandRequest invalidRequest = new GameCommandRequest(null, null, null, null);
        kafkaTemplate.send("game-commands", invalidRequest).get();

        verify(interpretCommand, timeout(2000).times(0)).process(any());
    }
}
