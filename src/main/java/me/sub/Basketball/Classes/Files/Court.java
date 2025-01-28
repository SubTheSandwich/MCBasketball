package me.sub.Basketball.Classes.Files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Court {

    File file;
    FileConfiguration customFile;
    UUID id;

    public Court(UUID uuid) {
        id = uuid;
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Basketball").getDataFolder().getPath() + "/data/courts/", id + ".yml");
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public Boolean exists() {
        return file.exists();
    }
    public void setup() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {

            }
        }
    }

    public FileConfiguration get() {
        return customFile;
    }

    public void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.println("Couldn't save file");
        }
    }

    public void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static Court getByName(String name) {
        File[] factions = new File(Bukkit.getServer().getPluginManager().getPlugin("Basketball").getDataFolder().getPath() + "/data/courts").listFiles();
        if (factions != null) {
            for (File f : factions) {
                YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                if (file.getString("name").equalsIgnoreCase(name.toLowerCase())) {
                    return new Court(UUID.fromString(file.getString("uuid")));
                }
            }
        }
        return null;
    }

    public String getName() {
        return customFile.getString("name");
    }

    public UUID getUUID() {
        return id;
    }

    @Override
    public String toString() {
        return "Court{" +
                "id=" + getUUID().toString() +
                '}';
    }
}
