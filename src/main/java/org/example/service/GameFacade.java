package org.example.service;

import org.example.client.AuthClient;
import org.example.dto.GameCreationRequest;
import org.example.dto.GameIdResponse;
import org.example.dto.TokenRequest;
import org.example.dto.TokenResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public class GameFacade {
    private final AuthClient authClient;

    public GameFacade(AuthClient authClient) {
        this.authClient = authClient;
    }

    public GameIdResponse createGame(List<String> players) {
        GameCreationRequest request = new GameCreationRequest(players);
        return authClient.createGame(request);
    }

    public TokenResponse getToken(String username, UUID gameId) {
        TokenRequest request = new TokenRequest(username, gameId);
        return authClient.getToken(request);
    }
}

