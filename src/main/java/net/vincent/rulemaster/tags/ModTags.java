package net.vincent.rulemaster.tags;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.vincent.rulemaster.RuleMaster;

public class ModTags {
    public static class Blocks {

        public static TagKey<Block> INCORRECT_FOR_BLOOD_CRYSTAL_TOOL = createTag("incorrect_for_blood_crystal_tool");

        public static TagKey<Block> createTag(String name){
            return BlockTags.create(Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
        }
    }

    public static class Items {

        public static TagKey<Item> BLOOD_CRYSTAL_REPAIRABLE = createTag("blood_crystal_repairable");

        public static TagKey<Item> createTag(String name){
            return ItemTags.create(Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
        }
    }

    public static class EntityTypes {

        public static TagKey<EntityType<?>> HUMANOID = createTag("humanoid");
        public static TagKey<EntityType<?>> LIVING_HUMANOID = createTag("living_humanoid");

        public static TagKey<EntityType<?>> createTag(String name){
            return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
        }
    }
}
