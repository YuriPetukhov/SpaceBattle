package org.example.client;

import org.example.dto.GameCreationRequest;
import org.example.dto.GameIdResponse;
import org.example.dto.TokenRequest;
import org.example.dto.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "${auth.service.url}")
public interface AuthClient {

    @PostMapping("/api/auth/create-game")
    GameIdResponse createGame(@RequestBody GameCreationRequest request);

    @PostMapping("/api/auth/token")
    TokenResponse getToken(@RequestBody TokenRequest request);
}

