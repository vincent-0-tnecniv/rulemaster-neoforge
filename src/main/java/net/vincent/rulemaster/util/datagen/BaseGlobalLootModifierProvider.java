package net.vincent.rulemaster.util.datagen;

import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.entity.EntityTypePredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class BaseGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public BaseGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid) {
        super(output, registries, modid);
    }

    protected AddTableLootModifier customLoot(LootItemCondition[] list, ResourceKey<LootTable> modifiedLootTable) {
        return new AddTableLootModifier(list, 1000, modifiedLootTable);
    }

    // TODO: add archaeology, brush, carve, charged_creeper, dispensers, equipment, gameplay, harvest, pots, shearing, spawners

    protected AddTableLootModifier entityTagLoot(HolderGetter<EntityType<?>> lookup, TagKey<EntityType<?>> tag, ResourceKey<LootTable> lootTable) {
        return customLoot(new LootItemCondition[] {
                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity()
                                .entityType(EntityTypePredicate.of(lookup, tag))
                ).build()
        },  lootTable);
    }
    // For more than one tag - use another tag and add that one single tag to the loot modifier
    // You can even remove custom mobs if doing so

    protected AddTableLootModifier simpleEntityLoot(List<EntityType<?>> entities, ResourceKey<LootTable> modifiedLootTable) {
        return new AddTableLootModifier(createEntityDropConditions(entities), 1000, modifiedLootTable);
    }

    private LootItemCondition[] createStructureConditions(List<ResourceKey<Structure>> structures) {
        LootItemCondition[] result = new LootItemCondition[structures.size()];
        for(int i = 0; i < structures.size(); i++) {
            if(structures.get(i).identifier().getPath().equals("jungle_pyramid")) {
                // There is a weird error with jungle temple - or what the game ID calls as a "jungle pyramid"
                result[i] = new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/jungle_temple")).build();
                continue;
            }
            result[i] = new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/" + structures.get(i).identifier().getPath())).build();
        }
        return result;
    }

    protected AddTableLootModifier simpleChestLoot(List<ResourceKey<Structure>> structures, ResourceKey<LootTable> modifiedLootTable) {
        return new AddTableLootModifier(createStructureConditions(structures), 1000, modifiedLootTable);
    }

    private LootItemCondition[] createEntityDropConditions(List<EntityType<?>> entities) {
        LootItemCondition[] result = new LootItemCondition[entities.size()];
        for(int i = 0; i < entities.size(); i++) {
            Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(entities.get(i));
            result[i] = new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("entities/" + id.getPath())).build();
        }
        return result;
    }

    protected AddTableLootModifier simpleBlockDrop(List<Block> blocks, ResourceKey<LootTable> modifiedLootTable) {
        // Assume that blocks is either a List<Block> or List<DeferredBlock<Block>>
        return new AddTableLootModifier(createBlockConditions(blocks), 1000, modifiedLootTable);
    }

    private LootItemCondition[] createBlockConditions(List<Block> blocks) {
        return new LootItemCondition[] {AnyOfCondition.anyOf(blocks.stream().map(LootItemBlockStatePropertyCondition::hasBlockStateProperties).toArray(LootItemBlockStatePropertyCondition.Builder[]::new)).build()};
    }

    public static List<EntityType<?>> getEntityTypesFromTag(HolderLookup.Provider provider, TagKey<EntityType<?>> tagKey) {
        var lookup = provider.lookupOrThrow(Registries.ENTITY_TYPE);
        var holders = lookup.getOrThrow(tagKey);
        return holders.stream()
                .map(Holder::value)
                .collect(Collectors.toList());
    }

    public static List<Block> getBlocksFromTag(TagKey<Block> tagKey) {
        HolderSet<Block> holders = BuiltInRegistries.BLOCK.getOrThrow(tagKey);
        return holders.stream()
                .map(Holder::value)
                .collect(Collectors.toList());
    }

    public static List<Item> getItemsFromTag(TagKey<Item> tagKey) {
        HolderSet<Item> holders = BuiltInRegistries.ITEM.getOrThrow(tagKey);
        return holders.stream()
                .map(Holder::value)
                .collect(Collectors.toList());
    }

    protected abstract void start();
}
