package org.example.collision;

import org.example.movement.MovingObject;
import org.springframework.stereotype.Component;

@Component
public abstract class CollisionHandler {
    protected CollisionHandler next;

    public void setNext(CollisionHandler next) {
        this.next = next;
    }

    public void handle(MovingObject a, MovingObject b) {
        if (!process(a, b) && next != null) {
            next.handle(a, b);
        }
    }

    protected abstract boolean process(MovingObject a, MovingObject b);
}
