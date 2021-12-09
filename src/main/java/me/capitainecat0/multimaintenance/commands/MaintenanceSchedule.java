package me.capitainecat0.multimaintenance.commands;

import me.capitainecat0.multimaintenance.MultiMaintenance;
import me.capitainecat0.multimaintenance.runnables.MaintenanceDurationRunnable;
import me.capitainecat0.multimaintenance.runnables.ScheduledMaintenanceRunnable;
import me.capitainecat0.multimaintenance.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MaintenanceSchedule implements CommandExecutor {
    private final MultiMaintenance instance;
    public MaintenanceSchedule(MultiMaintenance instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("multimaintenance.schedule") || !sender.hasPermission("multimaintenance.*")) {
            sender.sendMessage(Messenger.MAINTENANCE_NO_PERMISSION.getMessage());
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(Messenger.MAINTENANCE_NO_ARGUMENTS.getMessage());
            return true;
        }
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("schedule")){
                final int delay = Integer.parseInt(args[1]);
                MultiMaintenance.DELAY_BEFORE_MAINTENANCE = delay;
                if (!MultiMaintenance.SCHEDULED) {
                    if (!MultiMaintenance.ENABLED) {
                        new ScheduledMaintenanceRunnable(delay).runTaskTimer(instance, 0L, 20L);
                        MultiMaintenance.SCHEDULED = true;
                        if (MultiMaintenance.BROADCASTS_ENABLED) {
                            Bukkit.broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_BROADCAST_MESSAGE.getMessage().replace("%s", delay + "&e&lsecondes"));
                        }
                        sender.sendMessage(Messenger.MAINTENANCE_SUCCESSFULLY_SCHEDULED.getMessage().replace("%s", Integer.toString(delay)));
                    } else {
                        sender.sendMessage(Messenger.MAINTENANCE_ALREADY_ENABLED.getMessage());
                    }
                } else {
                    sender.sendMessage(Messenger.MAINTENANCE_ALREADY_SCHEDULED.getMessage());
                }
            }else if (args[0].equalsIgnoreCase("duration")){
                if (!MultiMaintenance.SCHEDULED) {
                    if (!MultiMaintenance.ENABLED) {
                        final int duration = Integer.parseInt(args[1]);
                        MultiMaintenance.MAINTENANCE_DURATION = duration;
                        new MaintenanceDurationRunnable(duration).runTaskTimer(instance, 0L, 20L);
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "maintenance on");
                        sender.sendMessage(Messenger.MAINTENANCE_SUCCESSFULLY_ENABLED_DURATION.getMessage().replace("%s", Integer.toString(duration)));
                    } else {
                        sender.sendMessage(Messenger.MAINTENANCE_ALREADY_ENABLED.getMessage());
                    }
                } else {
                    sender.sendMessage(Messenger.MAINTENANCE_ALREADY_SCHEDULED.getMessage());
                }
            }
        }
        return true;
    }
}