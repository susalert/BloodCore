package me.susalert.bloodcore.listeners;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.util.MultiAbilityManager;
import com.projectkorra.projectkorra.event.AbilityEndEvent;
import com.projectkorra.projectkorra.event.BendingReloadEvent;
import com.projectkorra.projectkorra.event.PlayerBindChangeEvent;
import com.projectkorra.projectkorra.event.PlayerSwingEvent;
import me.susalert.bloodcore.api.AbilityEndAware;
import me.susalert.bloodcore.api.BendingReloadAware;
import me.susalert.bloodcore.api.BindChangeAware;
import me.susalert.bloodcore.api.DamageTakenInteractable;
import me.susalert.bloodcore.api.DropItemInteractable;
import me.susalert.bloodcore.api.OffhandSwapInteractable;
import me.susalert.bloodcore.api.RightClickInteractable;
import me.susalert.bloodcore.api.SlotChangeInteractable;
import me.susalert.bloodcore.api.SneakEndInteractable;
import me.susalert.bloodcore.api.SneakStartInteractable;
import me.susalert.bloodcore.api.SwingInteractable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashSet;
import java.util.Set;

public class CoreListener implements Listener {

    private CoreAbility getBoundAbility(Player player) {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if (bPlayer == null) return null;

        String boundName = bPlayer.getBoundAbilityName();
        if (boundName != null && !boundName.isEmpty()) {
            CoreAbility boundAbility = CoreAbility.getAbility(boundName);
            if (boundAbility != null) {
                return boundAbility;
            }
        }

        if (MultiAbilityManager.hasMultiAbilityBound(player)) {
            String multiName = MultiAbilityManager.getBoundMultiAbility(player);
            if (multiName != null && !multiName.isEmpty()) {
                for (CoreAbility activeAbility : CoreAbility.getAbilities(player, CoreAbility.class)) {
                    if (activeAbility.getName().equalsIgnoreCase(multiName)) {
                        return activeAbility;
                    }
                }
            }
        }

        return null;
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        CoreAbility boundAbility = getBoundAbility(player);
        if (boundAbility == null) return;

        if (event.isSneaking()) {
            if (boundAbility instanceof SneakStartInteractable) {
                ((SneakStartInteractable) boundAbility).onSneakStart(player);
            }
        } else {
            if (boundAbility instanceof SneakEndInteractable) {
                ((SneakEndInteractable) boundAbility).onSneakEnd(player);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSwing(PlayerSwingEvent event) {
        Player player = event.getPlayer();
        CoreAbility boundAbility = getBoundAbility(player);
        if (boundAbility instanceof SwingInteractable) {
            ((SwingInteractable) boundAbility).onSwing(player);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        CoreAbility boundAbility = getBoundAbility(player);
        if (boundAbility instanceof RightClickInteractable) {
            ((RightClickInteractable) boundAbility).onRightClick(player, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSlotChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        CoreAbility boundAbility = getBoundAbility(player);
        if (boundAbility instanceof SlotChangeInteractable) {
            ((SlotChangeInteractable) boundAbility).onSlotChange(player, event.getPreviousSlot(), event.getNewSlot());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onOffhandSwap(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        CoreAbility boundAbility = getBoundAbility(player);
        if (boundAbility instanceof OffhandSwapInteractable) {
            ((OffhandSwapInteractable) boundAbility).onOffhandSwap(player, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        CoreAbility boundAbility = getBoundAbility(player);
        if (boundAbility instanceof DropItemInteractable) {
            ((DropItemInteractable) boundAbility).onDropItem(player, event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        for (CoreAbility activeAbility : CoreAbility.getAbilities(player, CoreAbility.class)) {
            if (activeAbility instanceof DamageTakenInteractable) {
                boolean blocked = ((DamageTakenInteractable) activeAbility).onDamageTaken(event);
                if (blocked) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBendingReload(BendingReloadEvent event) {
        Set<CoreAbility> visited = new HashSet<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (CoreAbility activeAbility : CoreAbility.getAbilities(player, CoreAbility.class)) {
                if (!visited.add(activeAbility)) continue;
                if (activeAbility instanceof BendingReloadAware) {
                    ((BendingReloadAware) activeAbility).onBendingReload(event);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onAbilityEnd(AbilityEndEvent event) {
        if (event.getAbility() instanceof AbilityEndAware) {
            ((AbilityEndAware) event.getAbility()).onAbilityEnd(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBindChange(PlayerBindChangeEvent event) {
        if (!event.isOnline() || event.getPlayer().getPlayer() == null) return;
        Player player = event.getPlayer().getPlayer();

        CoreAbility boundAbility = getBoundAbility(player);
        if (boundAbility instanceof BindChangeAware) {
            ((BindChangeAware) boundAbility).onBindChange(event);
        }

        for (CoreAbility activeAbility : CoreAbility.getAbilities(player, CoreAbility.class)) {
            if (activeAbility instanceof BindChangeAware) {
                ((BindChangeAware) activeAbility).onBindChange(event);
            }
        }
    }
}
