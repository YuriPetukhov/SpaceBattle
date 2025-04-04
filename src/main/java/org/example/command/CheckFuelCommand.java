package org.example.command;

import lombok.RequiredArgsConstructor;
import org.example.entity.FuelSystem;
import org.example.exceptions.type.NotEnoughFuelException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class CheckFuelCommand implements Command {
    private final FuelSystem fuelSystem;
    private final int requiredFuel;

    @Override
    public void execute() throws NotEnoughFuelException {
        if (!fuelSystem.hasEnoughFuel(requiredFuel)) {
            throw new NotEnoughFuelException(getClass().getSimpleName(), "Not enough fuel!");
        }
    }
}

