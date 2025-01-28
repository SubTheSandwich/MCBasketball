package me.sub.Basketball.Classes.Utils;

import me.sub.Basketball.Classes.Files.Court;
import org.bukkit.Location;
import org.bukkit.Material;

public class CourtData {

    private Court court;
    private boolean isClaiming;
    private Location claimLocationOne;
    private Location claimLocationTwo;
    private boolean closedManually;
    private Material pillarType;

    public CourtData() {
        this.court = null;
        isClaiming = false;
        claimLocationOne = null;
        claimLocationTwo = null;
        closedManually = true;
        pillarType = null;
    }

    public boolean isClaiming() {
        return isClaiming;
    }

    public void setClaiming(boolean claiming) {
        isClaiming = claiming;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Location getClaimLocationOne() {
        return claimLocationOne;
    }

    public void setClaimLocationOne(Location claimLocationOne) {
        this.claimLocationOne = claimLocationOne;
    }

    public Location getClaimLocationTwo() {
        return claimLocationTwo;
    }

    public void setClaimLocationTwo(Location claimLocationTwo) {
        this.claimLocationTwo = claimLocationTwo;
    }

    public boolean isClosedManually() {
        return closedManually;
    }

    public void setClosedManually(boolean closedManually) {
        this.closedManually = closedManually;
    }

    public Material getPillarType() {
        return pillarType;
    }

    public void setPillarType(Material pillarType) {
        this.pillarType = pillarType;
    }

    @Override
    public String toString() {
        return "CourtData{" +
                "court=" + court +
                ", isClaiming=" + isClaiming +
                ", claimLocationOne=" + claimLocationOne +
                ", claimLocationTwo=" + claimLocationTwo +
                ", closedManually=" + closedManually +
                '}';
    }
}
