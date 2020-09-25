package me.warcenter.warcenter;

import me.warcenter.warcenter.commands.*;
import me.warcenter.warcenter.events.ScoreboardCreation;
import me.warcenter.warcenter.events.TimerStart;
import me.warcenter.warcenter.events.WarEvents;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.shampaggon.crackshot.CSUtility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Main extends JavaPlugin {

    Plugin plugin = this;
    public static Main main;

    public File customConfigFile;
    private FileConfiguration customConfig;

    public static CSUtility csu = new CSUtility();

    @Override
    public void onEnable() {
        main = this;
        createCustomConfig();
        saveCustomConfig();
        WarCommands commands = new WarCommands();
        getServer().getPluginManager().registerEvents(new WarEvents(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardCreation(), this);
        getServer().getPluginManager().registerEvents(new Game(), this);
        getServer().getPluginManager().registerEvents(new Lobby(), this);
        getServer().getPluginManager().registerEvents(new TimerStart(), this);
        getCommand("hello").setExecutor(commands);
        getCommand("war").setExecutor(commands);
        getCommand("WarSetMainLobby").setExecutor(commands);
        getCommand("warreload").setExecutor(commands);
        getCommand("warjoin").setExecutor(new WarJoin());
        getCommand("WarSetLobby").setExecutor(new WarSetLobby());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("WarSetChest1").setExecutor(new SetChestLocation());
        getCommand("WarSetDragonPos1").setExecutor(new SetDragonPos1());
        getCommand("WarSetDragonPos2").setExecutor(new SetDragonPos2());
        getCommand("GameStart").setExecutor(new GameStart());
        getCommand("WarSetPath").setExecutor(new GameStart());
        getCommand("AddPlayerToSpawn").setExecutor(commands);
        getCommand("CheckMainLobby").setExecutor(new CheckArrays());
        getCommand("CheckLobby").setExecutor(new CheckArrays());
        getCommand("CheckInGame").setExecutor(new CheckArrays());
        getCommand("CheckAlive").setExecutor(new CheckArrays());
        getCommand("CheckDead").setExecutor(new CheckArrays());
        getCommand("CheckSpectating").setExecutor(new CheckArrays());

        loadConfig();
    }

    public static List<Player> inMainLobby = new ArrayList<Player>();
    public static List<Player> inLobby = new ArrayList<Player>();
    public static List<Player> inGame = new ArrayList<Player>();
    public static List<Player> isAlive = new ArrayList<Player>();
    public static List<Player> isDead = new ArrayList<Player>();
    public static List<Player> isSpectating = new ArrayList<Player>();

    public boolean gameRestarting;

    @Override
    public void onDisable() {
        getLogger().info("Plugin Stopped!");
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "chestlocations.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("chestlocations.yml", false);
        }

        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveCustomConfig() {
        try {
            customConfig.save(customConfigFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}