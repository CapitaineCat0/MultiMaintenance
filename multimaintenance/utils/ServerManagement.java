package me.capitainecat0.multimaintenance.utils;

import me.capitainecat0.multimaintenance.MultiMaintenance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerManagement {
    public static void KickNotAllowed(){
        for(Player players : Bukkit.getOnlinePlayers()){
            if(players.hasPermission("multimaintenance.join") || MultiMaintenance.getAUTHORIZED().contains(players.getUniqueId())) {
            }else{
                players.kickPlayer(
                        Messenger.MAINTENANCE_KICK.getMessage());
            }
        }
    }
}

