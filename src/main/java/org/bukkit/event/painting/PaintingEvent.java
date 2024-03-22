package org.bukkit.event.painting;

import org.bukkit.entity.Painting;
import org.bukkit.event.Event;

/**
 * Represents a painting-related event.
 */
public class PaintingEvent extends Event {

    protected Painting painting;

    protected PaintingEvent(final Painting painting) {
        super();
        this.painting = painting;
    }

    /**
     * Gets the painting involved in this event.
     *
     * @return the painting
     */
    public Painting getPainting() {
        return painting;
    }
}
