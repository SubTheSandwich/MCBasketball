package me.sub.Basketball.Commands;

import me.sub.Basketball.Classes.Files.Court;
import me.sub.Basketball.Classes.Files.GUI;
import me.sub.Basketball.Classes.Files.User;
import me.sub.Basketball.Classes.Utils.C;
import me.sub.Basketball.Classes.Files.Locale;
import me.sub.Basketball.Classes.Utils.CourtData;
import me.sub.Basketball.Classes.Utils.Permission;
import me.sub.Basketball.Classes.Utils.UserData;
import me.sub.Basketball.Main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class CourtCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(C.chat(Locale.get().getString("primary.not-player")));
            return true;
        }
        if (!Permission.check(p, "court")) {
            p.sendMessage(C.chat(Locale.get().getString("primary.no-permission")));
            return true;
        }
        if (args.length != 2) {
            p.sendMessage(C.chat(Locale.get().getString("commands.court.usage")));
            return true;
        }
        Court court = Court.getByName(args[1]);
        if (args[0].equalsIgnoreCase("create")) {
            if (court != null) {
                p.sendMessage(C.chat(Locale.get().getString("commands.court.exists")));
                return true;
            }
            UUID uuid = UUID.randomUUID();
            court = new Court(uuid);
            court.setup();
            court.get().set("uuid", uuid.toString());
            court.get().set("name", args[1]);
            court.save();
            p.sendMessage(C.chat(Locale.get().getString("commands.court.create.created").replace("%court%", args[1])));
            return true;
        }
        if (args[0].equalsIgnoreCase("delete")) {
            return true;
        }
        if (args[0].equalsIgnoreCase("modify")) {
            if (court == null) {
                p.sendMessage(C.chat(Locale.get().getString("commands.court.not-exists")));
                return true;
            }
            if (UserData.get(p.getUniqueId()).getCourtData() != null) {
                p.sendMessage(C.chat(Locale.get().getString("commands.court.modify.already")));
                return true;
            }
            UserData.get(p.getUniqueId()).setCourtData(new CourtData());
            UserData.get(p.getUniqueId()).getCourtData().setCourt(court);
            GUI.open(p, "modify.start");
            p.sendMessage(C.chat(Locale.get().getString("commands.court.modify.start").replace("%court%", court.getName())));
            return true;
        }
        p.sendMessage(C.chat(Locale.get().getString("commands.court.usage")));
        return false;
    }
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        return null;
    }
}
