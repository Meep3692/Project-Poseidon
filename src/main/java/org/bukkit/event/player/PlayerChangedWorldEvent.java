
package org.bukkit.event.player;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerChangedWorldEvent extends PlayerEvent {

    private final World from;

    public PlayerChangedWorldEvent(Player player, World from) {
        super(player);
        this.from = from;
    }

    public World getFrom() {
        return from;
    }
}