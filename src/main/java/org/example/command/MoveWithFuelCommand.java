package org.example.command;

import org.example.movement.Move;
import org.springframework.beans.factory.annotation.Qualifier;

public class MoveWithFuelCommand implements Command {
    private final CheckFuelCommand checkFuel;
    private final @Qualifier("move") Move move;
    private final BurnFuelCommand burnFuel;

    public MoveWithFuelCommand(CheckFuelCommand checkFuel, Move move, BurnFuelCommand burnFuel) {
        this.checkFuel = checkFuel;
        this.move = move;
        this.burnFuel = burnFuel;
    }

    @Override
    public void execute() throws Exception {
        checkFuel.execute();
        move.execute();
        burnFuel.execute();
    }
}

