package me.sub.Basketball.Classes.Utils;

import me.sub.Basketball.Classes.Files.Court;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Claim {

    public static Court getAtLocation(Location location) {
        Location check = location.clone();
        location.setY(0);
        File[] courts = new File(Bukkit.getServer().getPluginManager().getPlugin("Basketball").getDataFolder().getPath() + "/data/courts").listFiles();
        if (courts == null) return null;
        for (File f : courts) {
            YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
            if (!file.isConfigurationSection("claim")) continue;
            if (!file.getString("claim.world").equalsIgnoreCase(check.getWorld().getName())) continue;
            Location locationOne = new Location(Bukkit.getWorld(file.getString("claim.world")), file.getInt("claim.1.x"), 0, file.getInt("claim.1.z"));
            Location locationTwo = new Location(Bukkit.getWorld(file.getString("claim.world")), file.getInt("claim.2.x"), 0, file.getInt("claim.2.z"));
            Cuboid cuboid = new Cuboid(locationOne, locationTwo);
            if (!cuboid.isIn(check)) continue;
            return new Court(UUID.fromString(file.getString("uuid")));
        }
        return null;
    }

    public static Material getRandomPillarType() {
        List<Material> blocks = new ArrayList<>();
        for (Material material : Material.values()) {
            if (!material.isBlock()) continue;
            if (material.isAir()) continue;
            if (material.isEdible()) continue;
//            if (material.isItem()) continue;
//            if (material.isRecord()) continue;
//            if (!material.isSolid()) continue;
//            if (Tag.TRAPDOORS.isTagged(material)) continue;
//            if (Tag.DOORS.isTagged(material)) continue;
//            if (Tag.RAILS.isTagged(material)) continue;
//            if (Tag.PRESSURE_PLATES.isTagged(material)) continue;
//            if (Tag.BUTTONS.isTagged(material)) continue;
//            if (Tag.SLABS.isTagged(material)) continue;
//            if (Tag.STAIRS.isTagged(material)) continue;
//            if (Tag.ALL_SIGNS.isTagged(material)) continue;
//            if (Tag.CAMPFIRES.isTagged(material)) continue;
//            if (Tag.CROPS.isTagged(material)) continue;
//            if (Tag.FENCE_GATES.isTagged(material)) continue;
//            if (Tag.FENCES.isTagged(material)) continue;
//            if (Tag.FLOWERS.isTagged(material)) continue;
//            if (Tag.WOOL_CARPETS.isTagged(material)) continue;
            if (material.name().toLowerCase().contains("glass")) continue;
            blocks.add(material);
        }
        return blocks.get(new Random().nextInt(blocks.size()));
    }
}
