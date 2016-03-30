package com.codisimus.plugins.shortcuts;

import com.codisimus.plugins.shortcuts.CommandHandler.CodCommand;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Executes Player Commands
 *
 * @author Codisimus
 */
public class ShortcutCommands {
    private static final float DEFAULT_FLY_SPEED = 0.1F;
    private static final long DAY = 0L;
    private static final long NOON = 6000L;
    private static final long NIGHT = 18000L;
    private final Map<String, String> sendTo = new HashMap<>();

    @CodCommand(
        command = "fly",
        weight = 1,
        aliases = {"speed"},
        usage = {
            "§2<command> [Speed]§b Toggle high speed fly (Valid speeds are 1-10)"
        }
    )
    public boolean fly(Player player) {
        if (player.getFlySpeed() == DEFAULT_FLY_SPEED) {
            flyOn(player, 10);
            player.sendMessage("§5High Speed Fly on");
        } else {
            flyOff(player);
            player.sendMessage("§5High Speed Fly off");
        }
        return true;
    }
    @CodCommand(command = "fly", weight = 2)
    public boolean fly(Player player, int speed) {
        if (speed < 1 || speed > 10) {
            return false;
        }
        flyOn(player, speed);
        player.sendMessage("§5Fly speed set to §6" + speed);
        return true;
    }

    public boolean flyOn(Player player, int speed) {
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setFlySpeed(speed / 10F);
        return true;
    }

    public boolean flyOff(Player player) {
        player.setFlySpeed(DEFAULT_FLY_SPEED);
        if (player.getGameMode() != GameMode.CREATIVE) {
            player.setFlying(false);
            player.setAllowFlight(false);
        }
        return true;
    }

    @CodCommand(
        command = "disable",
        weight = 10,
        aliases = {"pd"},
        usage = {
            "§2<command> <Plugin>§b Disable a Plugin"
        }
    )
    public boolean disable(CommandSender sender, Plugin plugin) {
        Bukkit.getPluginManager().disablePlugin(plugin);
        sender.sendMessage("§5" + plugin.getName() + " is now disabled");
        return true;
    }

    @CodCommand(
        command = "enable",
        weight = 11,
        aliases = {"pe"},
        usage = {
            "§2<command> <Plugin>§b Enable a Plugin"
        }
    )
    public boolean enable(CommandSender sender, Plugin plugin) {
        Bukkit.getPluginManager().enablePlugin(plugin);
        sender.sendMessage("§5" + plugin.getName() + " is now enabled");
        return true;
    }

    @CodCommand(
        command = "prl",
        weight = 12,
        aliases = {"pluginreload", "pluginrl", "preload"},
        usage = {
            "§2<command> <Plugin>§b Reload a Plugin"
        }
    )
    public boolean pluginReload(CommandSender sender, Plugin plugin) {
        Bukkit.getPluginManager().disablePlugin(plugin);
        Bukkit.getPluginManager().enablePlugin(plugin);
        sender.sendMessage("§5" + plugin.getName() + " has been reloaded");
        return true;
    }

    @CodCommand(
        command = "td",
        weight = 20,
        usage = {
            "§2<command>§b Toggle downfall for the current World"
        }
    )
    public boolean toggleDownfall(CommandSender sender) {
        World world = sender instanceof Player
                        ? ((Player) sender).getWorld()
                        : Bukkit.getWorlds().get(0);
        world.setStorm(!world.hasStorm());
        Bukkit.broadcastMessage("Toggling downfall for World " + world.getName());
        return true;
    }

    @CodCommand(
        command = "day",
        aliases = {"dawn"},
        weight = 21,
        usage = {
            "§2<command> §b Set world time to 0"
        }
    )
    public boolean day(CommandSender sender) {
        setTime(sender, DAY);
        return true;
    }

    @CodCommand(
        command = "noon",
        weight = 22,
        usage = {
            "§2<command> §b Set world time to 6000"
        }
    )
    public boolean noon(CommandSender sender) {
        setTime(sender, NOON);
        return true;
    }

    @CodCommand(
        command = "night",
        weight = 23,
        usage = {
            "§2<command> §b Set world time to 18000"
        }
    )
    public boolean night(CommandSender sender) {
        setTime(sender, NIGHT);
        return true;
    }

    public boolean setTime(CommandSender sender, long time) {
        World world = sender instanceof Player
                        ? ((Player) sender).getWorld()
                        : Bukkit.getWorlds().get(0);
        world.setTime(time);
        sender.sendMessage("Time set to " + time + " for World " + world.getName());
        return true;
    }

