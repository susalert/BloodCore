package me.susalert.bloodcore.cleanup;

import java.util.HashSet;
import java.util.Set;

public class CleanupManager {
    
    private final Set<Revertible> trackedTasks = new HashSet<>();

    /**
     * Register a task (like a TempBlock or BossBar) to be tracked.
     */
    public void track(Revertible task) {
        trackedTasks.add(task);
    }

    /**
     * Remove a task from tracking if it was already handled naturally.
     */
    public void untrack(Revertible task) {
        trackedTasks.remove(task);
    }

    /**
     * Obliterates all tracked tasks. Triggered on server shutdown.
     */
    public void revertAll() {
        for (Revertible task : trackedTasks) {
            try {
                task.revert();
            } catch (Exception ignored) {
                // We ignore exceptions so one broken revert doesn't stop the whole loop
            }
        }
        trackedTasks.clear();
    }
}