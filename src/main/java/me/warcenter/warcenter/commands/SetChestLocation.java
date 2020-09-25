package me.warcenter.warcenter.commands;

import me.warcenter.warcenter.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class SetChestLocation implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command!");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("WarSetChest1")) {
            Location loc = player.getLocation();
            double x = loc.getBlockX();
            double y = loc.getBlockY();
            double z = loc.getBlockZ();

            Main.main.getCustomConfig().set("ChestLocation1.World", loc.getWorld().getName());
            Main.main.getCustomConfig().set("ChestLocation1.x",  x);
            Main.main.getCustomConfig().set("ChestLocation1.y",  y);
            Main.main.getCustomConfig().set("ChestLocation1.z",  z);
            Main.main.getCustomConfig().set("ChestLocation1.set", true);
            try {
                Main.main.getCustomConfig().save(Main.main.customConfigFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage("Set Chest 1 to: " + "X: " + x + " Y: " + y + " Z: " + z);

        }

        return true;
    }
}
