package org.example.collision;

import org.example.movement.MovingObject;
import org.example.utils.CollisionDetector;
import org.springframework.stereotype.Component;

@Component
public class NeighborCollisionHandler extends CollisionHandler {
    @Override
    protected boolean process(MovingObject a, MovingObject b) {
        a.addNeighbor(b);
        b.addNeighbor(a);
        return true;
    }
}

