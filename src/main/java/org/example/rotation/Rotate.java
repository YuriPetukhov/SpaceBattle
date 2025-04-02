package org.example.rotation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.command.Command;
import org.example.exceptions.handler.ExceptionHandler;
import org.springframework.stereotype.Service;

/**
 * Класс, отвечающий за поворот объекта вокруг оси.
 * Вычисляет новое направление объекта на основе текущего направления и угловой скорости.
 */
@Slf4j
@RequiredArgsConstructor
public class Rotate implements Command {
    private final RotatingObject rotatingObject;
    private final ExceptionHandler exceptionHandler;

    @Override
    public void execute() throws Exception {
        try {
            rotatingObject.rotate();
        } catch (Exception e) {
            exceptionHandler.handle(getClass().getSimpleName(), e);
        }
    }
}
