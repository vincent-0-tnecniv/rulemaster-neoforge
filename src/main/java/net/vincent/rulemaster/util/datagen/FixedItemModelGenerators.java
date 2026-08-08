package net.vincent.rulemaster.util.datagen;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.BiConsumer;

public class FixedItemModelGenerators extends ItemModelGenerators {
    public FixedItemModelGenerators(ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    public void generateAllTools(Item swordItem, Item pickaxeItem, Item axeItem,
                               Item shovelItem, Item hoeItem, Item spearItem) {
        this.generateFlatItem(pickaxeItem, ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(swordItem, ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(axeItem, ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(shovelItem, ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateFlatItem(hoeItem, ModelTemplates.FLAT_HANDHELD_ITEM);
        this.generateSpear(spearItem);
    }

    public void generateAllTools(DeferredItem<Item> swordItem, DeferredItem<Item> pickaxeItem, DeferredItem<Item> axeItem,
                               DeferredItem<Item> shovelItem, DeferredItem<Item> hoeItem, DeferredItem<Item> spearItem){
        generateAllTools(swordItem.get(), pickaxeItem.get(), axeItem.get(), shovelItem.get(), hoeItem.get(), spearItem.get());
    }

    public void generateTrimmableArmor(ResourceKey<EquipmentAsset> key, Item helmetItem, Item chestplateItem, Item leggingsItem, Item bootsItem, boolean hasDyedLayer){
        this.generateTrimmableItem(helmetItem, key, ItemModelGenerators.TRIM_PREFIX_HELMET, hasDyedLayer);
        this.generateTrimmableItem(chestplateItem, key, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, hasDyedLayer);
        this.generateTrimmableItem(leggingsItem, key, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, hasDyedLayer);
        this.generateTrimmableItem(bootsItem, key, ItemModelGenerators.TRIM_PREFIX_BOOTS, hasDyedLayer);
    }

    public void generateTrimmableArmor(ResourceKey<EquipmentAsset> key, DeferredItem<Item> helmetItem, DeferredItem<Item> chestplateItem, DeferredItem<Item> leggingsItem, DeferredItem<Item> bootsItem, boolean hasDyedLayer) {
        generateTrimmableArmor(key, helmetItem.get(), chestplateItem.get(), leggingsItem.get(), bootsItem.get(), hasDyedLayer);
    }

    @Override
    public void generateBow(Item item) {
        this.createFlatItemModel(item, ModelTemplates.BOW);
        super.generateBow(item);
    }

    @Override
    public void generateCrossbow(Item item) {
        this.createFlatItemModel(item, ModelTemplates.CROSSBOW);
        super.generateCrossbow(item);
    }
}
