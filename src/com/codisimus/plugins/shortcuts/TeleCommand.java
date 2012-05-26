package com.codisimus.plugins.shortcuts;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Player toTele, dest;
        switch (args.length) {
            case 1:
                if (!(sender instanceof Player))
                    return false;
                Player player = Shortcuts.server.matchPlayer(args[0]).get(0);
                if (alias.equals("tp")) {
                    toTele = (Player)sender;
                    dest = player;
                }
                else {
                    toTele = player;
                    dest = (Player)sender;
                }
                break;
                
            case 2:
                toTele = Shortcuts.server.matchPlayer(args[0]).get(0);
                dest = Shortcuts.server.matchPlayer(args[0]).get(0);
                break;
                
            default: return false;
        }
        if (toTele == null || dest == null)
            sender.sendMessage("The Player could not be found");
        else
            toTele.teleport(dest);
        return true;
    }
}