package me.kapehh.ConsoleSpawnMob;

import me.kapehh.main.pluginmanager.config.EventPluginConfig;
import me.kapehh.main.pluginmanager.config.EventType;
import me.kapehh.main.pluginmanager.config.PluginConfig;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Karen on 10.07.2014.
 */
public class ConsoleSpawnMob extends JavaPlugin {
    SpawnCommandExecutor spawnCommandExecutor = new SpawnCommandExecutor();

    Map<World, Integer> worldsLimit = new HashMap<World, Integer>();
    PluginConfig pluginConfig = null;

    @EventPluginConfig(EventType.LOAD)
    public void onLoadConfig() {
        FileConfiguration cfg = pluginConfig.getConfig();
        boolean needSave = false;
        worldsLimit.clear();

        String strPath;
        int limit;
        for (World world : Bukkit.getWorlds()) {
            strPath = "limit." + world.getName();
            limit = cfg.getInt(strPath, -1);

            if (limit < 0) {
                limit = 0; // 0 - no limit
                cfg.set(strPath, 0);
                needSave = true;
            }

            worldsLimit.put(world, limit);
        }

        if (needSave) {
            pluginConfig.saveData();
        }

        spawnCommandExecutor.setWorldsLimit(worldsLimit);
        getLogger().info("Config loaded!");
    }

    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("PluginManager") == null) {
            getLogger().info("PluginManager not found!!!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        pluginConfig = new PluginConfig(this);
        pluginConfig.addEventClasses(this);
        pluginConfig.setup();
        pluginConfig.loadData();
        spawnCommandExecutor.setPluginConfig(pluginConfig);

        getCommand("spawnmobx").setExecutor(spawnCommandExecutor);
    }

    @Override
    public void onDisable() {
        /*if (pluginConfig != null) {
            pluginConfig.saveData();
        }*/
    }
}
