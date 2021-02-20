package me.huljak;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    final String prefix = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("prefix"));
    final String prefixClear = this.getConfig().getString("prefix-clear");

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(prefixClear + " Loaded!");
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.saveDefaultConfig();
        this.reloadConfig();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(prefixClear + " Disabled!");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        event.setJoinMessage(null);
        String title = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("join-title"));
        String subtitle = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("join-subtitle"));
        String message = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("join-message"));
        subtitle.replace("%player%", p.getDisplayName());
        TitleAPI.sendTitle(p, 20, 60, 20, title, subtitle);
        if (message != null) {
            p.sendMessage(prefix + " " + message);
        }
        p.setGameMode(GameMode.ADVENTURE);
        p.getActivePotionEffects().clear();
        p.getInventory().clear();
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setFlying(false);
        p.teleport(getSpawnLocation());
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(p);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(event.toWeatherState());
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
        if (event.getEntity() instanceof Player) {
            event.getEntity().setHealth(20);
            ((Player) event.getEntity()).setFoodLevel(20);
        }
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        try {
            Player p = event.getPlayer();
            TitleAPI.sendTitle(p, 20, 60, 20, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("logged-title")), ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("logged-subtitle")));
            p.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("logged-message")));
            Thread.sleep(1500L);
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(this.getConfig().getString("server"));
            event.getPlayer().sendPluginMessage(this, "BungeeCord", out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (sender.hasPermission(this.getConfig().getString("permission"))) {
                if (command.getName().equalsIgnoreCase("setspawn")) {
                    this.getConfig().set("spawn.x", p.getLocation().getX());
                    this.getConfig().set("spawn.y", p.getLocation().getY());
                    this.getConfig().set("spawn.z", p.getLocation().getZ());
                    this.getConfig().set("spawn.yaw", p.getLocation().getYaw());
                    this.getConfig().set("spawn.pitch", p.getLocation().getPitch());
                    this.getConfig().set("spawn.world", p.getLocation().getWorld().getName());
                    this.saveConfig();
                    this.reloadConfig();
                    p.sendMessage(prefix + " §aSpawn has been successfully set!");

                } else {
                    p.sendMessage(prefix + " §cInvalid arguments.");
                }
            } else {
                p.sendMessage(prefix + " §cYou don't have permission!");
            }
        }
        return false;
    }

    public Location getSpawnLocation() {
        double x = this.getConfig().getDouble("spawn.x", 0.0);
        double y = this.getConfig().getDouble("spawn.y", 0.0);
        double z = this.getConfig().getDouble("spawn.z", 0.0);
        World w = Bukkit.getWorld(this.getConfig().getString("spawn.world", "world"));
        float pitch = (float) this.getConfig().getDouble("spawn.pitch", 0.0);
        float yaw = (float) this.getConfig().getDouble("spawn.yaw", 0.0);
        Location spawn = new Location(w, x, y, z);
        spawn.setPitch(pitch);
        spawn.setYaw(yaw);
        if (this.getConfig().getConfigurationSection("spawn") == null || spawn == null) {
            return Bukkit.getWorld("world").getSpawnLocation();
        }
        return spawn;
    }

}
