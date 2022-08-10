package me.capitainecat0.multimaintenance.utils;

import me.capitainecat0.multimaintenance.MultiMaintenance;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlConfig {
    private static final String CONFIG_NAME = "config.yml";
    private final YamlConfiguration configuration;
    private final File savedFile;

    public YamlConfig(MultiMaintenance main){
        this.savedFile = new File(main.getDataFolder(), CONFIG_NAME);
        this.configuration = YamlConfiguration.loadConfiguration(savedFile);
    }

    public void writeConfig() throws IOException {
        configuration.set("maintenance_state", MultiMaintenance.ENABLED);
        configuration.set("broadcasts_enabled", MultiMaintenance.BROADCASTS_ENABLED);
        configuration.save(savedFile);
    }
    public void readConfig(){
        MultiMaintenance.ENABLED = configuration.getBoolean("maintenance_state");
        MultiMaintenance.BROADCASTS_ENABLED = true;
    }
}