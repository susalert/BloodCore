package me.susalert.bloodcore.api;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;

public interface DropItemInteractable {
    void onDropItem(Player player, PlayerDropItemEvent event);
}
