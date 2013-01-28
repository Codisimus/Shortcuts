package com.codisimus.plugins.shortcuts;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class PluginReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) {
            return false;
        }
        Plugin plugin = Shortcuts.pm.getPlugin(args[0]);
        if (plugin == null) {
            sender.sendMessage("§4" + args[0] + " is not installed on the Server");
            return true;
        }
        Shortcuts.pm.disablePlugin(plugin);
        Shortcuts.pm.enablePlugin(plugin);
        sender.sendMessage("§5" + args[0] + " has been reloaded");
        return true;
    }
}
