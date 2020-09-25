package me.warcenter.warcenter.events;

import me.warcenter.warcenter.Lobby;
import me.warcenter.warcenter.Main;
import me.warcenter.warcenter.commands.WarJoin;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class WarEvents implements Listener {

    static Plugin plugin = Main.getPlugin(Main.class);
    private static final UUID movementSpeedUID = UUID.fromString("206a89dc-ae78-4c4d-b42c-3b31db3f5a7c");
    private int count;
    TimerStart timerStart = new TimerStart();

    static boolean spawnSet = plugin.getConfig().getBoolean("Mainlobby.set");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);

        if(!spawnSet) {
            player.sendMessage("[WAR] Please Set the lobby and RESTART the server with: /WarSetMainLobby");
        }
        else{
            double x = plugin.getConfig().getDouble("Mainlobby.x");
            double y = plugin.getConfig().getDouble("Mainlobby.y");
            double z = plugin.getConfig().getDouble("Mainlobby.z");
            String configWorld = plugin.getConfig().getString("Mainlobby.World");
            World world = Bukkit.getServer().getWorld(configWorld);
            Location loc = new Location(world, x, y, z);
            if(player.hasPlayedBefore()) {
                player.teleport(loc);
            }
        }

        Main.inMainLobby.add(player);

    }

    @EventHandler
    public void onSneak(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String w = plugin.getConfig().getString("DragonPos1.World");
        double x = plugin.getConfig().getDouble("DragonPos1.x");
        double y = plugin.getConfig().getDouble("DragonPos1.y");
        double z = plugin.getConfig().getDouble("DragonPos1.z");
        World world = Bukkit.getServer().getWorld(w);
        Location loc = new Location(world, x, y, z);
        if (Main.inGame.contains(player)) {
            if (player.isSneaking()) {
                player.setVelocity(player.getVelocity().multiply(0.3));
            }

        }
    }

    @EventHandler
    public void onPlayerFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Main.inMainLobby.contains(player)) {
            Main.inMainLobby.remove(player);
        }
        if (Main.inLobby.contains(player)) {
            Main.inLobby.remove(player);
        }
        if (Main.inGame.contains(player)) {
            Main.inGame.remove(player);
        }
        if (Main.isDead.contains(player)) {
            Main.isDead.remove(player);
        }
        if (Main.isSpectating.contains(player)) {
            Main.isSpectating.remove(player);
        }
        if (Main.isAlive.contains(player)) {
            Main.isAlive.remove(player);
        }
        winnerCheck(player);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        if (Main.inGame.contains(player)) {
            Location loc = player.getLocation();
            loc.setY(loc.getY() + 1);
            player.spigot().respawn();
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.main, new Runnable() {
                public void run() {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.teleport(loc);
                }
            }, 1);
            if (Main.isAlive.contains(player)) {
                Main.isAlive.remove(player);
            }
            winnerCheck(player);
            Main.isDead.add(player);
            Main.isSpectating.add(player);
        }
        else{
            double x = plugin.getConfig().getDouble("Mainlobby.x");
            double y = plugin.getConfig().getDouble("Mainlobby.y");
            double z = plugin.getConfig().getDouble("Mainlobby.z");
            String configWorld = plugin.getConfig().getString("Mainlobby.World");
            World world = Bukkit.getServer().getWorld(configWorld);
            Location loc = new Location(world, x, y, z);
            player.getInventory().clear();
            player.teleport(loc);
        }
    }

    public void winnerCheck(Player player) {
        if (Main.isAlive.size() == 1) {
            if (Main.isAlive.contains(player)) {
                timerStart.winner = ChatColor.BOLD + player.getName();
            }
            Bukkit.broadcastMessage("The winner is " + timerStart.winner);
        }
    }


}
