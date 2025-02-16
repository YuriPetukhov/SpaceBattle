package org.example.command;

import org.example.entity.FuelSystem;
import org.example.exceptions.handler.ExceptionHandler;
import org.example.exceptions.type.NotEnoughFuelException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BurnFuelCommandTest {

    @Test
    void testExecuteWhenFuelIsSufficient() throws Exception {
        FuelSystem fuelSystem = mock(FuelSystem.class);
        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(fuelSystem, 10, exceptionHandler);

        doNothing().when(fuelSystem).burnFuel(10);

        burnFuelCommand.execute();

        verify(fuelSystem, times(1)).burnFuel(10);
        verify(exceptionHandler, never()).handle(anyString(), any(Exception.class));
    }

    @Test
    void testExecuteWhenFuelIsNotSufficient() throws Exception {
        FuelSystem fuelSystem = mock(FuelSystem.class);
        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(fuelSystem, 10, exceptionHandler);

        doThrow(new NotEnoughFuelException("BurnFuelCommand", "Not enough fuel!"))
                .when(fuelSystem).burnFuel(10);

        burnFuelCommand.execute();

        verify(fuelSystem, times(1)).burnFuel(10);

        verify(exceptionHandler, times(1)).handle(eq("BurnFuelCommand"), any(NotEnoughFuelException.class));
    }

}