package com.codisimus.plugins.shortcuts;

import java.io.*;
import java.util.Calendar;
import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class APITools {
    static boolean useWholeNumbers;

    public static boolean inventoryHasItem(Inventory inv, int id, short durability, int amount) {
	int amountHolding = 0;
	for (ItemStack item: inv.all(id).values()) {
            if (item.getDurability() == durability) {
		amountHolding = amountHolding + item.getAmount();
            }
        }
	return amountHolding >= amount;
    }

    public static void updateCompases(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            Location l = players[i].getLocation();
            double shortestDistance = 99999;
            int nearestPlayer = i;
            for (int j = 0; j < players.length; j++) {
                double distanceToPlayer = l.distance(players[j].getLocation());
                if (distanceToPlayer < shortestDistance && i != j) {
                    nearestPlayer = j;
                    shortestDistance = distanceToPlayer;
                }
            }

            players[i].setCompassTarget(players[nearestPlayer].getLocation());
       }
    }

    public static String concatArgs(String[] args, int first) {
        return concatArgs(args, first, args.length - 1);
    }

    public static String concatArgs(String[] args, int first, int last) {
        String string = "";
        for (int i = first; i <= last; i++) {
            string = string + " " + args[i];
        }
        return string.isEmpty() ? string : string.substring(1);
    }

    public static boolean isNumerical(String string) {
        if (string.isEmpty()) {
            return false;
        }

        for (char c: string.toCharArray()) {
            if (!isNumerical(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNumerical(char c) {
        switch (c) {
        case 0: //Fall through
        case 1: //Fall through
        case 2: //Fall through
        case 3: //Fall through
        case 4: //Fall through
        case 5: //Fall through
        case 6: //Fall through
        case 7: //Fall through
        case 8: //Fall through
        case 9: return true;
        default: return false;
        }
    }

    /**
     * Trims the money amount down
     * Casts to int if WholeNumbers is set to true in the config
     * Gets rid of all but two places after the decimal
     *
     * @param money The double value that will be trimmed
     * @return The double value that has been trimmed
     */
    public static double trim(double money) {
        if (useWholeNumbers) {
            return (int) money;
        }

        //Get rid of numbers after the 100ths decimal place
        return ((long) (money * 100)) / 100;
    }

    /**
     * Adds various Unicode characters and colors to a string
     *
     * @param string The string being formated
     * @return The formatted String
     */
    public static String format(String string) {
        return string.replace("&", "§").replace("<ae>", "æ").replace("<AE>", "Æ")
                .replace("<o/>", "ø").replace("<O/>", "Ø")
                .replace("<a>", "å").replace("<A>", "Å");
    }

    /**
     * Returns the remaining time until the Button resets
     * Returns null if the Button never resets
     *
     * @param time The given time
     * @return the remaining time until the Button resets
     */
    public static String getTimeRemaining(long time) {
        long timeRemaining = time - System.currentTimeMillis();

        if (timeRemaining > DateUtils.MILLIS_PER_DAY) {
            return (int) timeRemaining / DateUtils.MILLIS_PER_DAY + " day(s)";
        } else if (timeRemaining > DateUtils.MILLIS_PER_HOUR) {
            return (int) timeRemaining / DateUtils.MILLIS_PER_HOUR + " hour(s)";
        } else if (timeRemaining > DateUtils.MILLIS_PER_MINUTE) {
            return (int) timeRemaining / DateUtils.MILLIS_PER_MINUTE + " minute(s)";
        } else if (timeRemaining > DateUtils.MILLIS_PER_SECOND) {
            return (int) timeRemaining / DateUtils.MILLIS_PER_SECOND + " second(s)";
        } else {
            return "1 second";
        }
    }

    public static String getTimeDifference(Calendar timeInFuture, Calendar currentTime) {
        if (!currentTime.before(timeInFuture)) {
            return "0 seconds";
        }

        int futureYear = timeInFuture.get(Calendar.YEAR);
        int futureDay = timeInFuture.get(Calendar.DAY_OF_YEAR);
        int futureHour = timeInFuture.get(Calendar.HOUR_OF_DAY);
        int futureMinute = timeInFuture.get(Calendar.MINUTE);
        int futureSecond = timeInFuture.get(Calendar.SECOND);

        int currentYear = currentTime.get(Calendar.YEAR);
        int currentDay = currentTime.get(Calendar.DAY_OF_YEAR);
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);
        int currentSecond = currentTime.get(Calendar.SECOND);

        String timeString = "";
        if (currentYear < futureYear) {
            timeString = timeString.concat((futureYear - currentYear) + " years, ");
        }
        if (currentDay < futureDay) {
            timeString = timeString.concat((futureDay - currentDay) + " days, ");
        }
        if (currentHour < futureHour) {
            timeString = timeString.concat((futureHour - currentHour) + " hours, ");
        }
        if (currentMinute < futureMinute) {
            timeString = timeString.concat((futureMinute - currentMinute) + " minutes, ");
        }

        return timeString.concat((futureSecond - currentSecond) + " seconds");
    }

    public static Calendar getFutureTime(String offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, toSeconds(offset));
        return calendar;
    }

    /**
     * Returns the number of the current day in the AD time period
     *
     * @return The number of the current day in the AD time period
     */
    public static int getDayAD() {
        Calendar calendar = Calendar.getInstance();
        int yearAD = calendar.get(Calendar.YEAR);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        return (int) ((yearAD - 1) * 365.4) + dayOfYear;
    }

    public static int toSeconds(String time) {
        int seconds = 0;
        for (String field: time.split(" ")) {
            int multiplier;
            int length = field.length();
            switch (field.charAt(length)) {
            case 'y': multiplier = 31536000; break;
            case 'd': multiplier = 86400; break;
            case 'h': multiplier = 3600; break;
            case 'm': multiplier = 60; break;
            case 's': multiplier = 1; break;
            default: return -1;
            }
            try {
                seconds = seconds + (Integer.parseInt(field.substring(0, length - 1)) * multiplier);
            } catch (Exception ex) {
                return -1;
            }
        }
        return seconds;
    }

    public static void copyFolder(File src, File dest) throws IOException {
    	if (src.isDirectory()) {
            //if directory does not exist, create it
            if (!dest.exists()) {
                dest.mkdir();
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile,destFile);
            }
    	} else {
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
    	}
    }

    public void delete(File file) {
        if (file.isDirectory()) {
            for (File f: file.listFiles()) {
                delete(f);
            }
        }
        file.delete();
    }
}
