package org.example.service;

import org.example.command.InterpretCommand;
import org.example.dto.GameCommandRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.Map;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(topics = "game-commands", partitions = 1)
class GameCommandConsumerTest {

    @Autowired
    private KafkaTemplate<String, GameCommandRequest> kafkaTemplate;

    @SpyBean
    private GameCommandConsumer gameCommandConsumer;

    @MockBean
    private InterpretCommand interpretCommand;

    @AfterEach
    void cleanup() {
        System.gc();
    }

    @Test
    void testKafkaListenerReceivesMessage() throws Exception {
        GameCommandRequest request = new GameCommandRequest("game1", "obj1", "move", Map.of("speed", 5));
        kafkaTemplate.send("game-commands", request).get();

        verify(interpretCommand, timeout(2000)).process(request);
    }
}





