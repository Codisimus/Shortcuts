package com.codisimus.plugins.shortcuts;

import java.io.File;
import java.util.Properties;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Shortcuts extends JavaPlugin {
    static Server server;
    static PluginManager pm;
    static String dataFolder;

    @Override
    public void onEnable () {
        server = getServer();
        pm = server.getPluginManager();

        File dir = this.getDataFolder();
        if (!dir.isDirectory()) {
            dir.mkdir();
        }

        dataFolder = dir.getPath();

        /* Register Events */
        pm.registerEvents(new BanHandler(), this);

        /* Register Commands */
        getCommand("enable").setExecutor(new EnableCommand());
        getCommand("disable").setExecutor(new DisableCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
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
        getCommand("ban").setExecutor(new TempBanCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("fly").setExecutor(new FlyCommand());

        Properties version = new Properties();
        try {
            version.load(this.getResource("version.properties"));
        } catch (Exception ex) {
        }
        getLogger().info("Shortcuts " + this.getDescription().getVersion()
                + " (Build " + version.getProperty("Build") + ") is enabled!");
    }
}
