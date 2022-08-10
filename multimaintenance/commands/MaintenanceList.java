package me.capitainecat0.multimaintenance.commands;

import me.capitainecat0.multimaintenance.MultiMaintenance;
import me.capitainecat0.multimaintenance.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class MaintenanceList implements CommandExecutor {
    public MaintenanceList(MultiMaintenance instance) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("multimaintenance.list") || !sender.hasPermission("multimaintenance.*")){
            sender.sendMessage(Messenger.MAINTENANCE_NO_PERMISSION.getMessage());
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Messenger.MAINTENANCE_NO_ARGUMENTS.getMessage());
            return true;
        }
        else if(args.length == 1){
          if (args[0].equalsIgnoreCase("list")){
              OfflinePlayer player;
              sender.sendMessage(Messenger.ALLOWED_LIST.getMessage());
              for (UUID uuid : MultiMaintenance.getAUTHORIZED()) {
                  player = Bukkit.getOfflinePlayer(uuid);
                  if (player != null) {
                      sender.sendMessage("&e-&a " + player.getName());
                  }
              }
          }
        }
        return true;
    }
}

