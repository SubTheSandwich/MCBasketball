package me.sub.Basketball.Classes.Files;

import me.sub.Basketball.Classes.Utils.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Items {

    public static FileConfiguration get() {
        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Basketball").getDataFolder(), "items.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void save() {
        try {
            File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Basketball").getDataFolder(), "items.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.save(file);
        } catch (IOException e) {
            System.out.println("Unable to save file locale.yml");
        }
    }

    public static ItemStack get(String name) {
        if (name == null) return null;
        if (!get().isConfigurationSection(name)) return null;
        ItemStack item = new ItemStack(Material.getMaterial(get().getString(name + ".item")));
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        get().getStringList(name + ".lore").forEach(l -> lore.add(C.chat(l)));
        meta.setLore(lore);
        meta.setDisplayName(C.chat(get().getString(name + ".name")));
        item.setItemMeta(meta);
        return item;
    }
}
