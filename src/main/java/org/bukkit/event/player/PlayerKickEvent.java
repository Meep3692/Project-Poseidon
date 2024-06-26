package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Called when a player gets kicked from the server
 */
public class PlayerKickEvent extends PlayerEvent implements Cancellable {
    private String leaveMessage;
    private String kickReason;
    private Boolean cancel;

    public PlayerKickEvent(Player playerKicked, String kickReason, String leaveMessage) {
        super(playerKicked);
        this.kickReason = kickReason;
        this.leaveMessage = leaveMessage;
        this.cancel = false;
    }

    /**
     * Gets the reason why the player is getting kicked
     *
     * @return string kick reason
     */
    public String getReason() {
        return kickReason;
    }

    /**
     * Gets the leave message send to all online players
     *
     * @return string kick reason
     */
    public String getLeaveMessage() {
        return leaveMessage;
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    /**
     * Sets the reason why the player is getting kicked
     *
     * @param kickReason kick reason
     */
    public void setReason(String kickReason) {
        this.kickReason = kickReason;
    }

    /**
     * Sets the leave message send to all online players
     *
     * @param leaveMessage leave message
     */
    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }
}
