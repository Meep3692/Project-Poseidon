package org.bukkit.event.player;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Thrown when a player picks an item up from the ground
 */
public class PlayerPickupItemEvent extends PlayerEvent implements Cancellable {
    private final Item item;
    private boolean cancel = false;
    private int remaining;

    public PlayerPickupItemEvent(final Player player, final Item item, int remaining) {
        super(player);
        this.item = item;
        this.remaining = remaining;
    }

    /**
     * Gets the ItemDrop created by the player
     *
     * @return Item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Gets the amount remaining on the ground, if any
     *
     * @return amount remaining on the ground
     */
    public int getRemaining() {
        return remaining;
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
