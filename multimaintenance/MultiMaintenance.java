package me.capitainecat0.multimaintenance;

import me.capitainecat0.multimaintenance.commands.CommandsListener;
import me.capitainecat0.multimaintenance.listeners.Listeners;
import me.capitainecat0.multimaintenance.utils.PluginCore;
import me.capitainecat0.multimaintenance.utils.YamlAuthorized;
import me.capitainecat0.multimaintenance.utils.YamlConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class MultiMaintenance extends PluginCore<MultiMaintenance> {
    public static boolean ENABLED = false;
    public static boolean SCHEDULED = false;
    public static boolean BROADCASTS_ENABLED = true;
    public static int DELAY_BEFORE_MAINTENANCE = 0;
    public static int MAINTENANCE_DURATION = 0;

    private static MultiMaintenance instance;
    private static Set<UUID> AUTHORIZED = new HashSet<>();

    @Override
    protected boolean start(MultiMaintenance main) {
        instance = main;
        readList();
        readMaintenanceState();
        new CommandsListener().init();
        new Listeners().init();
        this.saveResourceAs("config.yml");
        this.saveResourceAs("whitelist.yml");
        this.saveDefaultConfig();
        return true;
    }

    @Override
    protected void stop() {

    }

    public static MultiMaintenance getInstance() {
        return instance;
    }

    public static Set<UUID> getAUTHORIZED() {
        return AUTHORIZED;
    }

    private void readList(){
        MultiMaintenance.AUTHORIZED = new YamlAuthorized(this).readAllowed();
    }

    private void readMaintenanceState(){
        new YamlConfig(this).readConfig();
    }

    public void saveResourceAs(String inPath) {
        if (inPath != null && !inPath.isEmpty()) {
            InputStream in = this.getResource(inPath);
            if (in == null) {
                throw new IllegalArgumentException("Le fichier " + inPath + " est introuvable dans le dossier du plugin");
            } else {
                if (!this.getDataFolder().exists() && !this.getDataFolder().mkdir()) {
                    this.getLogger().severe("Impossible de créer le dossier !");
                }

                File inFile = new File(this.getDataFolder(), inPath);
                if (!inFile.exists()) {
                    Bukkit.getConsoleSender().sendMessage("§cLe fichier " + inFile.getName() + " est introuvable, création en cours ...");
                    this.saveResource(inPath, false);
                    if (!inFile.exists()) {
                        this.getLogger().severe("Impossible de copier le fichier !");
                    } else {
                        Bukkit.getConsoleSender().sendMessage("§aLe fichier " + inFile.getName() + " à été créé !");
                    }
                }

            }
        } else {
            throw new IllegalArgumentException("Le dossier ne doit pas être vide/null !");
        }
    }
    }
