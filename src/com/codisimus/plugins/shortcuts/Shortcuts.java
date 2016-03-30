package com.codisimus.plugins.shortcuts;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Shortcuts extends JavaPlugin {
    static File dataFolder;

    @Override
    public void onEnable () {
        dataFolder = this.getDataFolder();
        if (!dataFolder.isDirectory()) {
            dataFolder.mkdir();
        }

        /* Register Events */
        Bukkit.getPluginManager().registerEvents(new BanHandler(), this);

        /* Register Commands */
        new CommandHandler(this).registerCommands(ShortcutCommands.class);
    }
}
