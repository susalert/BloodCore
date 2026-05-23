package me.susalert.bloodcore.listeners;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.susalert.bloodcore.api.Interactable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class CoreListener implements Listener {

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) return;
        
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if (bPlayer == null) return;

        String boundName = bPlayer.getBoundAbilityName();
        if (boundName == null || boundName.isEmpty()) return;

        // Fetch the "fake" registry instance from ProjectKorra
        CoreAbility dummyAbility = CoreAbility.getAbility(boundName);
        
        // If it's one of our BloodCore abilities, trigger the shift logic!
        if (dummyAbility instanceof Interactable) {
            ((Interactable) dummyAbility).onShiftClick(player);
        }
    }

    @EventHandler
    public void onLeftClick(PlayerAnimationEvent event) {
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if (bPlayer == null) return;

        String boundName = bPlayer.getBoundAbilityName();
        if (boundName == null || boundName.isEmpty()) return;

        // Fetch the "fake" registry instance from ProjectKorra
        CoreAbility dummyAbility = CoreAbility.getAbility(boundName);

        if (dummyAbility instanceof Interactable) {
            ((Interactable) dummyAbility).onLeftClick(player);
        }
    }
}