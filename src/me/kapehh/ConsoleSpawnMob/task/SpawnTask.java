package me.kapehh.ConsoleSpawnMob.task;

import me.kapehh.ConsoleSpawnMob.ConsoleSpawnMob;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.metadata.FixedMetadataValue;
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
        Entity spawnedEntity = item.getLocation().getWorld().spawnEntity(item.getLocation(), item.getEntityType());
        // TODO: Fix it
        if (spawnedEntity instanceof Damageable) {
            Damageable entity = (Damageable) spawnedEntity;
            entity.setMaxHealth(1000);
            entity.setHealth(999);
            Monster monster = (Monster) spawnedEntity;
            monster.setCustomName("SWAAAAAAG");
            EntityEquipment entityEquipment = monster.getEquipment();
            spawnedEntity.setMetadata(ConsoleSpawnMob.CONSOLE_SPAWNMOB_TAG, new FixedMetadataValue(ConsoleSpawnMob.instance, 22));
        }
        spawnItemList.remove(0);
    }
}
