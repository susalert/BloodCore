package me.susalert.bloodcore.api;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public interface OffhandSwapInteractable {
    void onOffhandSwap(Player player, PlayerSwapHandItemsEvent event);
}
