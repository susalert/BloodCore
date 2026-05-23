package me.susalert.bloodcore.api;

import org.bukkit.entity.Player;

/**
 * An interface for CoreAbilities to easily hook into Shift and Left Clicks
 * without needing their own Bukkit Listeners.
 */
public interface Interactable {
    
    /**
     * Fired when the player presses Shift with the ability bound.
     */
    void onShiftClick(Player player);
    
    /**
     * Fired when the player Left Clicks with the ability bound.
     */
    void onLeftClick(Player player);
}