package org.example.collision;

import org.example.entity.Point;
import org.example.entity.Vector;
import org.example.entity.Velocity;
import org.example.mock.MockMovingObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NeighborhoodSystemTest {

    @Test
    void testObjectAddedToMultipleNeighborhoods() {
        NeighborhoodSystem system = new NeighborhoodSystem(10, 2);
        MockMovingObject obj1 = new MockMovingObject(new Point(5, 5), new Vector(new Velocity(5, 5)));
        MockMovingObject obj2 = new MockMovingObject(new Point(5, 5), new Vector(new Velocity(5, 5)));

        system.updateObject(obj1);
        system.updateObject(obj2);

        system.checkAllCollisions();

        assertTrue(obj1.getNeighbors().contains(obj2), "Objects must be neighbors after collision detection");
        assertTrue(obj2.getNeighbors().contains(obj1), "Objects must be neighbors after collision detection");
    }

    @Test
    void testObjectIsAddedToOverlappingNeighborhoods() {
        NeighborhoodSystem system = new NeighborhoodSystem(10, 2);
        MockMovingObject obj = new MockMovingObject(new Point(5, 5), new Vector(new Velocity(1, 1)));

        system.updateObject(obj);

        long count = system.getGrids().stream()
                .filter(grid -> grid.getObjects().contains(obj))
                .count();

        assertTrue(count > 1, "Object should be in multiple neighborhoods due to overlap");
    }


}