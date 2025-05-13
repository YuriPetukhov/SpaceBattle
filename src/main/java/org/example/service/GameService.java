package org.example.service;

import org.example.entity.Game;

import java.util.Map;

public class GameService {
    private final Map<String, Game> gameRepository;

    public GameService(Map<String, Game> gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game getGameById(String gameId) {
        return gameRepository.get(gameId);
    }
}

