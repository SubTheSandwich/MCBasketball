package me.sub.Basketball.Events.Player.Interact;

import me.sub.Basketball.Classes.Files.Court;
import me.sub.Basketball.Classes.Files.Items;
import me.sub.Basketball.Classes.Files.Locale;
import me.sub.Basketball.Classes.Utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.util.Iterator;

public class PlayerClaimEvents implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (e.getItem() == null) return;
        if (!e.getItem().equals(Items.get("claim-wand"))) return;
        if (UserData.get(p.getUniqueId()).getCourtData() == null) return;
        e.setCancelled(true);
        CourtData data = UserData.get(p.getUniqueId()).getCourtData();
        if (data.getCourt() == null) return;
        if (!data.isClaiming()) return;
        if (a.equals(Action.LEFT_CLICK_AIR)) {
            if (!p.isSneaking()) return;
            if (data.getClaimLocationOne() == null || data.getClaimLocationTwo() == null) {
                p.sendMessage(C.chat(Locale.get().getString("events.claim.fail.missing")));
                return;
            }
            if (data.getClaimLocationOne().getWorld() != data.getClaimLocationTwo().getWorld()) {
                p.sendMessage(C.chat(Locale.get().getString("events.claim.fail.world")));
                return;
            }
            Cuboid cuboid = new Cuboid(data.getClaimLocationOne(), data.getClaimLocationTwo());
            File[] courts = new File(Bukkit.getServer().getPluginManager().getPlugin("Basketball").getDataFolder().getPath() + "/data/courts").listFiles();
            if (courts != null) {
                for (File f : courts) {
                    YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
                    if (file.getString("name").equalsIgnoreCase(data.getCourt().getName())) continue;
                    if (!file.isConfigurationSection("claim")) continue;
                    if (!file.getString("claim.world").equalsIgnoreCase(data.getClaimLocationOne().getWorld().getName())) continue;
                    Location locationOne = new Location(Bukkit.getWorld(file.getString("claim.world")), file.getInt("claim.1.x"), 0, file.getInt("claim.1.z"));
                    Location locationTwo = new Location(Bukkit.getWorld(file.getString("claim.world")), file.getInt("claim.2.x"), 0, file.getInt("claim.2.z"));
                    Cuboid other = new Cuboid(locationOne, locationTwo);
                    if (cuboid.intersects(other)) {
                        p.sendMessage(C.chat(Locale.get().getString("events.claim.fail.collides")));
                        return;
                    }
                }
            }
        }
        if (a.equals(Action.LEFT_CLICK_BLOCK)) {
            Location location = e.getClickedBlock().getLocation();
            Location check = location.clone();
            check.setY(0);
            if (Claim.getAtLocation(check) != null) {
                Court court = Claim.getAtLocation(check);
                if (!court.getName().equalsIgnoreCase(data.getCourt().getName())) {
                    p.sendMessage(C.chat(Locale.get().getString("events.claim.fail.collides")));
                    return;
                }
            }
            if (data.getClaimLocationTwo() != null) {
                if (p.getWorld() != data.getClaimLocationTwo().getWorld()) {
                    p.sendMessage(C.chat(Locale.get().getString("events.claim.fail.world")));
                    return;
                }
                if (data.getClaimLocationOne() != null) {
                    Cuboid cuboid = new Cuboid(data.getClaimLocationOne(), data.getClaimLocationTwo());
                    for (Iterator<Location> it = cuboid.corners(); it.hasNext();) {
                        Location loc = it.next();
                        for (int i = loc.getWorld().getMinHeight(); i < loc.getWorld().getMaxHeight(); i++) {
                            Location at = new Location(loc.getWorld(), loc.getX(), i, loc.getZ());
                            if (at.getBlock().getType().isAir()) p.sendBlockChange(at, Material.AIR.createBlockData());
                        }
                    }
                }
            }
            if (data.getPillarType() == null) {
                data.setPillarType(Claim.getRandomPillarType());
            }
            p.sendMessage(C.chat(Locale.get().getString("events.claim.select.one")));
            if (data.getClaimLocationOne() != null) {
                for (int i = p.getWorld().getMinHeight(); i < p.getWorld().getMaxHeight(); i++) {
                    Location at = new Location(p.getWorld(), data.getClaimLocationOne().getX(), i, data.getClaimLocationOne().getZ());
                    if (at.getBlock().getType().isAir()) p.sendBlockChange(at, Material.AIR.createBlockData());
                }
            }
            data.setClaimLocationOne(e.getClickedBlock().getLocation());
            Material pillar = data.getPillarType();
            if (data.getClaimLocationTwo() != null) {
                Cuboid cuboid = new Cuboid(data.getClaimLocationOne(), data.getClaimLocationTwo());
                for (Iterator<Location> it = cuboid.corners(); it.hasNext(); ) {
                    Location loc = it.next();
                    for (int i = loc.getWorld().getMinHeight(); i < loc.getWorld().getMaxHeight(); i++) {
                        Location at = new Location(loc.getWorld(), loc.getX(), i, loc.getZ());
                        if (at.getBlock().getType().isAir()) {
                            if (i % 3 == 0) {
                                p.sendBlockChange(at, pillar.createBlockData());
                            } else {
                                p.sendBlockChange(at, Material.GLASS.createBlockData());
                            }
                        }
                    }
                }
                return;
            }
            for (int i = p.getWorld().getMinHeight(); i < p.getWorld().getMaxHeight(); i++) {
                Location at = new Location(p.getWorld(), e.getClickedBlock().getLocation().getX(), i, e.getClickedBlock().getLocation().getZ());
                if (at.getBlock().getType().isAir()) {
                    if (i % 3 == 0) {
                        p.sendBlockChange(at, pillar.createBlockData());
                    } else {
                        p.sendBlockChange(at, Material.GLASS.createBlockData());
                    }
                }
            }
            return;
        }
        if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
            Location location = e.getClickedBlock().getLocation();
            Location check = location.clone();
            check.setY(0);
            if (Claim.getAtLocation(check) != null) {
                Court court = Claim.getAtLocation(check);
                if (!court.getName().equalsIgnoreCase(data.getCourt().getName())) {
                    p.sendMessage(C.chat(Locale.get().getString("events.claim.fail.collides")));
                    return;
                }
            }
            if (data.getClaimLocationOne() != null) {
                if (p.getWorld() != data.getClaimLocationOne().getWorld()) {
                    p.sendMessage(C.chat(Locale.get().getString("events.claim.fail.world")));
                    return;
                }
                if (data.getClaimLocationTwo() != null) {
                    Cuboid cuboid = new Cuboid(data.getClaimLocationOne(), data.getClaimLocationTwo());
                    for (Iterator<Location> it = cuboid.corners(); it.hasNext(); ) {
                        Location loc = it.next();
                        for (int i = loc.getWorld().getMinHeight(); i < loc.getWorld().getMaxHeight(); i++) {
                            Location at = new Location(loc.getWorld(), loc.getX(), i, loc.getZ());
                            if (at.getBlock().getType().isAir()) p.sendBlockChange(at, Material.AIR.createBlockData());
                        }
                    }
                }
            }
            p.sendMessage(C.chat(Locale.get().getString("events.claim.select.two")));
            if (data.getClaimLocationTwo() != null) {
                for (int i = p.getWorld().getMinHeight(); i < p.getWorld().getMaxHeight(); i++) {
                    Location at = new Location(p.getWorld(), data.getClaimLocationTwo().getX(), i, data.getClaimLocationTwo().getZ());
                    if (at.getBlock().getType().isAir()) p.sendBlockChange(at, Material.AIR.createBlockData());
                }
            }
            data.setClaimLocationTwo(e.getClickedBlock().getLocation());
            if (data.getPillarType() == null) {
                data.setPillarType(Claim.getRandomPillarType());
            }
            Material pillar = data.getPillarType();
            if (data.getClaimLocationOne() != null) {
                Cuboid cuboid = new Cuboid(data.getClaimLocationOne(), data.getClaimLocationTwo());
                for (Iterator<Location> it = cuboid.corners(); it.hasNext(); ) {
                    Location loc = it.next();
                    for (int i = loc.getWorld().getMinHeight(); i < loc.getWorld().getMaxHeight(); i++) {
                        Location at = new Location(loc.getWorld(), loc.getX(), i, loc.getZ());
                        if (at.getBlock().getType().isAir()) {
                            if (i % 3 == 0) {
                                p.sendBlockChange(at, pillar.createBlockData());
                            } else {
                                p.sendBlockChange(at, Material.GLASS.createBlockData());
                            }
                        }
                    }
                }
                return;
            }
            for (int i = p.getWorld().getMinHeight(); i < p.getWorld().getMaxHeight(); i++) {
                Location at = new Location(p.getWorld(), e.getClickedBlock().getLocation().getX(), i, e.getClickedBlock().getLocation().getZ());
                if (at.getBlock().getType().isAir()) {
                    if (i % 3 == 0) {
                        p.sendBlockChange(at, pillar.createBlockData());
                    } else {
                        p.sendBlockChange(at, Material.GLASS.createBlockData());
                    }
                }
            }
            return;
        }
    }
}
