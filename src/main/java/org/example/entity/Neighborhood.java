package org.example.entity;

import lombok.Data;
import lombok.Getter;
import org.example.collision.CollisionProcessor;
import org.example.movement.MovingObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@Data
@Getter
public class Neighborhood {
    private final int size;
    private final int offsetX, offsetY;
    private final Map<String, Set<MovingObject>> zones = new ConcurrentHashMap<>();
    private final Map<MovingObject, String> objectZoneMap = new ConcurrentHashMap<>();
    private final CollisionProcessor collisionProcessor;

    public Neighborhood(int size, int offsetX, int offsetY) {
        this.size = size;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.collisionProcessor = new CollisionProcessor();
    }

    public void updateObject(MovingObject obj) {
        String newZone = getZoneId(obj);
        String oldZone = objectZoneMap.get(obj);
        if (!newZone.equals(oldZone)) {
            removeFromZone(obj, oldZone);
            addToZone(obj, newZone);
            objectZoneMap.put(obj, newZone);
        }
    }

    private void removeFromZone(MovingObject obj, String zone) {
        if (zone != null) {
            zones.getOrDefault(zone, Collections.emptySet()).remove(obj);
        }
    }

    private void addToZone(MovingObject obj, String zone) {
        zones.computeIfAbsent(zone, k -> ConcurrentHashMap.newKeySet()).add(obj);
    }

    private String getZoneId(MovingObject obj) {
        try {
            int x = (obj.getLocation().getX() - offsetX) / size;
            int y = (obj.getLocation().getY() - offsetY) / size;
            return x + "_" + y;
        } catch (Exception e) {
            return "unknown";
        }
    }

    public void checkCollisions() {
        zones.values().forEach(zone -> {
            List<MovingObject> objects = new ArrayList<>(zone);
            for (int i = 0; i < objects.size(); i++) {
                for (int j = i + 1; j < objects.size(); j++) {
                    collisionProcessor.processCollision(objects.get(i), objects.get(j));
                }
            }
        });
    }

    public Collection<MovingObject> getObjects() {
        List<MovingObject> allObjects = new ArrayList<>();
        zones.values().forEach(allObjects::addAll);
        return allObjects;
    }
}
