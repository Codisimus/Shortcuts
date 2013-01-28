package com.codisimus.plugins.shortcuts;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if (player.getFlySpeed() == 1) {
            player.setFlySpeed(0.1F);
            player.setFlying(false);
            if (player.getGameMode() != GameMode.CREATIVE) {
                player.setAllowFlight(true);
            }
            player.sendMessage("§5High Speed Fly off");
        } else {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setFlySpeed(1);
            player.sendMessage("§5High Speed Fly on");
        }
        return true;
    }
}
