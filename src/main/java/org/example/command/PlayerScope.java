package org.example.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
public class PlayerScope {
    private String currentPlayerId;

    public void setCurrentPlayer(String playerId) {
        this.currentPlayerId = playerId;
    }

    public boolean isOwnedByCurrentPlayer(String ownerId) {
        return currentPlayerId != null && currentPlayerId.equals(ownerId);
    }
}
