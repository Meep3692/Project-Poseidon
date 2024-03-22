package org.bukkit.event.weather;

import org.bukkit.World;
import org.bukkit.event.Event;

/**
 * Represents a Weather-related event
 */
public class WeatherEvent extends Event {
    protected World world;

    public WeatherEvent(final World where) {
        super();
        world = where;
    }

    /**
     * Returns the World where this event is occurring
     *
     * @return World this event is occurring in
     */
    public final World getWorld() {
        return world;
    }
}
