package org.example.movement;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Angle;
import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.entity.Velocity;
import org.example.exceptions.type.InvalidDenominatorException;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Реализация интерфейса MovingObject.
 * Представляет объект, который может перемещаться по игровому полю.
 */
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Primary
public class MovingObjectImpl implements MovingObject {

    private Point location;
    private Vector velocity;
    private Angle angle;

    /**
     * Возвращает текущее положение объекта.
     *
     * @return текущее положение объекта
     */
    @Override
    public Point getLocation() throws LocationNotSetException {
        if (location == null) {
            throw new LocationNotSetException(getClass().getSimpleName(), "Location is not set. Unable to get current location.");
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
    public Vector getVelocity() throws VelocityNotSetException {
        if (velocity == null) {
            throw new VelocityNotSetException(getClass().getSimpleName(), "Velocity is not set. Unable to get current velocity");
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
    public void setLocation(Point newValue) throws VelocityNotSetException {
        if (newValue == null) {
            throw new VelocityNotSetException(getClass().getSimpleName(), "New location cannot be null");
        }
        log.debug("Setting new location: {}", newValue);
        this.location = newValue;
    }
}