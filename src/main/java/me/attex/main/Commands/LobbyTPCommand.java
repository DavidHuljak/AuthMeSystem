package me.attex.main.Commands;

import me.attex.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LobbyTPCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lobbytp")) {
            if (!(sender instanceof Player)) {
                System.out.println("You can only run this command as a player.");
            }
            Player player = (Player) sender;
            sendServer(player, "Lobby");
        }
        return true;
    }

    private void sendServer(Player player, String server) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(Main.getPlugin(), "Bungeecord", byteArrayOutputStream.toByteArray());
        player.sendMessage(ChatColor.GREEN + "§8[§2AuthMe§8]§7 Connecting to §2Lobby");
    }


}
