package org.example.command;

import lombok.RequiredArgsConstructor;
import org.example.entity.FuelSystem;
import org.example.exceptions.handler.ExceptionHandler;
import org.example.exceptions.type.NotEnoughFuelException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BurnFuelCommand implements Command {

    private final FuelSystem fuelSystem;
    private final int fuelConsumed;
    private final ExceptionHandler exceptionHandler;

    @Override
    public void execute() throws Exception {
        try {
            fuelSystem.burnFuel(fuelConsumed);
        } catch (Exception e) {
            exceptionHandler.handle(getClass().getSimpleName(), e);
        }
    }
}

