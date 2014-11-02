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
public class ConsoleSpawnMob extends JavaPlugin implements CommandExecutor {
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

        getLogger().info("Config loaded!");
    }

    private boolean isFullWorld(World world) {
        Integer limit = worldsLimit.get(world);
        return !(limit == null || limit == 0) && world.getEntities().size() >= limit;
    }

    private boolean doFixLocation(World world, Location location) {
        Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block == null) {
            return false;
        }

        if (block.getType().equals(Material.AIR)) {
            // Ищем первый не пустой блок внизу
            for (int i = 1; i <= 6; i++) {
                Block blockFirst = world.getBlockAt(location.getBlockX(), location.getBlockY() - i, location.getBlockZ());
                if (!blockFirst.getType().equals(Material.AIR)) {
                    location.setY(blockFirst.getY() + 1);
                    return true;
                }
            }
        } else {
            // Ищем 2 пустых блока вверху
            for (int i = 0; i <= 6; i++) {
                Block blockFirst = world.getBlockAt(location.getBlockX(), location.getBlockY() + i + 1, location.getBlockZ());
                Block blockSecond = world.getBlockAt(location.getBlockX(), location.getBlockY() + i, location.getBlockZ());
                if (blockFirst.getType().equals(Material.AIR) && blockSecond.getType().equals(Material.AIR)) {
                    location.setY(blockSecond.getY());
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("You need OP!");
            return true;
        }

        if (args.length < 1) {
            return false;
        }

        String method = args[0];
        if (method.equalsIgnoreCase("location")) {
            if (args.length < 8) {
                return false;
            }

            World world = Bukkit.getWorld(args[1]);
            if (world == null) {
                sender.sendMessage("World '" + args[1] + "' not found");
                return true;
            }

            if (isFullWorld(world)) {
                return true;
            }

            Location locationCenter = new Location(world,
                    Double.valueOf(args[2]),
                    Double.valueOf(args[3]),
                    Double.valueOf(args[4])
            );
            double radius = Double.valueOf(args[5]);
            int count = Integer.valueOf(args[6]);
            EntityType entityType = EntityType.valueOf(args[7].toUpperCase());
            double step = (2.0 * Math.PI) / count;

            for (int i = 0; i < count; i++) {
                Location location = new Location(
                    world,
                    locationCenter.getX() + (Math.cos(i * step) * radius),
                    locationCenter.getY(),
                    locationCenter.getZ() + (Math.sin(i * step) * radius)
                );
                if (doFixLocation(world, location)) {
                    world.spawnEntity(location, entityType);
                }
            }

            return true;
        } else if (method.equalsIgnoreCase("players")) {
            if (args.length < 5) {
                return false;
            }

            World world = Bukkit.getWorld(args[1]);
            if (world == null) {
                sender.sendMessage("World '" + args[1] + "' not found");
                return true;
            }

            if (isFullWorld(world)) {
                return true;
            }

            double radius = Double.valueOf(args[2]);
            int count = Integer.valueOf(args[3]);
            EntityType entityType = EntityType.valueOf(args[4].toUpperCase());
            double step = (2.0 * Math.PI) / count;

            for (Player player : world.getPlayers()) {
                Location playerLocation = player.getLocation();
                for (int i = 0; i < count; i++) {
                    Location location = new Location(
                        world,
                        playerLocation.getX() + (Math.cos(i * step) * radius),
                        playerLocation.getY(),
                        playerLocation.getZ() + (Math.sin(i * step) * radius)
                    );
                    if (doFixLocation(world, location)) {
                        world.spawnEntity(location, entityType);
                    }
                }
            }

            return true;
        } else if (method.equalsIgnoreCase("reload")) {
            pluginConfig.loadData();
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
            }
            return true;
        }

        return false;
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

        getCommand("spawnmobx").setExecutor(this);
    }

    @Override
    public void onDisable() {
        if (pluginConfig != null) {
            pluginConfig.saveData();
        }
    }
}
