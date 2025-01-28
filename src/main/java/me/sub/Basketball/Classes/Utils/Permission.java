package me.sub.Basketball.Classes.Utils;

import org.bukkit.entity.Player;

public class Permission {

    public static boolean check(Player player, String command) {
        return player.hasPermission("*") || player.isOp() || player.hasPermission("basketball.*") || player.hasPermission("basketball.command." + command);
    }
}
