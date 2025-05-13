package org.example.command;

import org.example.entity.FuelSystem;
import org.example.exceptions.type.NotEnoughFuelException;


public class CheckFuelCommand implements Command {
    private final FuelSystem fuelSystem;
    private final int requiredFuel;

    public CheckFuelCommand(FuelSystem fuelSystem, int requiredFuel) {
        this.fuelSystem = fuelSystem;
        this.requiredFuel = requiredFuel;
    }

    @Override
    public void execute() throws NotEnoughFuelException {
        if (!fuelSystem.hasEnoughFuel(requiredFuel)) {
            throw new NotEnoughFuelException(getClass().getSimpleName(), "Not enough fuel!");
        }
    }
}

