package org.bukkit.event.world;

import org.bukkit.World;

/**
 * Called when a World is loaded
 */
public class WorldLoadEvent extends WorldEvent {
    public WorldLoadEvent(World world) {
        super(world);
    }
}
