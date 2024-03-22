package org.bukkit.event.vehicle;

import org.bukkit.block.Block;
import org.bukkit.entity.Vehicle;

/**
 * Raised when a vehicle collides with a block.
 */
public class VehicleBlockCollisionEvent extends VehicleCollisionEvent {
    private Block block;

    public VehicleBlockCollisionEvent(Vehicle vehicle, Block block) {
        super(vehicle);
        this.block = block;
    }

    /**
     * Gets the block the vehicle collided with
     *
     * @return the block the vehicle collided with
     */
    public Block getBlock() {
        return block;
    }
}
