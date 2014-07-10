package me.kapehh.ConsoleSpawnMob;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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

    // /<command> [ location <world> <x> <y> <z> / players <patternName> <radius> ] <count> <mob>

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
            if (args.length < 7) {
                return false;
            }

            World world = Bukkit.getWorld(args[1]);
            if (world == null) {
                sender.sendMessage("World '" + args[1] + "' not found");
                return true;
            }

            Location location = new Location(world,
                    Double.valueOf(args[2]),
                    Double.valueOf(args[3]),
                    Double.valueOf(args[4])
            );
            int count = Integer.valueOf(args[5]);
            EntityType entityType = EntityType.valueOf(args[6]);

            for (int i = 0; i < count; i++) {
                world.spawnEntity(location, entityType);
            }

            return true;
        } else if (method.equalsIgnoreCase("players")) {
            if (args.length < 5) {
                return false;
            }

            String matchString = args[1];
            double radius = Double.valueOf(args[2]);
            int count = Integer.valueOf(args[3]);
            EntityType entityType = EntityType.valueOf(args[4]);
            double step = (2.0 * Math.PI) / count;

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().matches(matchString)) {
                    Location playerLocation = player.getLocation();
                    for (int i = 0; i < count; i++) {
                        Location location = new Location(
                                playerLocation.getWorld(),
                                playerLocation.getX() + (Math.cos(i * step) * radius),
                                playerLocation.getY(),
                                playerLocation.getZ() + (Math.sin(i * step) * radius)
                        );
                        player.getLocation().getWorld().spawnEntity(location, entityType);
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
