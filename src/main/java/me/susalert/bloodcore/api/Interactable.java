package me.susalert.bloodcore.api;

import com.projectkorra.projectkorra.event.AbilityEndEvent;
import com.projectkorra.projectkorra.event.BendingReloadEvent;
import com.projectkorra.projectkorra.event.PlayerBindChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

/**
 * High-level BloodCore API contract for addon abilities.
 *
 * Implement this single interface to opt in to centralized event routing,
 * then override only the hooks your ability needs.
 */
public interface Interactable extends
        SwingInteractable,
        RightClickInteractable,
        SneakStartInteractable,
        SneakEndInteractable,
        SlotChangeInteractable,
        OffhandSwapInteractable,
        DropItemInteractable,
        DamageTakenInteractable,
        BendingReloadAware,
        AbilityEndAware,
        BindChangeAware {

    @Override
    default void onSwing(Player player) {}

    @Override
    default void onRightClick(Player player, PlayerInteractEvent event) {}

    @Override
    default void onSneakStart(Player player) {}

    @Override
    default void onSneakEnd(Player player) {}

    @Override
    default void onSlotChange(Player player, int oldSlot, int newSlot) {}

    @Override
    default void onOffhandSwap(Player player, PlayerSwapHandItemsEvent event) {}

    @Override
    default void onDropItem(Player player, PlayerDropItemEvent event) {}

    @Override
    default boolean onDamageTaken(EntityDamageEvent event) {
        return false;
    }

    @Override
    default void onBendingReload(BendingReloadEvent event) {}

    @Override
    default void onAbilityEnd(AbilityEndEvent event) {}

    @Override
    default void onBindChange(PlayerBindChangeEvent event) {}
}
