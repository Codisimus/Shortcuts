package com.codisimus.plugins.shortcuts;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        Location spawn = player.getLocation();
        player.getWorld().setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
        player.sendMessage("§5The new spawn has been set");
        return true;
    }
}
