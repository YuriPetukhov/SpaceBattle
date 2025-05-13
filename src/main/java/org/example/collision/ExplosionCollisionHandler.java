package org.example.collision;

import org.example.movement.MovingObject;
import org.springframework.stereotype.Component;

@Component
public class ExplosionCollisionHandler extends CollisionHandler {
    @Override
    protected boolean process(MovingObject a, MovingObject b) {
        if (a.isExplosive() || b.isExplosive()) {
            a.destroy();
            b.destroy();
            return true;
        }
        return false;
    }
}

