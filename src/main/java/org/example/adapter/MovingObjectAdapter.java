package org.example.adapter;

import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.ioc.IoCContainer;
import org.example.movement.MovingObject;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;
import org.springframework.stereotype.Component;

@Component
public class MovingObjectAdapter implements MovingObject {
    private final IUObject obj;

    public MovingObjectAdapter(IUObject obj) {
        this.obj = obj;
    }

    @Override
    public Point getLocation() throws LocationNotSetException {
        return (Point) IoCContainer.Resolve("MovingObject:getLocation", obj);
    }

    @Override
    public Vector getVelocity() throws VelocityNotSetException {
        return (Vector) IoCContainer.Resolve("MovingObject:getVelocity", obj);
    }

    @Override
    public void setLocation(Point newValue) throws VelocityNotSetException {
        IoCContainer.Resolve("MovingObject:setLocation", obj, newValue);
    }
}