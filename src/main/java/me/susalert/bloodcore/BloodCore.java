package me.susalert.bloodcore;

import me.susalert.bloodcore.cleanup.CleanupManager;
import me.susalert.bloodcore.listeners.CoreListener;
import org.bukkit.plugin.java.JavaPlugin;

public class BloodCore extends JavaPlugin {

    private static BloodCore instance;
    private CleanupManager cleanupManager;

    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("ProjectKorra") == null) {
            getLogger().severe("ProjectKorra is not installed! BloodCore API cannot function.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;
        this.cleanupManager = new CleanupManager();

        getServer().getPluginManager().registerEvents(new CoreListener(), this);

        getLogger().info("BloodCore API initialized. Ready to boost some addons.");
    }

    @Override
    public void onDisable() {
        if (instance == null) return; 

        getLogger().info("BloodCore shutting down. Executing global cleanups...");
        if (cleanupManager != null) {
            cleanupManager.revertAll();
        }
    }

    public static BloodCore getInstance() { return instance; }
    public CleanupManager getCleanupManager() { return cleanupManager; }
}