package com.codisimus.plugins.shortcuts;

import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhisperCommand implements CommandExecutor {
    private HashMap<String, String> sendTo = new HashMap<String, String>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length < 2 && !(args.length == 1 && alias.equals("re")))
            return false;
        
        Player player = Shortcuts.server.matchPlayer(alias.equals("re") ? sendTo.get(sender.getName()) : args[0]).get(0);
        if (player == null)
            sender.sendMessage("The Player could not be found");
        else {
            String msg = ":";
            for (int i = 1; i < args.length; i++)
                msg = msg.concat(" ".concat(args[i]));
            player.sendMessage("§5Whipser from "+sender.getName()+msg);
            sender.sendMessage("§5Your message has been sent to "+player.getName());
            sendTo.put(player.getName(), sender.getName());
        }
        return true;
    }
}