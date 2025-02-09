package org.example.command;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MacroCommandTest {

    @Test
    public void testExecute_AllCommandsRunSuccessfully() throws Exception {
        Command command1 = mock(Command.class);
        Command command2 = mock(Command.class);
        Command command3 = mock(Command.class);

        MacroCommand macroCommand = new MacroCommand(List.of(command1, command2, command3));

        macroCommand.execute();

        verify(command1, times(1)).execute();
        verify(command2, times(1)).execute();
        verify(command3, times(1)).execute();
    }

    @Test
    public void testExecute_WhenCommandThrowsException_ShouldStopExecution() throws Exception {
        Command command1 = mock(Command.class);
        Command command2 = mock(Command.class);
        Command command3 = mock(Command.class);

        doThrow(new Exception("Ошибка в команде 2")).when(command2).execute();

        MacroCommand macroCommand = new MacroCommand(List.of(command1, command2, command3));

        assertThrows(Exception.class, macroCommand::execute);

        verify(command1, times(1)).execute();

        verify(command2, times(1)).execute();

        verify(command3, never()).execute();
    }

}