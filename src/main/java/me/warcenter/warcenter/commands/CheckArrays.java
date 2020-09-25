package me.warcenter.warcenter.commands;

import me.warcenter.warcenter.Main;
import me.warcenter.warcenter.events.TimerStart;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CheckArrays implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command!");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("CheckMainLobby")) {
            player.sendMessage(Main.inMainLobby.toString());
        }

        if (cmd.getName().equalsIgnoreCase("CheckLobby")) {
            player.sendMessage(Main.inLobby.toString());
        }

        if (cmd.getName().equalsIgnoreCase("CheckSpectating")) {
            player.sendMessage(Main.isSpectating.toString());
        }

        if (cmd.getName().equalsIgnoreCase("CheckDead")) {
            player.sendMessage(Main.isDead.toString());
        }

        if (cmd.getName().equalsIgnoreCase("CheckAlive")) {
            player.sendMessage(Main.isAlive.toString());
        }

        if (cmd.getName().equalsIgnoreCase("CheckInGame")) {
            player.sendMessage(Main.inGame.toString());
        }
        return true;
    }
}
