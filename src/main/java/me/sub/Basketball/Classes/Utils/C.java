package me.sub.Basketball.Classes.Utils;

import org.bukkit.ChatColor;

public class C {
    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String strip(String s) {
        return ChatColor.stripColor(s);
    }
}
