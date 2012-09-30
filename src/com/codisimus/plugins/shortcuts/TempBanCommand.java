package com.codisimus.plugins.shortcuts;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TempBanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        int i = 1;
        if (args.length < 2 || !APITools.isNumerical(args[i].charAt(0))) {
            return false;
        }

        while (i < args.length) {
            i++;
            if (!APITools.isNumerical(args[i].charAt(0))) {
                break;
            }
        }

        Bukkit.broadcastMessage(BanHandler.banPlayer(args[0],
                APITools.concatArgs(args, 1, i - 1),
                APITools.concatArgs(args, i)));
        return true;
    }
}
