package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Game;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameService {
    private final Map<String, Game> gameRepository;

    public Game getGameById(String gameId) {
        return gameRepository.get(gameId);
    }
}

