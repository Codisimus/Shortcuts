package com.codisimus.plugins.shortcuts;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Long time = alias.equals("day") ? 0 : 18000L;
        World world = sender instanceof Player
                        ? ((Player) sender).getWorld()
                        : Shortcuts.server.getWorlds().get(0);
        world.setTime(time);
        sender.sendMessage("Time set to " + time + " for World " + world.getName());
        return true;
    }
}
