package me.kapehh.ConsoleSpawnMob.task;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

/**
 * Created by Karen on 08.11.2014.
 */
public class SpawnItem {
    EntityType entityType;
    Location location;

    public SpawnItem(Location location, EntityType entityType) {
        this.location = location;
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "SpawnItem{" +
                "entityType=" + entityType +
                ", location=" + location +
                '}';
    }
}
