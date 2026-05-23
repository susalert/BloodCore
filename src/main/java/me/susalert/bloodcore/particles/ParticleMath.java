package me.susalert.bloodcore.particles;

import com.projectkorra.projectkorra.GeneralMethods;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleMath {

    /**
     * Generates a list of locations forming a perfect ring/circle.
     * Example Usage: For generating BloodShield rings.
     * * @param center The center location.
     * @param radius The radius of the ring.
     * @param points How many points to generate (higher = smoother).
     * @return List of locations forming the ring.
     */
    public static List<Location> getRing(Location center, double radius, int points) {
        List<Location> locations = new ArrayList<>();
        double increment = (2 * Math.PI) / points;
        
        for (int i = 0; i < points; i++) {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(center.getWorld(), x, center.getY(), z));
        }
        return locations;
    }

    /**
     * Draws a straight beam of red particles from point A to point B.
     * Example Usage: Drawing the PiercingBlood laser beam instantly.
     * * @param start The starting point.
     * @param end The ending point.
     * @param spacing Space between each particle.
     * @param hexColor The hex code (e.g., "FF0000" for pure red).
     */
    public static void drawLine(Location start, Location end, double spacing, String hexColor) {
        double distance = start.distance(end);
        Vector direction = end.toVector().subtract(start.toVector()).normalize().multiply(spacing);
        
        Location current = start.clone();
        for (double d = 0; d < distance; d += spacing) {
            GeneralMethods.displayColoredParticle(hexColor, current);
            current.add(direction);
        }
    }
}