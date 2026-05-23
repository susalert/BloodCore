package me.susalert.bloodcore.state;

/**
 * A universal utility for tracking charge times, firing states, and durations.
 * Replaces boilerplate variables like 'startTime', 'isCharged', etc.
 */
public class ChargeTracker {

    private long startTime;
    private final long requiredChargeTime;
    private boolean hasAnnouncedCharge;

    public ChargeTracker(long chargeTimeMs) {
        this.requiredChargeTime = chargeTimeMs;
        this.startTime = System.currentTimeMillis();
        this.hasAnnouncedCharge = false;
    }

    /**
     * Checks if the required time has passed.
     */
    public boolean isFullyCharged() {
        return System.currentTimeMillis() - startTime >= requiredChargeTime;
    }

    /**
     * A one-time trigger check. Returns true ONLY on the exact tick it reaches full charge.
     * Perfect for playing a "Max Charge" sound effect once.
     */
    public boolean justReachedCharge() {
        if (isFullyCharged() && !hasAnnouncedCharge) {
            hasAnnouncedCharge = true;
            return true;
        }
        return false;
    }

    /**
     * Returns a percentage of completion (0.0 to 1.0).
     * Useful for updating BossBars or particle intensities.
     */
    public double getChargePercent() {
        if (isFullyCharged()) return 1.0;
        return (double) (System.currentTimeMillis() - startTime) / requiredChargeTime;
    }

    /**
     * Resets the timer back to zero.
     */
    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.hasAnnouncedCharge = false;
    }
}