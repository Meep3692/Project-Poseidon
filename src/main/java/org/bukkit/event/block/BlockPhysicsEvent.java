package org.bukkit.event.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;

/**
 * Thrown when a block physics check is called
 */
public class BlockPhysicsEvent extends BlockEvent implements Cancellable {
    private final int changed;
    private boolean cancel = false;

    public BlockPhysicsEvent(final Block block, final int changed) {
        super(block);
        this.changed = changed;
    }

    /**
     * Gets the type of block that changed, causing this event
     *
     * @return Changed block's type id
     */
    public int getChangedTypeId() {
        return changed;
    }

    /**
     * Gets the type of block that changed, causing this event
     *
     * @return Changed block's type
     */
    public Material getChangedType() {
        return Material.getMaterial(changed);
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
