package org.example.command;

import org.example.entity.FuelSystem;
import org.example.exceptions.handler.ExceptionHandler;

public class BurnFuelCommand implements Command {

    private final FuelSystem fuelSystem;
    private final Integer fuelConsumed;
    private final ExceptionHandler exceptionHandler;

    public BurnFuelCommand(FuelSystem fuelSystem, Integer fuelConsumed, ExceptionHandler exceptionHandler) {
        this.fuelSystem = fuelSystem;
        this.fuelConsumed = fuelConsumed;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void execute() throws Exception {
        try {
            fuelSystem.burnFuel(fuelConsumed);
        } catch (Exception e) {
            exceptionHandler.handle(getClass().getSimpleName(), e);
        }
    }
}

