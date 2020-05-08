package me.attex.main;

import me.attex.main.Commands.LobbyTPCommand;
import me.attex.main.Events.Events;
import me.attex.main.Events.ScoreboardEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;

    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nAuthMeSystem by Attex has been Enabled\n\n");
        registerEvents();
        registerCommands();
        getServer().getMessenger().registerOutgoingPluginChannel(this, "Bungeecord");
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nAuthMeSystem by Attex has been Disabled\n\n");
    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents((Listener)new Events(), (Plugin)this);
        pm.registerEvents((Listener)new ScoreboardEvent(), (Plugin)this);
    }

    public void registerCommands() {


    }
    public static Main getPlugin() {
        return plugin;
    }

}
