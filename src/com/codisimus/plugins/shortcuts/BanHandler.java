package com.codisimus.plugins.shortcuts;

import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BanHandler implements Listener {
    static HashMap<String, Long> bannedPlayers = new HashMap<String, Long>();

    public static void load() {
        File file = new File(Shortcuts.dataFolder + "/bannedPlayers.dat");
        if (!file.exists()) {
            return;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            bannedPlayers = (HashMap<String, Long>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                fis.close();
            } catch (Exception e) {
            }
        }
    }

    public static void save() {
        File file = new File(Shortcuts.dataFolder + "/bannedPlayers.dat");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(bannedPlayers);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void banEnforcer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!bannedPlayers.containsKey(playerName)) {
            return;
        }

        long future = bannedPlayers.get(playerName);
        long current = System.currentTimeMillis();
        if (current < future) {
            player.kickPlayer("You are banned for " + APITools.getTimeRemaining(future));
        } else {
            bannedPlayers.remove(playerName);
        }
    }

    public static String banPlayer(String playerName, String time, String reason) {
        Calendar future = APITools.getFutureTime(time);
        bannedPlayers.put(playerName, future.getTimeInMillis());

        String banMessage = "been banned for " + APITools.getTimeDifference(future, Calendar.getInstance()) + " for " + reason;

        Player player = Shortcuts.server.getPlayer(playerName);
        if (player != null) {
            player.kickPlayer("You have " + banMessage);
        }

        save();
        return playerName + " has " + banMessage;
    }
}
