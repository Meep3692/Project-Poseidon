package org.bukkit.event.player;

import org.bukkit.entity.Player;

/**
 * Called when a player joins a server
 */
public class PlayerJoinEvent extends PlayerEvent {
    private String joinMessage;

    public PlayerJoinEvent(Player playerJoined, String joinMessage) {
        super(playerJoined);
        this.joinMessage = joinMessage;
    }

    /**
     * Gets the join message to send to all online players
     *
     * @return string join message
     */
    public String getJoinMessage() {
        return joinMessage;
    }

    /**
     * Sets the join message to send to all online players
     *
     * @param joinMessage join message
     */
    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }
}
