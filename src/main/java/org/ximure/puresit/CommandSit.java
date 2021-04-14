package org.ximure.puresit;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class CommandSit implements CommandExecutor {

    // Constructor for sittingplayers methods
    private final SittingPlayers sittingPlayers;
    public CommandSit(SittingPlayers sittingPlayers) {
        this.sittingPlayers = sittingPlayers;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            final UUID playerUUID = player.getUniqueId();

            // If player is already sitting, we'll do nothing
            if (sittingPlayers.isSitting(playerUUID))
                player.sendMessage("[Pure] Вы уже сидите");
            // Or if player isn't on ground
            else if (!player.isOnGround() && !sittingPlayers.isSitting(playerUUID))
                player.sendMessage("[Pure] Вы не можете сесть в полёте");
            else {
                final World world = getServer().getWorld("world");
                assert world != null;
                // Adding player to sitting array
                sittingPlayers.addSitting(playerUUID);
                // Getting player location and setting it to a little bit below armor stand height
                Location playerLocation = player.getLocation();
                playerLocation.setY(playerLocation.getY() - 1.6);
                // Spawning armor stand, setting up its properties and put player on top of it
                ArmorStand armorStand = (ArmorStand) world.spawnEntity(playerLocation, EntityType.ARMOR_STAND);
                armorStand.setVisible(false);
                armorStand.setGravity(false);
                armorStand.addPassenger(player);
                player.sendMessage("[Pure] Вы сели");
            }
        }
        return true;
    }
}