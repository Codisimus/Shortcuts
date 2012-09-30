package com.codisimus.plugins.shortcuts;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Player toTele = null;
        Player dest = null;
        switch (args.length) {
        case 1:
            if (!(sender instanceof Player)) {
                return false;
            }

            Player player;
            List<Player> playerList = Shortcuts.server.matchPlayer(args[0]);
            if (playerList.isEmpty()) {
                break;
            }
            player = playerList.get(0);

            if (alias.equals("tp")) {
                toTele = (Player)sender;
                dest = player;
            } else { //tph
                toTele = player;
                dest = (Player)sender;
            }
            break;

        case 2:
            playerList = Shortcuts.server.matchPlayer(args[0]);
            if (playerList.isEmpty()) {
                break;
            }
            toTele = playerList.get(0);
            playerList = Shortcuts.server.matchPlayer(args[1]);
            if (playerList.isEmpty()) {
                break;
            }
            dest = playerList.get(0);
            break;

        default: return false;
        }
        if (toTele == null || dest == null) {
            sender.sendMessage("The Player could not be found");
        } else {
            toTele.teleport(dest);
        }
        return true;
    }
}
