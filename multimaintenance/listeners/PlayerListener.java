package me.capitainecat0.multimaintenance.listeners;

import me.capitainecat0.multimaintenance.MultiMaintenance;
import me.capitainecat0.multimaintenance.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();

        if(MultiMaintenance.ENABLED && !player.hasPermission("multimaintenance.join")) {
            if (!MultiMaintenance.getAUTHORIZED().contains(player.getUniqueId())){
            player.kickPlayer(Messenger.MAINTENANCE_KICK.getMessage());
            }
        }
    }
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        if (MultiMaintenance.ENABLED) {
        event.setMotd(Messenger.MAINTENANCE_SERVERLIST.getMessage());
        } else {
            event.setMotd(Bukkit.getServer().getMotd());
        }

    }
}
