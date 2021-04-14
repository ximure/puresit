package org.ximure.puresit;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.UUID;

public class EventListener implements Listener {

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

    // If player leaves game while sitting, armor stand which player sits on we'll be removed
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();
        Entity armorStand = event.getPlayer().getVehicle();
        assert armorStand != null;
        if (sittingPlayers.isSitting(playerUUID)) {
            armorStand.remove();
            sittingPlayers.removeSittingStatus(playerUUID);
        }
    }

    // If player breaks a block underneath him while sitting armor stand we'll be removed
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Entity armorStand = event.getPlayer().getVehicle();
        assert armorStand != null;
        if (sittingPlayers.isSitting(player.getUniqueId())) {
            // Getting what block below player
            Block block = player.getLocation().subtract(0, -1, 0).getBlock();
            // If it's air, then we'll remove armor stand
            if (block.getType().isAir()) {
                sittingPlayers.removeSittingStatus(player.getUniqueId());
                armorStand.remove();
            }
        }
    }
}