    @CodCommand(
        command = "gm",
        weight = 30,
        usage = {
            "§2<command> [Player]§b Toggle gamemode for yourself or the given Player"
        }
    )
    public boolean toggleGamemode(Player player) {
        player.setGameMode(player.getGameMode() == GameMode.CREATIVE
                            ? GameMode.SURVIVAL
                            : GameMode.CREATIVE);
        player.sendMessage("§5Your Gamemode has been set to §6" + player.getGameMode().name());
        return true;
    }
    @CodCommand(command = "gm", weight = 31)
    public boolean toggleGamemode(CommandSender sender, Player player) {
        toggleGamemode(player);
        sender.sendMessage("§5" + player.getName() + " has been set to GameMode " + player.getGameMode().name());
        return true;
    }

    @CodCommand(
        command = "heal",
        weight = 32,
        usage = {
            "§2<command> [Player]§b Heal yourself or another Player"
        }
    )
    public boolean heal(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.sendMessage("§5You have been healed");
        return true;
    }
    @CodCommand(command = "heal", weight = 33)
    public boolean heal(CommandSender sender, Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        sender.sendMessage("§5" + player.getName() + " has been healed");
        player.sendMessage("§5You have been healed");
        return true;
    }

    @CodCommand(
        command = "setspawn",
        weight = 40,
        usage = {
            "§2<command>§b Set the spawn Location of the current World"
        }
    )
    public boolean setspawn(Player player) {
        Location spawn = player.getLocation();
        player.getWorld().setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
        player.sendMessage("§5The new spawn has been set");
        return true;
    }

    @CodCommand(
        command = "spawn",
        weight = 41,
        usage = {
            "§2<command>§b Teleport to the current World's spawn"
        }
    )
    public boolean spawn(Player player) {
        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        player.sendMessage("§5You have been sent to the spawn");
        return true;
    }

    @CodCommand(
        command = "tp",
        weight = 42,
        usage = {
            "§2<command> [PlayerA] <PlayerB>§b Teleport Player A (or yourself) to Player B"
        }
    )
    public boolean teleport(CommandSender sender, String partialPlayerAName, String partialPlayerBName) {
        Player toTele = Bukkit.getPlayer(partialPlayerAName);
        Player dest = Bukkit.getPlayer(partialPlayerBName);

        if (toTele != null && dest != null) {
            toTele.teleport(dest);
            sender.sendMessage("§5Teleporting §5" + toTele.getName() + "§5 to §6" + dest.getName());
        } else {
            sender.sendMessage("§4The Player could not be found");
        }
        return true;
    }
    @CodCommand(command = "tp", weight = 43)
    public boolean teleport(Player player, String partialPlayerName) {
        List<Player> playerList = Bukkit.matchPlayer(partialPlayerName);
        if (!playerList.isEmpty()) {
            player.teleport(playerList.get(0));
        } else {
            player.sendMessage("§4The Player could not be found");
        }
        return true;
    }

    @CodCommand(
        command = "tph",
        weight = 44,
        usage = {
            "§2<command> <Player>§b Teleport to the given Player"
        }
    )
    public boolean teleportHere(Player player, String partialPlayerName) {
        List<Player> playerList = Bukkit.matchPlayer(partialPlayerName);
        if (!playerList.isEmpty()) {
            playerList.get(0).teleport(player);
        } else {
            player.sendMessage("§4The Player could not be found");
        }
        return true;
    }

    @CodCommand(
        command = "spy",
        weight = 50,
        usage = {
            "§2<command> <Player>§b View the given Player's Inventory"
        }
    )
    public boolean spy(Player player, Player victim) {
        player.openInventory(victim.getInventory());
        return true;
    }

    @CodCommand(
        command = "w",
        aliases = {"whisper"},
        weight = 60,
        usage = {
            "§2<command> <Player> <Message>§b Send a private message to the given Player"
        },
        minArgs = 1
    )
    public boolean whisper(CommandSender sender, Player player, String[] args) {
        String msg = ":";
        for (int i = 1; i < args.length; i++) {
            msg = msg.concat(" " + args[i]);
        }
        player.sendMessage("§5Whipser from " + sender.getName() + msg);
        sender.sendMessage("§5Your message has been sent to " + player.getName());
        sendTo.put(player.getName(), sender.getName());
        return true;
    }

    @CodCommand(
        command = "re",
        aliases = {"reply"},
        weight = 61,
        usage = {
            "§2<command> <Message>§b Send a private message to last Player who whispered to you"
        },
        minArgs = 1
    )
    public boolean reply(CommandSender sender, String[] args) {
        String msg = ":";
        for (int i = 1; i < args.length; i++) {
            msg = msg.concat(" " + args[i]);
        }
        Player player = Bukkit.getPlayerExact(sendTo.get(sender.getName()));
        player.sendMessage("§5Whipser from " + sender.getName() + msg);
        sender.sendMessage("§5Your message has been sent to " + player.getName());
        sendTo.put(player.getName(), sender.getName());
        return true;
    }

    @CodCommand(
        command = "ban",
        aliases = {"tempban"},
        weight = 70,
        usage = {
            "§2<command> <Player>§b Ban the given Player"
        },
        minArgs = 1
    )
    public boolean ban(CommandSender sender, String[] args) {
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
