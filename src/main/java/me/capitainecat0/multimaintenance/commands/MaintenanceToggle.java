package me.capitainecat0.multimaintenance.commands;

import me.capitainecat0.multimaintenance.MultiMaintenance;
import me.capitainecat0.multimaintenance.utils.Messenger;
import me.capitainecat0.multimaintenance.utils.ServerManagement;
import me.capitainecat0.multimaintenance.utils.YamlAuthorized;
import me.capitainecat0.multimaintenance.utils.YamlConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class MaintenanceToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("multimaintenance.toggle") || !sender.hasPermission("multimaintenance.*")){
            sender.sendMessage(Messenger.MAINTENANCE_NO_PERMISSION.getMessage());
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Messenger.MAINTENANCE_NO_ARGUMENTS.getMessage());
            return true;
        }
        else if(args.length == 1){
            if (args[0].equalsIgnoreCase("on")){
                if (!MultiMaintenance.ENABLED){
                    if (!MultiMaintenance.SCHEDULED) {
                        try {
                            if(MultiMaintenance.BROADCASTS_ENABLED) {
                                Bukkit.broadcastMessage(Messenger.MAINTENANCE_ENABLED_BROADCAST.getMessage());
                                }
                            MultiMaintenance.ENABLED = true;
                            new YamlConfig(MultiMaintenance.getInstance()).writeConfig();
                            sender.sendMessage(Messenger.MAINTENANCE_ENABLED.getMessage());
                            ServerManagement.KickNotAllowed();
                        } catch (IOException e) {
                                e.printStackTrace();
                                sender.sendMessage(Messenger.COMMAND_ERROR_OCCURED.getMessage().replace("%cmd", "maintenance on"));
                            }
                        } else {
                            sender.sendMessage(Messenger.MAINTENANCE_ALREADY_SCHEDULED.getMessage());
                        }
                    } else {
                        sender.sendMessage(Messenger.MAINTENANCE_ALREADY_ENABLED.getMessage());
                    }


                //Disabling maintenance mode
            }
            /*if(args[0].equalsIgnoreCase("on")){
                MultiMaintenance.MAINTENANCE = true;
                ServerManagement.KickNotAllowed();
                sender.sendMessage(Messenger.MAINTENANCE_ON.getMsg());
            }
            else {
                sender.sendMessage(Messenger.MAINTENANCE_INVALID_ARGUMENTS.getMsg().replace("{0}", args[0]));
            }*/
            /*if(args[0].equalsIgnoreCase("off")){
                MultiMaintenance.MAINTENANCE = false;
                sender.sendMessage(Messenger.MAINTENANCE_OFF.getMsg());
            }
            else {
                sender.sendMessage(Messenger.MAINTENANCE_INVALID_ARGUMENTS.getMsg().replace("{0}", args[0]));
            }*/
            else if (args[0].equalsIgnoreCase("off")){
                if (MultiMaintenance.ENABLED){
                    try {
                        MultiMaintenance.ENABLED = false;
                        new YamlConfig(MultiMaintenance.getInstance()).writeConfig();
                        sender.sendMessage(Messenger.MAINTENANCE_DISABLED.getMessage());
                        if(MultiMaintenance.BROADCASTS_ENABLED) {
                            Bukkit.broadcastMessage(Messenger.MAINTENANCE_ENDED.getMessage());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        sender.sendMessage(Messenger.COMMAND_ERROR_OCCURED.getMessage().replace("%cmd", "maintenance off"));
                    }
                } else if (MultiMaintenance.SCHEDULED) {
                    MultiMaintenance.SCHEDULED = false;
                    sender.sendMessage(Messenger.MAINTENANCE_SCHEDULED_CANCELLED.getMessage());
                } else {
                    sender.sendMessage(Messenger.MAINTENANCE_ALREADY_DISABLED.getMessage());
                }
            }else if (args[0].equalsIgnoreCase("list")){
                OfflinePlayer player;
                sender.sendMessage(Messenger.ALLOWED_LIST.getMessage());
                for (UUID uuid : MultiMaintenance.getAUTHORIZED()) {
                    player = Bukkit.getOfflinePlayer(uuid);
                    if (player != null) {
                        sender.sendMessage("§e-§a " + player.getName());
                    }
                }
            }else if(args[0].equalsIgnoreCase("status")){
                if(MultiMaintenance.ENABLED){
                    sender.sendMessage(Messenger.MAINTENANCE_STATUS.getMessage().replace("%status", "§aactive"));
                }else if(!MultiMaintenance.ENABLED){
                    sender.sendMessage(Messenger.MAINTENANCE_STATUS.getMessage().replace("%status", "§cdésactivée"));
                }

            }
            }else if(args.length == 2){
            if (args[0].equalsIgnoreCase("add")) {
                final String name = args[1];
                final Player player = Bukkit.getPlayerExact(name);
                try {
                    assert player != null;
                    final UUID uuid = player.getUniqueId();
                    if (!MultiMaintenance.getAUTHORIZED().contains(uuid)) {
                        MultiMaintenance.getAUTHORIZED().add(uuid);
                        new YamlAuthorized(MultiMaintenance.getInstance()).writeAllowed();
                        sender.sendMessage(Messenger.ALLOWED_SUCCESSFULLY_ADDED.getMessage().replace("%p", player.getName()));
                    } else {
                        sender.sendMessage(Messenger.ALLOWED_ALREADY_ADDED.getMessage().replace("%p", player.getName()));
                    }
                } catch (Exception e) {
                    sender.sendMessage(Messenger.ALLOWED_PLAYER_NOT_FOUND.getMessage().replace("%p", name));
                }
            }else if (args[0].equalsIgnoreCase("remove")) {
                final String name = args[1];
                final UUID uuid = Bukkit.getPlayerExact(name).getUniqueId();
                try {
                    if (MultiMaintenance.getAUTHORIZED().contains(uuid)) {
                        MultiMaintenance.getAUTHORIZED().remove(uuid);
                        if(MultiMaintenance.ENABLED){
                            ServerManagement.KickNotAllowed();}
                        new YamlAuthorized(MultiMaintenance.getInstance()).writeAllowed();
                        sender.sendMessage(Messenger.ALLOWED_SUCCESSFULLY_REMOVED.getMessage().replace("%p", Bukkit.getPlayerExact(name).getName()));
                    } else {
                        sender.sendMessage(Messenger.ALLOWED_NOT_IN_LIST.getMessage().replace("%p", Bukkit.getPlayerExact(name).getName()));
                    }
                } catch (IOException e) {
                    sender.sendMessage(Messenger.ALLOWED_PLAYER_NOT_FOUND.getMessage().replace("%p", name));
                }
            }
        }
        return false;
    }
}
