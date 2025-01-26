package org.example.rotation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Реализация интерфейса RotatingObject.
 * Представляет объект, который может вращаться вокруг оси.
 */
@Slf4j
@AllArgsConstructor
public class RotatingObjectImpl implements RotatingObject {
    private Integer direction; // Текущее направление объекта
    private final int angularVelocity; // Угловая скорость

    /**
     * Возвращает текущее направление объекта.
     *
     * @return текущее направление объекта
     */
    @Override
    public int getDirection() {
        if (direction == null) {
            throw new IllegalStateException("Direction is not set. Unable to get current direction.");
        }
        log.debug("Getting current direction: {}", direction);
        return direction;
    }

    /**
     * Устанавливает новое направление объекта.
     *
     * @param direction новое направление объекта
     */
    @Override
    public void setDirection(int direction) {
        if (direction < 0 || direction >= 360) {
            throw new IllegalArgumentException("Direction must be between 0 and 359 degrees.");
        }
        log.debug("Setting new direction: {}", direction);
        this.direction = direction;
    }

    /**
     * Возвращает угловую скорость объекта.
     *
     * @return угловая скорость объекта
     */
    @Override
    public int getAngularVelocity() {
        if (angularVelocity <= 0) {
            throw new IllegalStateException("Angular velocity must be positive.");
        }
        log.debug("Getting angular velocity: {}", angularVelocity);
        return angularVelocity;
    }

}
