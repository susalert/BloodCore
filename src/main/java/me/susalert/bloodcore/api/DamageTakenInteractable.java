package me.susalert.bloodcore.api;

import org.bukkit.event.entity.EntityDamageEvent;

public interface DamageTakenInteractable {
    boolean onDamageTaken(EntityDamageEvent event);
}
