package org.example.adapter;

import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.ioc.IoCContainer;
import org.example.movement.MovingObject;
import org.example.exceptions.type.LocationNotSetException;
import org.example.exceptions.type.VelocityNotSetException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MovingObjectAdapter implements MovingObject {
    private final IUObject obj;

    private final Set<MovingObject> neighbors = new HashSet<>();

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

    @Override
    public boolean isExplosive() {

        return false;
    }

    @Override
    public boolean isFragile() {
        return false;
    }

    @Override
    public boolean isElastic() {
        return false;
    }

    @Override
    public void addNeighbor(MovingObject other) {
        neighbors.add(other);
    }

    @Override
    public void clearNeighbors() {
        neighbors.clear();
    }

    @Override
    public void bounceFrom(MovingObject other) {
        System.out.println("Object " + this + " bounces from " + other);
    }

    @Override
    public void destroy() {
        System.out.println("Object " + this + " is destroyed");
    }
}