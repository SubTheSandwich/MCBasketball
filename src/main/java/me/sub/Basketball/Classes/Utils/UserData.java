package me.sub.Basketball.Classes.Utils;

import me.sub.Basketball.Main.Main;

import java.util.UUID;

public class UserData {

    public CourtData courtData;

    public UserData() {
        courtData = null;
    }

    public CourtData getCourtData() {
        return courtData;
    }

    public void setCourtData(CourtData courtData) {
        this.courtData = courtData;
    }

    public static UserData get(UUID uuid) {
        return Main.getInstance().data.get(uuid);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "courtData=" + courtData +
                '}';
    }
}
