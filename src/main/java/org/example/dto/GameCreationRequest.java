package org.example.dto;

import java.util.List;

public record GameCreationRequest(List<String> playerUsernames) {}
