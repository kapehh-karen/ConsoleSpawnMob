package me.kapehh.ConsoleSpawnMob;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Karen on 10.07.2014.
 */
public class ConsoleSpawnMob extends JavaPlugin implements CommandExecutor {

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
        }

        return false;
    }

    @Override
    public void onEnable() {
        getCommand("spawnmobx").setExecutor(this);
    }
}
