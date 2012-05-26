package com.codisimus.plugins.shortcuts;

import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Shortcuts extends JavaPlugin {
    static Server server;

    @Override
    public void onDisable () {
    }
    
    @Override
    public void onEnable () {
        server = getServer();
        
        //Register Commands
        getCommand("td").setExecutor(new DownfallCommand());
        CommandExecutor executer = new TimeCommand();
        getCommand("day").setExecutor(executer);
        getCommand("night").setExecutor(executer);
        getCommand("gm").setExecutor(new GameModeCommand());
        executer = new WhisperCommand();
        getCommand("w").setExecutor(executer);
        getCommand("re").setExecutor(executer);
        executer = new TeleCommand();
        getCommand("tp").setExecutor(executer);
        getCommand("tph").setExecutor(executer);
        getCommand("spy").setExecutor(new SpyCommand());
        
        System.out.println("Shortcuts "+this.getDescription().getVersion()+" is enabled!");
    }
}