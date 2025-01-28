package me.sub.Basketball.Classes.Files;

import me.sub.Basketball.Classes.Utils.C;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI {

    public static FileConfiguration get() {
        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Basketball").getDataFolder(), "gui.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void save() {
        try {
            File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Basketball").getDataFolder(), "gui.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.save(file);
        } catch (IOException e) {
            System.out.println("Unable to save file locale.yml");
        }
    }

    public static void open(Player player, String gui) {
        Inventory inventory = Bukkit.createInventory(null, get().getInt("gui." + gui + ".size"), C.chat(get().getString("gui." + gui + ".name")));

        for (String s : get().getConfigurationSection("gui." + gui + ".items").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(get().getString("gui." + gui + ".items." + s + ".item")));
            ItemMeta meta = item.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            get().getStringList("gui." + gui + ".items." + s + ".lore").forEach(l -> lore.add(C.chat(l)));
            meta.setLore(lore);
            meta.setDisplayName(C.chat(get().getString("gui." + gui + ".items." + s + ".name")));
            meta.addItemFlags(ItemFlag.values());
            item.setItemMeta(meta);
            inventory.setItem(get().getInt("gui." + gui + ".items." + s + ".slot"), item);
        }
        if (get().getBoolean("filler.enabled")) {
            ItemStack fill = new ItemStack(Material.getMaterial(get().getString("filler.item")));
            ItemMeta meta = fill.getItemMeta();
            meta.setDisplayName(C.chat("&7"));
            fill.setItemMeta(meta);
            for (int i = 0; i < get().getInt("gui." + gui + ".size"); i++) {
                if (inventory.getItem(i) == null) inventory.setItem(i, fill);
            }
        }
        player.openInventory(inventory);
    }
}
