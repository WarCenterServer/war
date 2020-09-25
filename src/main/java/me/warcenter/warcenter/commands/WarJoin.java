package me.warcenter.warcenter.commands;

import me.warcenter.warcenter.Game;
import me.warcenter.warcenter.Main;
import me.warcenter.warcenter.events.TimerStart;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

public class WarJoin implements CommandExecutor {

    Plugin plugin = Main.getPlugin(Main.class);

    public TimerStart timerStart = new TimerStart();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command!");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("warjoin")) {
            if (timerStart.gameRunning == true) {
                String configWorld1 = plugin.getConfig().getString("GameSpawn.World");
                double x1 = plugin.getConfig().getDouble("GameSpawn.x");
                double y1 = plugin.getConfig().getDouble("GameSpawn.y");
                double z1 = plugin.getConfig().getDouble("GameSpawn.z");
                World world1 = Bukkit.getServer().getWorld(configWorld1);
                Location loc1 = new Location(world1, x1, y1, z1);
                player.teleport(loc1);
                player.setGameMode(GameMode.SPECTATOR);
                Main.isSpectating.add(player);
            } else {
                String configWorld = plugin.getConfig().getString("Lobby.World");
                double x = plugin.getConfig().getDouble("Lobby.x");
                double y = plugin.getConfig().getDouble("Lobby.y");
                double z = plugin.getConfig().getDouble("Lobby.z");
                World world = Bukkit.getServer().getWorld(configWorld);
                Location loc = new Location(world, x, y, z);
                player.getInventory().clear();
                player.teleport(loc);
                ItemStack item = new ItemStack(Material.MAGMA_CREAM);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW + "Leave Game");
                item.setItemMeta(meta);
                player.getInventory().setItem(8, item);
                player.setGameMode(GameMode.SURVIVAL);
                if (Main.inGame.contains(player)) {
                    Main.inGame.remove(player);
                }
                Main.inLobby.add(player);
                createBoard(player);
                if (Main.inLobby.size() >= 1) {
                    timerStart.startTimer();
                }
            }
        }

        return true;
    }

    public void createBoard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("Stats", "dummy");
        obj.setDisplayName("Stats");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore("Players Online:");
        score.setScore(Bukkit.getOnlinePlayers().size());
        player.setScoreboard(board);
    }

    public void updateScoreboard() {
        for(Player online : Main.inLobby) {
            Score score = online.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("Players Online:");
            score.setScore(Main.inLobby.size());
        }
    }

}
