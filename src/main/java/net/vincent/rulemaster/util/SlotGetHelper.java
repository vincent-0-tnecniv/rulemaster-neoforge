package net.vincent.rulemaster.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public class SlotGetHelper {

    public static boolean isHelmet(Player player, Item item) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == item;
    }

    public static boolean isHelmet(Player player, DeferredItem<Item> item) {
        return isHelmet(player, item.get());
    }

    public static boolean isChestplate(Player player, Item item) {
        return player.getItemBySlot(EquipmentSlot.CHEST).getItem() == item;
    }

    public static boolean isChestplate(Player player, DeferredItem<Item> item) {
        return isChestplate(player, item.get());
    }

    public static boolean isLeggings(Player player, Item item) {
        return player.getItemBySlot(EquipmentSlot.LEGS).getItem() == item;
    }

    public static boolean isLeggings(Player player, DeferredItem<Item> item) {
        return isLeggings(player, item.get());
    }

    public static boolean isBoots(Player player, Item item) {
        return player.getItemBySlot(EquipmentSlot.FEET).getItem() == item;
    }

    public static boolean isBoots(Player player, DeferredItem<Item> item) {
        return isBoots(player, item.get());
    }
}
