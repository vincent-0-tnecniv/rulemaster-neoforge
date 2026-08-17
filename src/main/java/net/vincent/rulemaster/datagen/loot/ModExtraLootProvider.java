package net.vincent.rulemaster.datagen.loot;

import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.BiConsumer;

public class ModExtraLootProvider implements LootTableSubProvider {

    public static final ResourceKey<LootTable> BLOOD_CRYSTAL_FROM_LIVING_HUMANOID = createKey("blood_crystal_from_living_humanoid");
    public static final ResourceKey<LootTable> BLOOD_CRYSTAL_FROM_BLOOD_INFUSED_HUMANOID = createKey("blood_crystal_from_blood_infused_humanoid");

    public ModExtraLootProvider(HolderLookup.Provider provider) {

    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        // This generates an object that tells:
        // 1. What to be added?
        // 2. How much to be added?
        // 3. Where to be added?
        // 4. How much chance for it to proc?

        output.accept(BLOOD_CRYSTAL_FROM_BLOOD_INFUSED_HUMANOID, simpleLoot(1, 1f, ModItems.BLOOD_CRYSTAL));
        output.accept(BLOOD_CRYSTAL_FROM_LIVING_HUMANOID, simpleLoot(1, 0.5f, ModItems.BLOOD_CRYSTAL));
    }

    protected static LootTable.Builder simpleLoot(int rolls, float randomChance, Item itemDropped, int minAmount, int maxAmount) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(rolls))
                        .when(LootItemRandomChanceCondition.randomChance(randomChance))
                        .add(LootItem.lootTableItem(itemDropped))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minAmount, maxAmount)))
        );
    }

    protected static LootTable.Builder simpleLoot(int rolls, float randomChance, Item itemDropped) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(rolls))
                        .when(LootItemRandomChanceCondition.randomChance(randomChance))
                        .add(LootItem.lootTableItem(itemDropped))
        );
    }

    protected static LootTable.Builder simpleLoot(int rolls, float randomChance, DeferredItem<Item> itemDropped, int minAmount, int maxAmount) {
        return simpleLoot(rolls, randomChance, itemDropped.get(), minAmount, maxAmount);
    }

    protected static LootTable.Builder simpleLoot(int rolls, float randomChance, DeferredItem<Item> itemDropped) {
        return simpleLoot(rolls, randomChance, itemDropped.get());
    }

    protected static ResourceKey<LootTable> createKey(Item item) {
        return createKey(BuiltInRegistries.ITEM.getKey(item).getPath());
    }

    protected static ResourceKey<LootTable> createKey(DeferredItem<Item> item) {
        return createKey(item.get());
    }

    protected static ResourceKey<LootTable> createKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE,
                Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "extra/glm/" + id));
    }

}

