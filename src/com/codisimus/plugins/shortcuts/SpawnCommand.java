package com.codisimus.plugins.shortcuts;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        player.teleport(Shortcuts.server.getWorlds().get(0).getSpawnLocation());
        player.sendMessage("§5You have been sent to the spawn");
        return true;
    }
}
