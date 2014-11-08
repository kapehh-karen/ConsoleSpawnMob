package me.kapehh.ConsoleSpawnMob.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karen on 08.11.2014.
 */
public class SpawnTask extends BukkitRunnable {
    // .runTaskTimer(this, 100, tickspersec);
    public List<SpawnItem> spawnItemList = new ArrayList<SpawnItem>();

    public void stop() {
        Bukkit.getScheduler().cancelTask(getTaskId()); // this.cancel()
    }

    @Override
    public void run() {
        if (spawnItemList.size() <= 0) return;
        SpawnItem item = spawnItemList.get(0);
        item.getLocation().getWorld().spawnEntity(item.getLocation(), item.getEntityType());
        spawnItemList.remove(0);
    }
}
