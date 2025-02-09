package org.example.command;

import lombok.RequiredArgsConstructor;
import org.example.entity.FuelSystem;
import org.example.exceptions.type.NotEnoughFuelException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BurnFuelCommand implements Command {
    private final FuelSystem fuelSystem;
    private final int fuelConsumed;

    @Override
    public void execute() throws NotEnoughFuelException {
        fuelSystem.burnFuel(fuelConsumed, this);
    }
}

