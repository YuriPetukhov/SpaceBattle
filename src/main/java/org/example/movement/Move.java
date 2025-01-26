package org.example.movement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Point;

import static org.example.entity.Point.plus;

/**
 * Класс, отвечающий за перемещение объекта на игровом поле.
 * Вычисляет новое положение объекта на основе его текущего положения и скорости.
 */
@Slf4j
@RequiredArgsConstructor
public class Move {
    private final MovingObject movingObject;

    /**
     * Выполняет перемещение объекта.
     * Новое положение объекта вычисляется как сумма текущего положения и вектора скорости.
     */
    public void execute() {
        log.info("Starting movement for object: {}", movingObject);

        // Вычисляем новое положение объекта
        Point newLocation = plus(movingObject.getLocation(), movingObject.getVelocity());
        log.debug("New location calculated: {}", newLocation);

        // Устанавливаем новое положение объекта
        movingObject.setLocation(newLocation);
        log.info("Object moved to new location: {}", newLocation);
    }
}