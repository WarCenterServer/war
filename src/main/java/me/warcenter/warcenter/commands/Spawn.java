package me.warcenter.warcenter.commands;

import me.warcenter.warcenter.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Spawn implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command!");
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (Main.inLobby.contains(player)) {
                double x = plugin.getConfig().getDouble("Mainlobby.x");
                double y = plugin.getConfig().getDouble("Mainlobby.y");
                double z = plugin.getConfig().getDouble("Mainlobby.z");
                String configWorld = plugin.getConfig().getString("Mainlobby.World");
                World world = Bukkit.getServer().getWorld(configWorld);
                Location loc = new Location(world, x, y, z);
                player.getInventory().clear();
                player.teleport(loc);
            }
            if (Main.inLobby.contains(player)) {
                double x = plugin.getConfig().getDouble("Mainlobby.x");
                double y = plugin.getConfig().getDouble("Mainlobby.y");
                double z = plugin.getConfig().getDouble("Mainlobby.z");
                String configWorld = plugin.getConfig().getString("Mainlobby.World");
                World world = Bukkit.getServer().getWorld(configWorld);
                Location loc = new Location(world, x, y, z);
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                player.getInventory().clear();
                Main.inLobby.remove(player);
                Main.inMainLobby.add(player);
                player.teleport(loc);
            }
            if (Main.inGame.contains(player)) {
                double x = plugin.getConfig().getDouble("Mainlobby.x");
                double y = plugin.getConfig().getDouble("Mainlobby.y");
                double z = plugin.getConfig().getDouble("Mainlobby.z");
                String configWorld = plugin.getConfig().getString("Mainlobby.World");
                World world = Bukkit.getServer().getWorld(configWorld);
                Location loc = new Location(world, x, y, z);
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                player.getInventory().clear();
                Main.inGame.remove(player);
                Main.inMainLobby.add(player);
                player.setGameMode(GameMode.SURVIVAL);
                player.teleport(loc);
            }

        }

        return true;
    }
}
