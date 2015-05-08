package me.kapehh.ConsoleSpawnMob;

import me.kapehh.ConsoleSpawnMob.mob.MobInfo;
import me.kapehh.ConsoleSpawnMob.mob.MobsManager;
import me.kapehh.ConsoleSpawnMob.task.SpawnItem;
import me.kapehh.ConsoleSpawnMob.task.SpawnTask;
import me.kapehh.main.pluginmanager.config.PluginConfig;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * Created by Karen on 03.11.2014.
 */
public class SpawnCommandExecutor implements CommandExecutor {
    Map<World, List<Double>> worldsLimit;
    PluginConfig pluginConfig;
    SpawnTask spawnTask;
    MobsManager mobsManager;

    public MobsManager getMobsManager() {
        return mobsManager;
    }

    public void setMobsManager(MobsManager mobsManager) {
        this.mobsManager = mobsManager;
    }

    public void setSpawnTask(SpawnTask spawnTask) {
        this.spawnTask = spawnTask;
    }

    public void setWorldsLimit(Map<World, List<Double>> worldsLimit) {
        this.worldsLimit = worldsLimit;
    }

    public void setPluginConfig(PluginConfig pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    private boolean isFullWorld(World world) {
        List<Double> limit = worldsLimit.get(world);
        int players_count = world.getPlayers().size();
        if (limit == null || limit.get(players_count) == 0) {
            return false;
        }
        double count = 0;
        for (Entity entity : world.getEntities()) {
            if ((entity instanceof LivingEntity) && !(entity instanceof Player)) {
                count++;
            }
        }
        for (SpawnItem item : spawnTask.spawnItemList) {
            if (item.getLocation().getWorld().equals(world)) {
                count++;
            }
        }
        return count >= limit.get(players_count);
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
            String mobName = args[7].toUpperCase();
            EntityType entityType = null;
            MobInfo mobInfo = mobsManager.getFromName(mobName);
            if (mobInfo == null) {
                entityType = EntityType.valueOf(mobName);
            }
            double step = (2.0 * Math.PI) / count;

            for (int i = 0; i < count; i++) {
                Location location = new Location(
                        world,
                        locationCenter.getX() + (Math.cos(i * step) * radius),
                        locationCenter.getY(),
                        locationCenter.getZ() + (Math.sin(i * step) * radius)
                );
                if (doFixLocation(world, location)) {
                    spawnTask.spawnItemList.add(new SpawnItem(mobInfo, entityType, location));
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
            String mobName = args[4].toUpperCase();
            MobInfo mobInfo = mobsManager.getFromName(mobName);
            EntityType entityType = null;
            if (mobInfo == null) {
                entityType = EntityType.valueOf(mobName);
            }
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
                        spawnTask.spawnItemList.add(new SpawnItem(mobInfo, entityType, location));
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
}
