package me.susalert.bloodcore.api;

import org.bukkit.entity.Player;

public interface SlotChangeInteractable {
    void onSlotChange(Player player, int oldSlot, int newSlot);
}
