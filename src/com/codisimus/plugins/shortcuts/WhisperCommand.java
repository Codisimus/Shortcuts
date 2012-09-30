package com.codisimus.plugins.shortcuts;

import java.util.HashMap;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhisperCommand implements CommandExecutor {
    private HashMap<String, String> sendTo = new HashMap<String, String>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length < 2 && !(args.length == 1 && alias.equals("re"))) {
            return false;
        }

        Player player = null;
        if (alias.equals("re")) {
            player = Shortcuts.server.getPlayer(sendTo.get(sender.getName()));
        } else {
            List<Player> playerList = Shortcuts.server.matchPlayer(args[0]);
            if (!playerList.isEmpty()) {
                player = playerList.get(0);
            }
        }

        if (player == null) {
            sender.sendMessage("The Player could not be found");
        } else {
            String msg = ":";
            for (int i = 1; i < args.length; i++) {
                msg = msg.concat(" ".concat(args[i]));
            }
            player.sendMessage("§5Whipser from " + sender.getName() + msg);
            sender.sendMessage("§5Your message has been sent to " + player.getName());
            sendTo.put(player.getName(), sender.getName());
        }
        return true;
    }
}
