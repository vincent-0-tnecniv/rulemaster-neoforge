package net.vincent.rulemaster.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
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

        public static TagKey<Item> MUCUS_DOOR_INFINITELY_OPENABLE = createTag("mucus_door_infinitely_openable");

        public static TagKey<Item> createTag(String name){
            return ItemTags.create(Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
        }
    }

    public static class EntityTypes {

        public static TagKey<EntityType<?>> HUMANOID = createTag("humanoid");
        public static TagKey<EntityType<?>> LIVING_HUMANOID = createTag("living_humanoid");
        public static TagKey<EntityType<?>> DROPS_BC_HALF_CHANCE = createTag("drops_bc_half_chance");
        public static TagKey<EntityType<?>> BLOOD_INFUSED_HUMANOID = createTag("blood_infused_humanoid");
        public static TagKey<EntityType<?>> DROPS_BC_ALL_CHANCE = createTag("drops_bc_all_chance");

        public static TagKey<EntityType<?>> createTag(String name){
            return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
        }
    }

    public static class Structures {

        public static TagKey<Structure> EYE_OF_BIRTH_LOCATED = createTag("eye_of_birth_located");

        public static TagKey<Structure> createTag(String name){
            return TagKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
        }
    }

    public static class StructureBiomes {

        // StructureBiome Tags should store the allowed biomes of a given structure

        public static final TagKey<Biome> CRADLE_OF_LIFE_BIOMES = createTag("cradle_of_life");

        private static TagKey<Biome> createTag(String id) {
            return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "has_structure/" + id + "_biomes"));
        }
    }

    public static class Biomes {

        // Biome Tags should store the biomes that belong to a custom tag

        private static TagKey<Biome> createTag(String id) {
            return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, id));
        }
    }

    public static class Trades {

        public static TagKey<VillagerTrade> createTag(String name){
            return TagKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
        }
    }
}
