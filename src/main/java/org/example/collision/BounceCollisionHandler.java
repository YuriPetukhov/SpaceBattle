package org.example.collision;

import org.example.movement.MovingObject;
import org.springframework.stereotype.Component;

@Component
public class BounceCollisionHandler extends CollisionHandler {
    @Override
    protected boolean process(MovingObject a, MovingObject b) {
        if (a.isElastic() || b.isElastic()) {
            a.bounceFrom(b);
            b.bounceFrom(a);
            return true;
        }
        return false;
    }
}

