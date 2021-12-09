package me.capitainecat0.multimaintenance.commands;

import me.capitainecat0.multimaintenance.MultiMaintenance;

public class CommandsListener {
    public void init(){
        MultiMaintenance.instance().registerCommand(new MaintenanceToggle(), "maintenance");
    }
}