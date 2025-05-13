package org.example.utils;

import org.example.movement.MovingObject;
import org.example.entity.Point;
import org.springframework.stereotype.Component;

@Component
public class CollisionDetector {
    public static boolean checkCollision(MovingObject a, MovingObject b) {
        try {
            Point p1 = a.getLocation();
            Point p2 = b.getLocation();
            int dx = p1.getX() - p2.getX();
            int dy = p1.getY() - p2.getY();
            return dx * dx + dy * dy < 100;
        } catch (Exception e) {
            return false;
        }
    }
}
