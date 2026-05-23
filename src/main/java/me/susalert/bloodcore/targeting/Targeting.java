package me.susalert.bloodcore.targeting;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class Targeting {

    /**
     * Safely grabs the first living entity the player is looking at within a specific range.
     * Example Usage: Finding a target to grab with BloodGrip or BloodHook.
     * * @param player The bender aiming.
     * @param range The max distance to check.
     * @return The targeted LivingEntity, or null if they missed.
     */
    public static LivingEntity getTargetedEntity(Player player, double range) {
        Location eyeLoc = player.getEyeLocation();
        Vector direction = eyeLoc.getDirection();
        
        // Paper's built-in raytrace is insanely optimized compared to manual loops
        RayTraceResult result = player.getWorld().rayTraceEntities(
            eyeLoc, 
            direction, 
            range, 
            0.5, // Hitbox leniency
            entity -> entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()
        );

        if (result != null && result.getHitEntity() != null) {
            return (LivingEntity) result.getHitEntity();
        }
        return null;
    }

    /**
     * Checks if a target is completely visible to the player (no walls in the way).
     * Example Usage: Ensuring you don't bloodbend someone through a solid brick wall.
     * * @param player The bender.
     * @param target The target to check.
     * @return True if there is a clear line of sight.
     */
    public static boolean hasLineOfSight(Player player, Entity target) {
        return player.hasLineOfSight(target);
    }
}