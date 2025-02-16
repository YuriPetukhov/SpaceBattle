package org.example.command;

import org.example.movement.Move;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoveWithFuelCommandTest {

    @Test
    public void testExecute_SuccessfulMovement() throws Exception {
        CheckFuelCommand checkFuel = mock(CheckFuelCommand.class);
        Move move = mock(Move.class);
        BurnFuelCommand burnFuel = mock(BurnFuelCommand.class);

        MoveWithFuelCommand command = new MoveWithFuelCommand(checkFuel, move, burnFuel);

        command.execute();

        verify(checkFuel, times(1)).execute();
        verify(move, times(1)).execute();
        verify(burnFuel, times(1)).execute();
    }

    @Test
    public void testExecute_NoFuel_ShouldThrowException() throws Exception {
        CheckFuelCommand checkFuel = mock(CheckFuelCommand.class);
        Move move = mock(Move.class);
        BurnFuelCommand burnFuel = mock(BurnFuelCommand.class);

        doThrow(new Exception("Недостаточно топлива")).when(checkFuel).execute();

        MoveWithFuelCommand command = new MoveWithFuelCommand(checkFuel, move, burnFuel);

        assertThrows(Exception.class, command::execute);

        verify(move, never()).execute();
        verify(burnFuel, never()).execute();
    }

}