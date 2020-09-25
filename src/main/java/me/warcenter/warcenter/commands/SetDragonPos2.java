package me.warcenter.warcenter.commands;

import me.warcenter.warcenter.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SetDragonPos2 implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command!");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("WarSetDragonPos2")) {
            Location loc = player.getLocation();
            double x = loc.getBlockX() + 0.5;
            double y = loc.getBlockY() + 100;
            double z = loc.getBlockZ() + 0.5;

            plugin.getConfig().set("DragonPos2.World", loc.getWorld().getName());
            plugin.getConfig().set("DragonPos2.x",  x);
            plugin.getConfig().set("DragonPos2.y",  y);
            plugin.getConfig().set("DragonPos2.z",  z);
            plugin.getConfig().set("DragonPos2.set", true);
            plugin.saveConfig();
            player.sendMessage("Set Dragon End to: " + "X: " + x + " Y: " + y + " Z: " + z);
        }

        return true;
    }
}

