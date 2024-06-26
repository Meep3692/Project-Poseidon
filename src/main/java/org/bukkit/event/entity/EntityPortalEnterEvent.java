package org.bukkit.event.entity;


import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Stores data for entities standing inside a portal block
 */
public class EntityPortalEnterEvent extends EntityEvent {

    private Location location;

    public EntityPortalEnterEvent(Entity entity, Location location) {
        super(entity);
        this.location = location;
    }

    /**
     * Gets the portal block the entity is touching
     *
     * @return The portal block the entity is touching
     */
    public Location getLocation() {
        return location;
    }
}
