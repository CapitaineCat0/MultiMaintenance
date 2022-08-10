package me.capitainecat0.multimaintenance.utils;

import me.capitainecat0.multimaintenance.MultiMaintenance;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class YamlAuthorized {

    private static final String ALLOWED_LIST = "whitelist.yml";
    private static final String KEY = "allowed_players";
    private final YamlConfiguration configuration;
    private final File savedFile;

    public YamlAuthorized(MultiMaintenance main){
        this.savedFile = new File(main.getDataFolder(), ALLOWED_LIST);
        this.configuration = YamlConfiguration.loadConfiguration(savedFile);
    }

    public void writeAllowed() throws IOException {
        List<String> uuids = new ArrayList<>();

        for (UUID uuid : MultiMaintenance.getAUTHORIZED()){
            uuids.add(uuid.toString());
        }

        configuration.set(KEY, uuids);
        configuration.save(savedFile);
    }

    public HashSet<UUID> readAllowed() {
        if(configuration!=null) {

            final List<String> uuids = (List<String>) configuration.getList(KEY);

            if (uuids != null){
                final HashSet<UUID> sUUIDs = new HashSet<>();
                for(String uuid : uuids){
                    sUUIDs.add(UUID.fromString(uuid));
                }
                return sUUIDs;
            }

        }
        return new HashSet<>();
    }

}
