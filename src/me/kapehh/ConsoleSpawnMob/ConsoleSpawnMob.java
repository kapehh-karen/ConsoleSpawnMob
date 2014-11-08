package me.kapehh.ConsoleSpawnMob;

import me.kapehh.ConsoleSpawnMob.task.SpawnTask;
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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karen on 10.07.2014.
 */
public class ConsoleSpawnMob extends JavaPlugin {
    SpawnTask spawnTask = null;
    SpawnCommandExecutor spawnCommandExecutor = new SpawnCommandExecutor();
    Map<World, List<Double>> worldsLimit = new HashMap<World, List<Double>>();
    PluginConfig pluginConfig = null;

    ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
    private List<Double> evalString(String eval, int max) throws ScriptException {
        List<Double> doubles = new ArrayList<Double>();
        for (int i = 0; i <= max; i++) {
            scriptEngine.put("players", i);
            Object ret = scriptEngine.eval(eval);
            if (ret instanceof Double)
                doubles.add((Double) ret);
            else if (ret instanceof Integer)
                doubles.add((double) (Integer) ret);
        }
        return doubles;
    }

    @EventPluginConfig(EventType.LOAD)
    public void onLoadConfig() {
        FileConfiguration cfg = pluginConfig.getConfig();
        boolean needSave = false;
        worldsLimit.clear();

        String strPath, strEval;
        int max_players = cfg.getInt("limit_max_players", 100);
        List<Double> limit = null;
        for (World world : Bukkit.getWorlds()) {
            strPath = "limit." + world.getName();
            strEval = cfg.getString(strPath, null);
            try {
                if (strEval != null) limit = evalString(strEval, max_players);
            } catch (ScriptException e) {
                e.printStackTrace();
            }

            if (strEval == null) {
                limit = null; // null or 0 - no limit
                cfg.set(strPath, 0);
                needSave = true;
            }

            worldsLimit.put(world, limit);
        }

        if (needSave) {
            pluginConfig.saveData();
        }

        if (spawnTask != null) spawnTask.stop();
        spawnTask = new SpawnTask();
        spawnTask.runTaskTimer(this, 0, cfg.getInt("tick_interval", 1));

        spawnCommandExecutor.setSpawnTask(spawnTask);
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
        getCommand("entities").setExecutor(new EntitiesCommandExecutor());
    }

    @Override
    public void onDisable() {
        if (spawnTask != null) spawnTask.stop();
    }
}
