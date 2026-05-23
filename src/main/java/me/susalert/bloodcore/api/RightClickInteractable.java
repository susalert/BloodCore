package me.susalert.bloodcore.api;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public interface RightClickInteractable {
    void onRightClick(Player player, PlayerInteractEvent event);
}
