package org.example.command;

import org.example.entity.FuelSystem;
import org.example.exceptions.type.NotEnoughFuelException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BurnFuelCommandTest {

    @Test
    void testExecuteWhenFuelIsSufficient() throws NotEnoughFuelException {
        FuelSystem fuelSystem = mock(FuelSystem.class);
        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(fuelSystem, 10);

        doNothing().when(fuelSystem).burnFuel(10, burnFuelCommand);

        burnFuelCommand.execute();

        verify(fuelSystem, times(1)).burnFuel(10, burnFuelCommand);
    }

    @Test
    void testExecuteWhenFuelIsNotSufficient() throws NotEnoughFuelException {
        FuelSystem fuelSystem = mock(FuelSystem.class);
        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(fuelSystem, 10);

        doThrow(new NotEnoughFuelException(burnFuelCommand, new Exception("Not enough fuel!")))
                .when(fuelSystem).burnFuel(10, burnFuelCommand);

        NotEnoughFuelException exception = assertThrows(NotEnoughFuelException.class, burnFuelCommand::execute);
        assertNotNull(exception.getCause());
        assertEquals("Not enough fuel!", exception.getCause().getMessage());
    }

}