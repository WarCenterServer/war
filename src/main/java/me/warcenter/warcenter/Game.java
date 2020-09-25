package me.warcenter.warcenter;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderDragon;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.*;

public class Game implements Listener{

    static Plugin plugin = Main.getPlugin(Main.class);

    public static void setupDragon(Player p) {
        String w = plugin.getConfig().getString("DragonPos1.World");
        double x = plugin.getConfig().getDouble("DragonPos1.x");
        double y = plugin.getConfig().getDouble("DragonPos1.y");
        double z = plugin.getConfig().getDouble("DragonPos1.z");
        World world = Bukkit.getServer().getWorld(w);
        p.sendMessage("Dragon setup happened!");
        Location loc = new Location(world, x, y, z);
        Entity dragon = world.spawnEntity(loc, EntityType.ENDER_DRAGON);
        for (Player player : Main.inGame) {
            dragon.setPassenger(player);
        }
        String w1 = plugin.getConfig().getString("DragonPos2.World");
        double x1 = plugin.getConfig().getDouble("DragonPos2.x");
        double y1 = plugin.getConfig().getDouble("DragonPos2.y");
        double z1 = plugin.getConfig().getDouble("DragonPos2.z");
        World world1 = Bukkit.getServer().getWorld(w1);
        Location loc1 = new Location(world1, x1, y1, z1);
        moveTo(dragon, loc1, 1.75D);
        dragon.getVelocity().normalize();
    }

    public static void moveTo(Entity entity, Location to, double speed) {
        Location loc = entity.getLocation();
        double x = loc.getX() - to.getX();
        double y = loc.getY() - to.getY();
        double z = loc.getZ() - to.getZ();
        Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
        entity.setVelocity(velocity);
    }

    @EventHandler
    public void onChestClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(block.getType() == Material.CHEST) {
                List<String> configItems = plugin.getConfig().getStringList("Items");
                int index = new Random().nextInt(configItems.size());
                int index2 = new Random().nextInt(configItems.size());
                int index3 = new Random().nextInt(configItems.size());
                String items = configItems.get(index);
                String items2 = configItems.get(index2);
                String items3 = configItems.get(index3);
                ItemStack newItem = new ItemStack(Material.getMaterial(items.toUpperCase()));
                ItemStack newItem2 = new ItemStack(Material.getMaterial(items2.toUpperCase()));
                ItemStack newItem3 = new ItemStack(Material.getMaterial(items3.toUpperCase()));

                List<String> configWeapons = plugin.getConfig().getStringList("Weapons");
                int weaponIndex = new Random().nextInt(configWeapons.size());
                String weapons = configWeapons.get(weaponIndex);

                String finalWeaponString = weapons.substring(0, 1).toUpperCase() + weapons.substring(1, weapons.length());
                ItemStack weapon = Main.csu.generateWeapon(finalWeaponString);

                if (Main.inGame.contains(player)) {
                    Chest chest = (Chest) block.getState();
                    Location b_loc = chest.getLocation();
                    World world = player.getWorld();
                    world.dropItem(b_loc, newItem);
                    world.dropItem(b_loc, newItem2);
                    world.dropItem(b_loc, newItem3);
                    world.dropItem(b_loc, weapon);
                    block.setType(Material.AIR);
                }
            }
        }
    }
}
