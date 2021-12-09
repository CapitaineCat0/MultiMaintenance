package me.capitainecat0.multimaintenance.listeners;

import me.capitainecat0.multimaintenance.MultiMaintenance;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Listeners {
    public void init() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListener(), MultiMaintenance.getInstance());
    }
}
