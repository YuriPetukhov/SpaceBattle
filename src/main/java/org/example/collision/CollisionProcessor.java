package org.example.collision;

import org.example.movement.MovingObject;
import org.springframework.stereotype.Component;

@Component
public class CollisionProcessor {
    private final CollisionHandler handlerChain;

    public CollisionProcessor() {
        CollisionHandler explosion = new ExplosionCollisionHandler();
        CollisionHandler bounce = new BounceCollisionHandler();
        CollisionHandler neighbor = new NeighborCollisionHandler();

        explosion.setNext(bounce);
        bounce.setNext(neighbor);

        this.handlerChain = explosion;
    }

    public void processCollision(MovingObject a, MovingObject b) {
        handlerChain.handle(a, b);
    }
}

