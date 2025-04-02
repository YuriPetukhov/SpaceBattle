package org.example.command;

import lombok.RequiredArgsConstructor;
import org.example.movement.Move;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class MoveWithFuelCommand implements Command {
    private final CheckFuelCommand checkFuel;
    private final @Qualifier("move") Move move;
    private final BurnFuelCommand burnFuel;

    @Override
    public void execute() throws Exception {
        checkFuel.execute();
        move.execute();
        burnFuel.execute();
    }
}

