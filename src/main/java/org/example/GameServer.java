package org.example;

import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.entity.Velocity;
import org.example.movement.Move;
import org.example.movement.MovingObjectImpl;

public class GameServer
{
    public static void main( String[] args )
    {
        Point initialLocation = new Point(10, 3);
        Velocity velocity = new Velocity();
        velocity.setX(-7);
        velocity.setY(3);
        Vector vector = new Vector();
        vector.setVelocity(velocity);

        MovingObjectImpl movingObject = new MovingObjectImpl(initialLocation, vector);
        Move move = new Move(movingObject);
        move.execute();
    }
}
