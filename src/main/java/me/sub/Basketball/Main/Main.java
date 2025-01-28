package me.sub.Basketball.Main;

import me.sub.Basketball.Classes.Utils.UserData;
import me.sub.Basketball.Commands.CourtCommand;
import me.sub.Basketball.Events.Player.GUI.Click.ModifyClickEvent;
import me.sub.Basketball.Events.Player.GUI.Close.ModifyCloseEvent;
import me.sub.Basketball.Events.Player.Interact.PlayerClaimEvents;
import me.sub.Basketball.Events.Player.Server.UserRegisterEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    public HashMap<UUID, UserData> data = new HashMap<>();
    private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        files();
        events();
        commands();
    }

    @Override
    public void onDisable() {

    }

    private void commands() {
        getCommand("court").setExecutor(new CourtCommand()); getCommand("court").setTabCompleter(new CourtCommand());
    }

    private void events() {
        PluginManager pm = getServer().getPluginManager();

        // User
        pm.registerEvents(new UserRegisterEvent(), this);
        pm.registerEvents(new PlayerClaimEvents(), this);

        // GUIs
        pm.registerEvents(new ModifyClickEvent(), this);
        pm.registerEvents(new ModifyCloseEvent(), this);
    }

    private void files() {
        saveResource("locale.yml", false);
        saveResource("gui.yml", false);
        saveResource("config.yml", false);
        saveResource("items.yml", false);
    }
}
