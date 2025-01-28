package me.sub.Basketball.Events.Player.GUI.Close;

import me.sub.Basketball.Classes.Files.GUI;
import me.sub.Basketball.Classes.Utils.CourtData;
import me.sub.Basketball.Classes.Utils.UserData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ModifyCloseEvent implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (!e.getView().getTitle().equalsIgnoreCase(GUI.get().getString("gui.modify.start.name"))) return;
        if (UserData.get(p.getUniqueId()).getCourtData() == null) return;
        CourtData data = UserData.get(p.getUniqueId()).getCourtData();
        if (data.isClosedManually()) UserData.get(p.getUniqueId()).setCourtData(null);
    }
}
