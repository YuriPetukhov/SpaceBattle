package org.example.controller;

import org.example.dto.GameCommandRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    private final KafkaTemplate<String, GameCommandRequest> kafkaTemplate;
    private static final String TOPIC = "game-commands";

    @PostMapping("/command")
    public ResponseEntity<String> receiveCommand(@RequestBody GameCommandRequest request) {
        kafkaTemplate.send(TOPIC, request);
        return ResponseEntity.ok("Команда принята и отправлена на обработку.");
    }
}
