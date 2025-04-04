package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {

    private int x;
    private int y;

    public static Point plus(Point currentPoint, Vector velocity) {
        int newX = currentPoint.getX() + velocity.getVelocity().getX();
        int newY = currentPoint.getY() + velocity.getVelocity().getY();
        return new Point(newX, newY);
    }
}
