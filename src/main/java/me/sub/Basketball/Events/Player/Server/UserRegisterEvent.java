package me.sub.Basketball.Events.Player.Server;

import me.sub.Basketball.Classes.Files.User;
import me.sub.Basketball.Classes.Utils.UserData;
import me.sub.Basketball.Main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserRegisterEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        User user = new User(p.getUniqueId());
        if (!user.exists()) {
            user.setup();
            user.get().set("uuid", p.getUniqueId().toString());
            user.get().set("name", p.getName());
            user.save();
        }
        if (!user.getName().equals(p.getName())) {
            user.get().set("name", p.getName());
            user.save();
        }
        Main.getInstance().data.put(p.getUniqueId(), new UserData());
    }
}
