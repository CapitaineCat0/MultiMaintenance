package me.capitainecat0.multimaintenance.commands;

import me.capitainecat0.multimaintenance.MultiMaintenance;
import me.capitainecat0.multimaintenance.utils.Messenger;
import me.capitainecat0.multimaintenance.utils.ServerManagement;
import me.capitainecat0.multimaintenance.utils.YamlAuthorized;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.UUID;

public class MaintenanceRemove implements CommandExecutor {
    private final MultiMaintenance instance;
    public MaintenanceRemove(MultiMaintenance instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("multimaintenance.remove") || !sender.hasPermission("multimaintenance.*")){
            sender.sendMessage(Messenger.MAINTENANCE_NO_PERMISSION.getMessage());
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Messenger.MAINTENANCE_NO_ARGUMENTS.getMessage());
            return true;
        }
        else if(args.length == 2){
            if (args[0].equalsIgnoreCase("remove")) {
                final String name = args[1];
                final UUID uuid = Bukkit.getPlayerExact(name).getUniqueId();
                try {
                    if (MultiMaintenance.getAUTHORIZED().contains(uuid)) {
                        MultiMaintenance.getAUTHORIZED().remove(uuid);
                        if(MultiMaintenance.ENABLED){
                            ServerManagement.KickNotAllowed();}
                        new YamlAuthorized(instance).writeAllowed();
                        sender.sendMessage(Messenger.ALLOWED_SUCCESSFULLY_REMOVED.getMessage().replace("%p", Bukkit.getPlayerExact(name).getName()));
                    } else {
                        sender.sendMessage(Messenger.ALLOWED_NOT_IN_LIST.getMessage().replace("%p", Bukkit.getPlayerExact(name).getName()));
                    }
                } catch (IOException e) {
                    sender.sendMessage(Messenger.ALLOWED_PLAYER_NOT_FOUND.getMessage().replace("%p", name));
                }
            }
    }
            /*if(args[0].equalsIgnoreCase("remove")){
                if (args.length == 1) {
                    sender.sendMessage(Messenger.MAINTENANCE_REMOVE_ERROR.getMsg());
                } else {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if(target == null) {
                        sender.sendMessage(Messenger.MAINTENANCE_INVALID_PLAYER.getMsg().replace("{0}", args[1]));
                        return true;
                    }else{
                        MultiMaintenance.getAUTORIZED().remove(target.getUniqueId());
                        sender.sendMessage(Messenger.MAINTENANCE_REMOVE_DONE.getMsg().replace("{0}", args[1]));
                    }
                }
            }
            else {
                sender.sendMessage(Messenger.MAINTENANCE_INVALID_ARGUMENTS.getMsg().replace("{0}", args[0]));
            }*/
        return true;
    }
}


