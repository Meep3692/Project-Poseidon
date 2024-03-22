package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;

/**
 * Raised when a vehicle collides.
 */
public class VehicleCollisionEvent extends VehicleEvent {
    public VehicleCollisionEvent(Vehicle vehicle) {
        super(vehicle);
    }
}
