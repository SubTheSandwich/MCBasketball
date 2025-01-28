package me.sub.Basketball.Events.Player.GUI.Click;

import me.sub.Basketball.Classes.Files.GUI;
import me.sub.Basketball.Classes.Files.Items;
import me.sub.Basketball.Classes.Files.Locale;
import me.sub.Basketball.Classes.Utils.C;
import me.sub.Basketball.Classes.Utils.CourtData;
import me.sub.Basketball.Classes.Utils.UserData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ModifyClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null) return;
        if (e.getCurrentItem() == null) return;
        if (!e.getView().getTitle().equalsIgnoreCase(C.chat(GUI.get().getString("gui.modify.start.name")))) return;
        if (UserData.get(p.getUniqueId()).getCourtData() == null) return;
        e.setCancelled(true);
        CourtData data = UserData.get(p.getUniqueId()).getCourtData();
        if (e.getCurrentItem().getType().toString().equalsIgnoreCase(GUI.get().getString("gui.modify.start.items.claim.item")) && e.getCurrentItem().getItemMeta().getDisplayName().equals(C.chat(GUI.get().getString("gui.modify.start.items.claim.name")))) {
            if (data.isClaiming()) {
                p.closeInventory();
                p.sendMessage(C.chat(Locale.get().getString("commands.court.modify.claim.already")));
                return;
            }
            if (p.getInventory().firstEmpty() == -1) {
                p.closeInventory();
                p.sendMessage(C.chat(Locale.get().getString("primary.inventory-full")));
                return;
            }
            data.setClaiming(true);
            data.setClosedManually(false);
            p.sendMessage(C.chat(Locale.get().getString("events.claim.given")));
            p.getInventory().addItem(Items.get("claim-wand"));
            p.closeInventory();
        }
    }
}
