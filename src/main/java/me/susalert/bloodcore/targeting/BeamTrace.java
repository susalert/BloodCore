package me.susalert.bloodcore.targeting;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

public class BeamTrace {

    /**
     * Iterates points along a beam until range is exhausted or the predicate returns false.
     */
    public static void forEachPoint(Location origin, Vector direction, double range, double step, Predicate<Location> consumer) {
        Vector normalized = direction.clone().normalize();
        for (double distance = 0; distance <= range; distance += step) {
            Location point = origin.clone().add(normalized.clone().multiply(distance));
            if (!consumer.test(point)) {
                break;
            }
        }
    }
}
