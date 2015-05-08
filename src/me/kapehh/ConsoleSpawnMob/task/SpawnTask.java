package me.kapehh.ConsoleSpawnMob.task;

import me.kapehh.ConsoleSpawnMob.ConsoleSpawnMob;
import me.kapehh.ConsoleSpawnMob.mob.MobInfo;
import me.kapehh.ConsoleSpawnMob.mob.MobsManager;
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
        MobInfo mobInfo = item.getMobInfo();
        if (mobInfo == null) {
            item.getLocation().getWorld().spawnEntity(item.getLocation(), item.getEntityType());
        } else {
            Entity spawnedEntity = item.getLocation().getWorld().spawnEntity(item.getLocation(), mobInfo.getEntityType());
            if (spawnedEntity instanceof Damageable) {
                Damageable entity = (Damageable) spawnedEntity;
                entity.setMaxHealth(mobInfo.getHealth());
                entity.setHealth(mobInfo.getHealth());
                Monster monster = (Monster) spawnedEntity;
                monster.setCustomName(mobInfo.getCustomName());
                EntityEquipment entityEquipment = monster.getEquipment();
                entityEquipment.setHelmet(mobInfo.getHelmet());
                entityEquipment.setHelmetDropChance(0F);
                entityEquipment.setChestplate(mobInfo.getChestplate());
                entityEquipment.setChestplateDropChance(0F);
                entityEquipment.setLeggings(mobInfo.getLeggings());
                entityEquipment.setLeggingsDropChance(0F);
                entityEquipment.setBoots(mobInfo.getBoots());
                entityEquipment.setBootsDropChance(0F);
                entityEquipment.setItemInHand(mobInfo.getWeapon());
                entityEquipment.setItemInHandDropChance(0F);
                spawnedEntity.setMetadata(ConsoleSpawnMob.CONSOLE_SPAWNMOB_TAG, new FixedMetadataValue(ConsoleSpawnMob.instance, mobInfo));
            }
        }
        spawnItemList.remove(0);
    }
}
