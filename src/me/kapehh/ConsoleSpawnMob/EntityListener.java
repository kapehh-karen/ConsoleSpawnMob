package me.kapehh.ConsoleSpawnMob;

import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

/**
 * Created by Karen on 06.05.2015.
 */
public class EntityListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Monster) {
            Monster monster = (Monster) event.getDamager();
            if (monster.hasMetadata(ConsoleSpawnMob.CONSOLE_SPAWNMOB_TAG)) {
                List<MetadataValue> values = monster.getMetadata(ConsoleSpawnMob.CONSOLE_SPAWNMOB_TAG);
                if (values != null && values.size() > 0) event.setDamage(values.get(0).asDouble());

                // TODO: Remove
                System.out.println("NAIZZZ: hp - " + monster.getHealth());
            }
        }
    }
}
