package org.example.collision;

import lombok.Getter;
import org.example.entity.Neighborhood;
import org.example.movement.MovingObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class NeighborhoodSystem {
    private final List<Neighborhood> grids = new ArrayList<>();
    private final Map<String, Set<MovingObject>> zones = new ConcurrentHashMap<>();

    public NeighborhoodSystem(@Value("${neighborhood.grid-size}") int gridSize,
                              @Value("${neighborhood.grid-count}") int gridCount) {
        for (int i = 0; i < gridCount; i++) {
            int offsetX = (i % 2) * gridSize / 2;
            int offsetY = (i / 2) * gridSize / 2;
            grids.add(new Neighborhood(gridSize, offsetX, offsetY));
        }
    }

    public void updateObject(MovingObject obj) {
        grids.forEach(grid -> grid.updateObject(obj));
    }

    public void checkAllCollisions() {
        grids.forEach(Neighborhood::checkCollisions);
    }

    public Collection<MovingObject> getObjects() {
        List<MovingObject> allObjects = new ArrayList<>();
        grids.forEach(grid -> allObjects.addAll(grid.getObjects()));
        return allObjects;
    }


}
