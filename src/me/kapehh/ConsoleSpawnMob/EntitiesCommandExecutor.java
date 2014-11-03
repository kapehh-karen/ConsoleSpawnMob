package me.kapehh.ConsoleSpawnMob;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;

/**
 * Created by Karen on 03.11.2014.
 */
public class EntitiesCommandExecutor implements CommandExecutor {

    private static class Value {
        private int value;

        private Value(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    private String getWorldEntities(World world) {
        StringBuilder sb = new StringBuilder();
        HashMap<EntityType, Value> map = new HashMap<EntityType, Value>();
        Value value;
        for (Entity entity : world.getEntities()) {
            if (map.containsKey(entity.getType())) {
                value = map.get(entity.getType());
                value.setValue(value.getValue() + 1);
            } else {
                map.put(entity.getType(), new Value(1));
            }
        }
        sb.append(ChatColor.YELLOW).append(world.getName()).append(ChatColor.RESET).append(" {\n");
        for (EntityType type : map.keySet()) {
            sb.append("  ")
                .append(type)
                .append(": ")
                .append(map.get(type).getValue())
                .append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("You need OP!");
            return true;
        }

        if (args.length > 0) {
            World world = Bukkit.getWorld(args[0]);
            if (world == null) {
                sender.sendMessage("World '" + args[0] + "' not found");
                return true;
            }
            sender.sendMessage(getWorldEntities(world));
        } else {
            String res = "";
            for (World world : Bukkit.getWorlds()) {
                res += getWorldEntities(world);
            }
            sender.sendMessage(res);
        }

        return true;
    }
}
