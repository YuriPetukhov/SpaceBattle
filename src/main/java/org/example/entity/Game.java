package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.example.command.Command;
import org.example.enums.GameState;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Data
@AllArgsConstructor
public class Game {
    private final String gameId;
    private final Queue<Command> commandQueue;
    private GameState gameState;

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void addCommandToQueue(Command command) {
        commandQueue.offer(command);
    }

    public void start() {
        this.gameState = GameState.RUNNING;
    }

    public void end() {
        this.gameState = GameState.ENDED;
    }
}


