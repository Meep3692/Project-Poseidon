package org.bukkit.event.vehicle;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.Cancellable;

/**
 * Raised when a living entity exits a vehicle.
 */
public class VehicleExitEvent extends VehicleEvent implements Cancellable {
    private boolean cancelled;
    private LivingEntity exited;

    public VehicleExitEvent(Vehicle vehicle, LivingEntity exited) {
        super(vehicle);
        this.exited = exited;
    }

    /**
     * Get the living entity that exited the vehicle.
     *
     * @return
     */
    public LivingEntity getExited() {
        return exited;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
