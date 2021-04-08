package org.ximure.puresit;

import java.util.ArrayList;
import java.util.UUID;

public class SittingPlayers {

    private final ArrayList<UUID> sittingPlayers = new ArrayList<>();

    public void addSitting(UUID playerUUID) {
        sittingPlayers.add(playerUUID);
    }

    public boolean isSitting(UUID playerUUID) {
        return sittingPlayers.contains(playerUUID);
    }

    public void removeSittingStatus(UUID playerUUID) {
        sittingPlayers.remove(playerUUID);
    }

    public void clearSittingStatuses() {
        sittingPlayers.clear();
    }
}
