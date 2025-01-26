package org.example.movement;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Point;
import org.example.entity.Vector;

/**
 * Реализация интерфейса MovingObject.
 * Представляет объект, который может перемещаться по игровому полю.
 */
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovingObjectImpl implements MovingObject {

    private Point location;
    private Vector velocity;

    /**
     * Возвращает текущее положение объекта.
     *
     * @return текущее положение объекта
     */
    @Override
    public Point getLocation() {
        if (location == null) {
            throw new IllegalStateException("Location is not set. Unable to get current location.");
        }
        log.debug("Getting current location: {}", location);
        return location;
    }

    /**
     * Возвращает текущий вектор скорости объекта.
     *
     * @return текущий вектор скорости
     */
    @Override
    public Vector getVelocity() {
        if (velocity == null) {
            throw new IllegalStateException("Velocity is not set. Unable to get current velocity");
        }
        log.debug("Getting current velocity: {}", velocity);
        return velocity;
    }

    /**
     * Устанавливает новое положение объекта.
     *
     * @param newValue новое положение объекта
     */
    @Override
    public void setLocation(Point newValue) {
        if (newValue == null) {
            throw new IllegalStateException("New location cannot be null");
        }
        log.debug("Setting new location: {}", newValue);
        this.location = newValue;
    }
}