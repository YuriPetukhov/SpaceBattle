package org.example.command;

import static org.mockito.Mockito.*;

import org.example.entity.Angle;
import org.example.entity.Velocity;
import org.example.exceptions.handler.ExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class RotateAndUpdateVelocityCommandTest {

    @Mock
    private Velocity velocity;

    @Mock
    private Angle angle;

    @Mock
    private ExceptionHandler exceptionHandler;
    @InjectMocks
    private RotateAndUpdateVelocityCommand command;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute_Rotation() throws Exception {
        when(velocity.getX()).thenReturn(10);
        when(velocity.getY()).thenReturn(0);
        when(angle.getD()).thenReturn(1);
        when(angle.getN()).thenReturn(1);

        command.execute();

        verify(velocity).setX(10);
        verify(velocity).setY(0);
    }

    @Test
    public void testExecute_RotationWithNegativeAngle() throws Exception {
        when(velocity.getX()).thenReturn(10);
        when(velocity.getY()).thenReturn(0);
        when(angle.getD()).thenReturn(1);
        when(angle.getN()).thenReturn(2);

        command.execute();

        verify(velocity).setX(-10);
        verify(velocity).setY(0);
    }

    @Test
    void testExecute_whenExceptionOccurs() throws Exception {
        when(velocity.getX()).thenReturn(10);
        when(velocity.getY()).thenReturn(0);

        when(angle.getD()).thenReturn(1);
        when(angle.getN()).thenReturn(2);

        doThrow(new IllegalArgumentException("X velocity cannot be negative"))
                .when(velocity).setX(-10);

        command.execute();

        verify(exceptionHandler, times(1)).handle(eq(command.getClass().getSimpleName()), any(IllegalArgumentException.class));
    }


}
