package me.warcenter.warcenter.commands;

import me.warcenter.warcenter.Main;
import me.warcenter.warcenter.events.TimerStart;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

public class GameStart implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);
    TimerStart timerStart = new TimerStart();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command!");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("GameStart")) {
            //Start the lobby timer countdown
            timerStart.startTimer();
        }

        //if (cmd.getName().equalsIgnoreCase("WarSetPath")) {
            //if (args.length == 1) {
                //if (args[0].equalsIgnoreCase("arena1")) {
              //      player.sendMessage("Nice Job Setting a dragon path!");
            //    }
          //  }
        //}

        return true;
    }
}
