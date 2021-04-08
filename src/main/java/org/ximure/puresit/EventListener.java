package org.ximure.puresit;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.UUID;

public class EventListener implements Listener {
    // Another sittingplayers constructor for eventlistener
    private final SittingPlayers sittingPlayers;
    public EventListener(SittingPlayers sittingPlayers) {
        this.sittingPlayers = sittingPlayers;
    }
    // If player leaves armor stand, we'll despawn it
    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        Entity armorStand = event.getDismounted();
        Entity sittingPlayer = event.getEntity();
        UUID playerUUID = sittingPlayer.getUniqueId();
        // If player was sitting, we'll destroy armor stand and remove player from sitting array
        if (sittingPlayers.isSitting(playerUUID)) {
            armorStand.remove();
            sittingPlayers.removeSittingStatus(playerUUID);
        }
    }
    // If player quits while sitting or something else goes wrong while he's disconnecting from server
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();
        sittingPlayers.removeSittingStatus(playerUUID);
    }
}
