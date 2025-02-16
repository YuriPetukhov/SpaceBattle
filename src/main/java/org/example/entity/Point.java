package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {

    private int x;
    private int y;

    public static Point plus(Point currentPoint, Vector vector, Angle angle) {
        Velocity velocity = vector.getVelocity();

        double angleInRadians = (2 * Math.PI * angle.getD()) / angle.getN();

        int newX = (int) (velocity.getX() * Math.cos(angleInRadians) - velocity.getY() * Math.sin(angleInRadians));
        int newY = (int) (velocity.getX() * Math.sin(angleInRadians) + velocity.getY() * Math.cos(angleInRadians));

        int newLocationX = currentPoint.getX() + newX;
        int newLocationY = currentPoint.getY() + newY;

        return new Point(newLocationX, newLocationY);
    }
}
