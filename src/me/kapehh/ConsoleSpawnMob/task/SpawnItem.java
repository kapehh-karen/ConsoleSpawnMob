package me.kapehh.ConsoleSpawnMob.task;

import me.kapehh.ConsoleSpawnMob.mob.MobInfo;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

/**
 * Created by Karen on 08.11.2014.
 */
public class SpawnItem {
    MobInfo mobInfo;
    EntityType entityType;
    Location location;

    public SpawnItem(MobInfo mobInfo, EntityType entityType, Location location) {
        this.mobInfo = mobInfo;
        this.entityType = entityType;
        this.location = location;
    }

    public MobInfo getMobInfo() {
        return mobInfo;
    }

    public void setMobInfo(MobInfo mobInfo) {
        this.mobInfo = mobInfo;
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
                "mobInfo=" + mobInfo +
                ", entityType=" + entityType +
                ", location=" + location +
                '}';
    }
}
