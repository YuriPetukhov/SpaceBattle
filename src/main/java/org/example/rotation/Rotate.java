package org.example.rotation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс, отвечающий за поворот объекта вокруг оси.
 * Вычисляет новое направление объекта на основе текущего направления и угловой скорости.
 */
@Slf4j
@RequiredArgsConstructor
public class Rotate {
    private final RotatingObject rotatingObject;

    /**
     * Выполняет поворот объекта.
     * Новое направление вычисляется как сумма текущего направления и угловой скорости.
     */
    public void execute() {
        log.info("Starting rotation for object: {}", rotatingObject);

        // Вычисляем новое направление объекта
        int newDirection = (rotatingObject.getDirection() + rotatingObject.getAngularVelocity()) % 360;
        log.debug("New direction calculated: {}", newDirection);

        // Устанавливаем новое направление объекта
        rotatingObject.setDirection(newDirection);
        log.info("Object rotated to new direction: {}", newDirection);
    }
}