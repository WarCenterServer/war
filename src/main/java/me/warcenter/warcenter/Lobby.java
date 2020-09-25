package me.warcenter.warcenter;


import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Lobby implements Listener{

    static Plugin plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Leave Game");
        item.setItemMeta(meta);
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (event.getItem() != null) {
                if (event.getItem().getItemMeta().equals(item.getItemMeta())) {
                    player.getInventory().clear();
                    if (Main.inLobby.contains(player)) {
                        Main.inLobby.remove(player);
                    }
                    player.sendMessage("Leaving game...");
                    double x = plugin.getConfig().getDouble("Mainlobby.x");
                    double y = plugin.getConfig().getDouble("Mainlobby.y");
                    double z = plugin.getConfig().getDouble("Mainlobby.z");
                    String configWorld = plugin.getConfig().getString("Mainlobby.World");
                    World world = Bukkit.getServer().getWorld(configWorld);
                    Location loc = new Location(world, x, y, z);
                    player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    player.teleport(loc);
                    Main.inMainLobby.add(player);
                }
            }
        }
    }

}