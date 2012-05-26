package com.codisimus.plugins.shortcuts;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DownfallCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        World world = sender instanceof Player ? ((Player)sender).getWorld() : Shortcuts.server.getWorlds().get(0);
        world.setStorm(!world.hasStorm());
        sender.sendMessage("Toggeling downfall for World "+world.getName());
        return true;
    }
}