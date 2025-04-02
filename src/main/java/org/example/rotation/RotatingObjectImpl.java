package org.example.rotation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Angle;
import org.example.exceptions.type.InvalidDenominatorException;
import org.springframework.stereotype.Component;

/**
 * Класс, представляющий вращающийся объект.
 */
@Slf4j
@AllArgsConstructor
public class RotatingObjectImpl implements RotatingObject {
    private Angle angle;
    private final Angle angularVelocity;

    @Override
    public Angle getAngle() {
        return angle;
    }

    @Override
    public void setAngle(Angle angle) {
        this.angle = angle;
    }

    @Override
    public Angle getAngularVelocity() {
        return angularVelocity;
    }

    /**
     * Обновляет угол, прибавляя угловую скорость.
     */
    public void rotate() throws InvalidDenominatorException {
        angle.add(angularVelocity);
        log.info("New direction: {}", angle);
    }
}
