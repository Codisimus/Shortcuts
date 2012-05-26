package com.codisimus.plugins.shortcuts;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player) || args.length == 0)
            return false;
        Player spyer = (Player)sender;
        Player victim = Shortcuts.server.matchPlayer(args[0]).get(0);
        if (victim == null)
            sender.sendMessage("The Player could not be found");
        else
            spyer.openInventory(victim.getInventory());
        return true;
    }
}