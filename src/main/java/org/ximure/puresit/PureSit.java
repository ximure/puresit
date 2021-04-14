package org.ximure.puresit;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PureSit extends JavaPlugin {

    // Registering sitting players array, command and eventlistener
    private final SittingPlayers sittingPlayers = new SittingPlayers();
    private final CommandSit commandSit = new CommandSit(sittingPlayers);
    private final EventListener eventListener = new EventListener(sittingPlayers);

    // Enabling /sit command and telling plugin that we'll have an eventlistener
    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("sit")).setExecutor(commandSit);
        getServer().getPluginManager().registerEvents(eventListener, this);
    }

    // Unregistering all event handlers on plugin disable
    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }
}
