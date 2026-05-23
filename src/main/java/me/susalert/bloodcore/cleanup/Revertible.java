package me.susalert.bloodcore.cleanup;

/**
 * Defines an object or task that can be safely reverted or cleaned up.
 */
public interface Revertible {
    void revert();
}