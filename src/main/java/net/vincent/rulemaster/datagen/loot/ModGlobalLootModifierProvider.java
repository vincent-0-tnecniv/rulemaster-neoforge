package net.vincent.rulemaster.datagen.loot;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.neoforged.neoforge.registries.DeferredItem;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.item.ModItems;
import net.vincent.rulemaster.loot.AddItemStackModifier;
import net.vincent.rulemaster.tags.ModTags;
import net.vincent.rulemaster.util.EntityKeyGetter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, RuleMaster.MOD_ID);
    }

    @Override
    protected void start() {
        add(ModItems.BLOOD_CRYSTAL, EntityTypes.WITCH);
        add(ModItems.BLOOD_CRYSTAL, EntityTypes.VILLAGER, 0.5f);
        add(ModItems.BLOOD_CRYSTAL, EntityTypes.WANDERING_TRADER,0.5f);
        add(ModItems.BLOOD_CRYSTAL, EntityTypes.PIGLIN,0.5f);
        add(ModItems.BLOOD_CRYSTAL, EntityTypes.PIGLIN_BRUTE,0.5f);
        add(ModItems.BLOOD_CRYSTAL, EntityTypes.PILLAGER,0.5f);
        add(ModItems.BLOOD_CRYSTAL, EntityTypes.VINDICATOR,0.5f);
        add(ModItems.BLOOD_CRYSTAL, EntityTypes.EVOKER,0.5f);
    }

    protected void add(Item item, EntityType<?> entityType, float chance) {
        String entityTypeName = EntityKeyGetter.getRK(entityType).identifier().getPath();
        add(ModItems.getRK(item).identifier().getPath() + "_to_" + entityTypeName,
                new AddItemStackModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("entities/" + entityTypeName)).build(),
                        LootItemRandomChanceCondition.randomChance((float) Math.sqrt(chance)).build()
                },
                        new ItemStackTemplate(item)));
    }

    protected void add(DeferredItem<Item> item, EntityType<?> entityType, float chance) {
        add(item.get(), entityType, chance);
    }

    protected void add(DeferredItem<Item> item, EntityType<?> entityType) {
        add(item.get(), entityType, 1f);
    }

    protected void add(Item item, EntityType<?> entityType) {
        add(item, entityType, 1f);
    }
}
