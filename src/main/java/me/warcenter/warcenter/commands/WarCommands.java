package me.warcenter.warcenter.commands;

import me.warcenter.warcenter.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class WarCommands implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    int lobbyPlayers = 0;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command!");
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("hello")) {
            player.sendMessage(ChatColor.GREEN + "Hello " + player.getName());
        }

        if (cmd.getName().equalsIgnoreCase("war")) {
            player.sendMessage("List of uses: /WarSetMainLobby: sets lobby spawn");
        }

        if (cmd.getName().equalsIgnoreCase("AddPlayerToSpawn")) {
            if (!Main.inLobby.contains(player)) {
                Main.inMainLobby.add(player);
            }
        }

        if (cmd.getName().equalsIgnoreCase("WarSetMainLobby")) {
            Location loc = player.getLocation();
            double x = loc.getBlockX() + 0.5;
            double y = loc.getBlockY();
            double z = loc.getBlockZ() + 0.5;

            plugin.getConfig().set("Mainlobby.World", loc.getWorld().getName());
            plugin.getConfig().set("Mainlobby.x",  x);
            plugin.getConfig().set("Mainlobby.y",  y);
            plugin.getConfig().set("Mainlobby.z",  z);
            plugin.getConfig().set("Mainlobby.set", true);
            plugin.saveConfig();
            player.sendMessage("Set Lobby Spawn to: " + "X: " + x + " Y: " + y + " Z: " + z);
        }

        if (cmd.getName().equalsIgnoreCase("warreload")) {
            plugin.saveConfig();
            plugin.reloadConfig();
        }

        return true;
    }

}