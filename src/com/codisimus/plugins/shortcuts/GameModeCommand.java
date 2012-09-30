package com.codisimus.plugins.shortcuts;

import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Player player = null;
        if (args.length == 0) {
            if (sender instanceof Player) {
                player = (Player) sender;
            }
        } else {
            List<Player> playerList = Shortcuts.server.matchPlayer(args[0]);
            if (!playerList.isEmpty()) {
                player = playerList.get(0);
            }
        }

        if (player == null) {
            sender.sendMessage("The Player could not be found");
        } else {
            player.setGameMode(player.getGameMode() == GameMode.CREATIVE
                                ? GameMode.SURVIVAL
                                : GameMode.CREATIVE);
            sender.sendMessage(player.getName() + " has been set to GameMode "
                                + player.getGameMode().name());
        }

        return true;
    }
}
