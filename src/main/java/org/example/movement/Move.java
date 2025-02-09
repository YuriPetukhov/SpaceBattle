package org.example.movement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.entity.Point;
import org.example.exceptions.handler.ExceptionHandler;
import org.springframework.stereotype.Service;

/**
 * Класс, отвечающий за перемещение объекта на игровом поле.
 * Вычисляет новое положение объекта на основе его текущего положения и скорости.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class Move implements Command {
    private final MovingObject movingObject;
    private final ExceptionHandler exceptionHandler;

    @Override
    public void execute() throws Exception {
        try {
            Point newLocation = Point.plus(movingObject.getLocation(), movingObject.getVelocity());
            movingObject.setLocation(newLocation);
        } catch (Exception e) {
            exceptionHandler.handle(this, e);
        }
    }
}