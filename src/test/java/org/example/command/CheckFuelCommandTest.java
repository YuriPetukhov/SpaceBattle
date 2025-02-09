package org.example.command;

import org.example.entity.FuelSystem;
import org.example.exceptions.type.NotEnoughFuelException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckFuelCommandTest {

    @Test
    void testExecuteWhenFuelIsSufficient() throws Exception {
        FuelSystem fuelSystem = mock(FuelSystem.class);
        when(fuelSystem.hasEnoughFuel(anyInt())).thenReturn(true);
        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(fuelSystem, 10);

        checkFuelCommand.execute();
    }

    @Test
    void testExecuteWhenFuelIsNotSufficient() {
        FuelSystem fuelSystem = mock(FuelSystem.class);
        when(fuelSystem.hasEnoughFuel(anyInt())).thenReturn(false);
        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(fuelSystem, 10);

        NotEnoughFuelException exception = assertThrows(NotEnoughFuelException.class, checkFuelCommand::execute);

        assertEquals("Not enough fuel!", exception.getMessage());

    }



}