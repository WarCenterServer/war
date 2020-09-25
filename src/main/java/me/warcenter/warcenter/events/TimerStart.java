package me.warcenter.warcenter.events;

import me.warcenter.warcenter.Game;
import me.warcenter.warcenter.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class TimerStart implements Listener {

    Plugin plugin = Main.getPlugin(Main.class);

    public int time = 10;
    public int gameTime = 120;
    String winner;

    public boolean gameRunning = false;

    public void startTimer() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
            @Override
            public void run() {
                if (time != -1){
                    if (time != 0) {
                        Bukkit.broadcastMessage(ChatColor.AQUA + "The game will begin in: " + time + " seconds!");
                    } else {
                        Bukkit.broadcastMessage(ChatColor.GREEN + "Teleporting players...");
                        for (Player player : Main.inLobby) {
                            player.getInventory().clear();
                            if (Main.isDead.contains(player)) {
                                Main.isDead.remove(player);
                            }
                            Main.inGame.add(player);
                            Main.isAlive.add(player);
                            Game.setupDragon(player);
                        }
                        double x1 = Main.main.getCustomConfig().getDouble("ChestLocation1.x");
                        double y1 = Main.main.getCustomConfig().getDouble("ChestLocation1.y");
                        double z1 = Main.main.getCustomConfig().getDouble("ChestLocation1.z");
                        String configWorld1 = Main.main.getCustomConfig().getString("ChestLocation1.World");
                        World world1 = Bukkit.getServer().getWorld(configWorld1);
                        Location loc1 = new Location(world1, x1, y1, z1);
                        loc1.getBlock().setType(Material.CHEST);
                    }
                    time--;
                }
            }
        }, 0L, 20L );
    }

    public void startGameTimer() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (gameTime != -1){
                    if (gameTime == 60) {
                        gameRunning = true;
                        Bukkit.broadcastMessage(ChatColor.BOLD + "There are " + gameTime + " seconds left.");
                    }
                    if (gameTime == 15) {
                        Bukkit.broadcastMessage(ChatColor.BOLD + "There are " + gameTime + " seconds left.");
                    }
                    if (gameTime <= 10) {
                        Bukkit.broadcastMessage(ChatColor.BOLD + "Game ending in " + gameTime + " seconds.");
                    }
                    if (gameTime == 0) {
                        Bukkit.broadcastMessage(ChatColor.BOLD + "Game Over! ");
                        for (Player player: Main.inGame) {
                            String configWorld = plugin.getConfig().getString("Mainlobby.World");
                            double x = plugin.getConfig().getDouble("Mainlobby.x");
                            double y = plugin.getConfig().getDouble("Mainlobby.y");
                            double z = plugin.getConfig().getDouble("Mainlobby.z");
                            World world = Bukkit.getServer().getWorld(configWorld);
                            Location loc = new Location(world, x, y, z);
                            if (!Main.inMainLobby.contains(player)){
                                Main.inMainLobby.add(player);
                            }
                            player.getInventory().clear();
                            player.teleport(loc);
                        }
                        gameRunning = false;
                        double x1 = Main.main.getCustomConfig().getDouble("ChestLocation1.x");
                        double y1 = Main.main.getCustomConfig().getDouble("ChestLocation1.y");
                        double z1 = Main.main.getCustomConfig().getDouble("ChestLocation1.z");
                        String configWorld1 = Main.main.getCustomConfig().getString("ChestLocation1.World");
                        World world1 = Bukkit.getServer().getWorld(configWorld1);
                        Location loc1 = new Location(world1, x1, y1, z1);
                        loc1.getBlock().setType(Material.AIR);
                    }
                    gameTime--;
                }
            }
        }, 0L, 20L);
    }
}
