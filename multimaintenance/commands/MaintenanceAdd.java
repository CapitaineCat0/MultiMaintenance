package me.capitainecat0.multimaintenance.commands;

import me.capitainecat0.multimaintenance.MultiMaintenance;
import me.capitainecat0.multimaintenance.utils.Messenger;
import me.capitainecat0.multimaintenance.utils.YamlAuthorized;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MaintenanceAdd implements CommandExecutor {
    private MultiMaintenance maintenance;
    public MaintenanceAdd(MultiMaintenance maintenance){
        this.maintenance = maintenance;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("multimaintenance.add") || !sender.hasPermission("multimaintenance.*")){
            sender.sendMessage(Messenger.MAINTENANCE_NO_PERMISSION.getMessage());
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Messenger.MAINTENANCE_NO_ARGUMENTS.getMessage());
            return true;
        }
        else if(args.length == 2){
            if (args[0].equalsIgnoreCase("add")) {
                final String name = args[1];
                final Player player = Bukkit.getPlayerExact(name);
                try {
                    assert player != null;
                    final UUID uuid = player.getUniqueId();
                    if (!MultiMaintenance.getAUTHORIZED().contains(uuid)) {
                        MultiMaintenance.getAUTHORIZED().add(uuid);
                        new YamlAuthorized(maintenance).writeAllowed();
                        sender.sendMessage(Messenger.ALLOWED_SUCCESSFULLY_ADDED.getMessage().replace("%p", player.getName()));
                    } else {
                        sender.sendMessage(Messenger.ALLOWED_ALREADY_ADDED.getMessage().replace("%p", player.getName()));
                    }
                } catch (Exception e) {
                    sender.sendMessage(Messenger.ALLOWED_PLAYER_NOT_FOUND.getMessage().replace("%p", player.getName()));
                }
            }
            /*if(args[0].equalsIgnoreCase("add")) {
                if (args.length == 1) {
                    sender.sendMessage(Messenger.MAINTENANCE_ADD_ERROR.getMsg());
                } else {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if(target == null) {
                        sender.sendMessage(Messenger.MAINTENANCE_INVALID_PLAYER.getMsg().replace("{0}", args[1]));
                        return true;
                    }else{
                        MultiMaintenance.getAUTORIZED().add(target.getUniqueId());
                        sender.sendMessage(Messenger.MAINTENANCE_ADD_DONE.getMsg().replace("{0}", args[1]));
                    }
                }
            }
            else {
                sender.sendMessage(Messenger.MAINTENANCE_INVALID_ARGUMENTS.getMsg().replace("{0}", args[0]));
            }*/
        }
        return true;
    }
}
