package me.attex.main.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;

public class ScoreboardEvent implements Listener
{
    @EventHandler
    public void join(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final ScoreboardManager m = Bukkit.getScoreboardManager();
        final Scoreboard b = m.getNewScoreboard();
        final Objective o = b.registerNewObjective("authmescoreboard", "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName("§2§lAtteX");
        o.getScore("§2").setScore(8);
        o.getScore("§2§lPlease login:").setScore(7);
        o.getScore(ChatColor.WHITE + "/login").setScore(6);
        o.getScore("§c").setScore(5);
        o.getScore("§2§lOr register:").setScore(4);
        o.getScore(ChatColor.WHITE + "/register").setScore(3);
        o.getScore("§a").setScore(2);
        o.getScore("§7  ● ● §fmc.attex.eu§7 ● ●  ").setScore(1);
        player.setScoreboard(b);
    }
}
