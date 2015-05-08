package me.kapehh.ConsoleSpawnMob.mob;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Karen on 08.05.2015.
 */
public class MobInfo {
    String name;
    EntityType entityType;
    String customName;
    double damage;
    double health;
    ItemStack weapon;
    ItemStack helmet;
    ItemStack chestplate;
    ItemStack leggings;
    ItemStack boots;

    public MobInfo() {
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public ItemStack getHelmet() {
        return helmet;
    }

    public void setHelmet(ItemStack helmet) {
        this.helmet = helmet;
    }

    public ItemStack getChestplate() {
        return chestplate;
    }

    public void setChestplate(ItemStack chestplate) {
        this.chestplate = chestplate;
    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public void setLeggings(ItemStack leggings) {
        this.leggings = leggings;
    }

    public ItemStack getBoots() {
        return boots;
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }

    public ItemStack getWeapon() {
        return weapon;
    }

    public void setWeapon(ItemStack weapon) {
        this.weapon = weapon;
    }

    @Override
    public String toString() {
        return "MobInfo{" +
                "name='" + name + '\'' +
                ", entityType=" + entityType +
                ", customName='" + customName + '\'' +
                ", damage=" + damage +
                ", health=" + health +
                ", weapon=" + weapon +
                ", helmet=" + helmet +
                ", chestplate=" + chestplate +
                ", leggings=" + leggings +
                ", boots=" + boots +
                '}';
    }
}
