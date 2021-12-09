package me.capitainecat0.multimaintenance.runnables;

import me.capitainecat0.multimaintenance.MultiMaintenance;
import me.capitainecat0.multimaintenance.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class MaintenanceDurationRunnable extends BukkitRunnable {

    public int time;
    public MaintenanceDurationRunnable(int time) {
        this.time = time;
    }


    @Override
    public void run() {
        if(MultiMaintenance.ENABLED) {
            if (time == 3600 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "1 §eheure"));
            } else if (time == 60 * 30 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "30 §eminutes"));
            } else if (time == 600 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "10 §eminutes"));
            } else if (time == 60 * 5 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "5 §eminutes"));
            } else if (time == 60 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "1 §eminute"));
            } else if (time == 30 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "30 §esecondes"));
            } else if (time == 10 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "10 §esecondes"));
            } else if (time == 5 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "5 §esecondes"));
            } else if (time == 4 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "4 §esecondes"));
            } else if (time == 3 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "3 §esecondes"));
            } else if (time == 2 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "2 §esecondes"));
            } else if (time == 1 && MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.getServer().broadcastMessage(Messenger.MAINTENANCE_SCHEDULED_END_BROADCAST_MESSAGE.getMessage().replace("%s", "1 §eseconde"));
            } else if (time == 0) {
                if(MultiMaintenance.BROADCASTS_ENABLED){
                    Bukkit.broadcastMessage(Messenger.MAINTENANCE_ENDED.getMessage());
                }
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "maintenance off");
                this.cancel();
                return;
            }
            time--;
        } else {
            System.out.println("Error : Maintenance disabled while running.");
            if(MultiMaintenance.BROADCASTS_ENABLED) {
                Bukkit.broadcastMessage(Messenger.MAINTENANCE_ENDED.getMessage());
            }
            this.cancel();
            return;
        }
    }
}
