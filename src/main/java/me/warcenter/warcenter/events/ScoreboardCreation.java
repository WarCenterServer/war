package me.warcenter.warcenter.events;

import me.warcenter.warcenter.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;

public class ScoreboardCreation implements Listener {

    int taskID;

    public void scoreboardStart() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(Main.main, new Runnable() {
            @Override
            public void run() {

            }
        }, (20*10L), 20*1);
    }



}
