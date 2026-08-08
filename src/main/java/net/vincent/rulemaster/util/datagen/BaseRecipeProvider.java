/*
* This is done by vincent00tencniv
* This helper parent class helps with organizing and creating helper methods that would be useful for the actual data gen class
* For that class, refer to ModRecipeProvider.java
* */

package net.vincent.rulemaster.util.datagen;

import net.vincent.rulemaster.RuleMaster;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseRecipeProvider extends RecipeProvider {
    protected BaseRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    protected void door(Block doorBlock, Block baseBlock, String groupName){
        door(doorBlock, baseBlock);
    }

    protected void door(Block doorBlock, Block baseBlock){
        doorBuilder(doorBlock, Ingredient.of(baseBlock));
    }

    protected void door(DeferredBlock<Block> doorBlock, DeferredBlock<Block> baseBlock, String groupName){
        door(doorBlock.get(), baseBlock.get(), groupName);
    }

    protected void door(DeferredBlock<Block> doorBlock, DeferredBlock<Block> baseBlock){
        door(doorBlock.get(), baseBlock.get());
    }

    protected void trapdoor(Block trapdoorBlock, Block baseBlock, String groupName){
        trapdoor(trapdoorBlock, baseBlock);
    }

    protected void trapdoor(Block trapdoorBlock, Block baseBlock){
        trapdoorBuilder(trapdoorBlock, Ingredient.of(baseBlock));
    }

    protected void trapdoor(DeferredBlock<Block> trapdoorBlock, DeferredBlock<Block> baseBlock, String groupName){
        trapdoor(trapdoorBlock.get(), baseBlock.get(), groupName);
    }

    protected void trapdoor(DeferredBlock<Block> trapdoorBlock, DeferredBlock<Block> baseBlock){
        trapdoor(trapdoorBlock.get(), baseBlock.get());
    }

    protected void pressurePlate(DeferredBlock<Block> pressurePlateBlock, DeferredBlock<Block> baseBlock) {
        pressurePlate(pressurePlateBlock.get(), baseBlock.get());
    }

    protected void pressurePlate(DeferredBlock<Block> pressurePlateBlock, DeferredBlock<Block> baseBlock, String groupName) {
        pressurePlate(pressurePlateBlock, baseBlock);
    }

    protected void pressurePlate(Block pressurePlateBlock, Block baseBlock, String groupName) {
        pressurePlate(pressurePlateBlock, baseBlock);
    }

    protected void slab(Block pressurePlateBlock, Block baseBlock, String groupName){
        slab(RecipeCategory.BUILDING_BLOCKS, pressurePlateBlock, baseBlock);
    }

    protected void slab(DeferredBlock<Block> pressurePlateBlock, DeferredBlock<Block> baseBlock, String groupName){
        slab(pressurePlateBlock.get(), baseBlock.get(), groupName);
    }

    protected void button(Block buttonBlock, Block baseBlock, String groupName){
        buttonBuilder(buttonBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).group(groupName).save(output);
    }

    protected void button(Block buttonBlock, Block baseBlock){
        buttonBuilder(buttonBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).save(output);
    }

    protected void button(DeferredBlock<Block> buttonBlock, DeferredBlock<Block> baseBlock, String groupName){
        button(buttonBlock.get(), baseBlock.get(), groupName);}

    protected void button(DeferredBlock<Block> buttonBlock, DeferredBlock<Block> baseBlock){
        button(buttonBlock.get(), baseBlock.get());
    }

    protected void stairs(Block stairBlock, Block baseBlock, String groupName){
        stairBuilder(stairBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).group(groupName).save(output);
    }

    protected void stairs(Block stairBlock, Block baseBlock){
        stairBuilder(stairBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).save(output);
    }

    protected void stairs(DeferredBlock<Block> stairBlock, DeferredBlock<Block> baseBlock, String groupName){
        stairs(stairBlock.get(), baseBlock.get(), groupName);
    }

    protected void stairs(DeferredBlock<Block> stairBlock, DeferredBlock<Block> baseBlock){
        stairs(stairBlock.get(), baseBlock.get());
    }

    protected void fence(Block fenceBlock, Block baseBlock, String groupName) {
        fenceBuilder(fenceBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).group(groupName).save(output);
    }

    protected void fence(Block fenceBlock, Block baseBlock) {
        fenceBuilder(fenceBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).save(output);
    }

    protected void fence(DeferredBlock<Block> fenceBlock, DeferredBlock<Block> baseBlock, String groupName) {
        fence(fenceBlock.get(), baseBlock.get(), groupName);
    }

    protected void fence(DeferredBlock<Block> fenceBlock, DeferredBlock<Block> baseBlock) {
        fence(fenceBlock.get(), baseBlock.get());
    }

    protected void fenceGate(Block fenceGateBlock, Block baseBlock, String groupName) {
        fenceGateBuilder(fenceGateBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).group(groupName).save(output);
    }

    protected void fenceGate(Block fenceGateBlock, Block baseBlock) {
        fenceGateBuilder(fenceGateBlock, Ingredient.of(baseBlock)).unlockedBy(getHasName(baseBlock), has(baseBlock)).save(output);
    }

    protected void fenceGate(DeferredBlock<Block> fenceGateBlock, DeferredBlock<Block> baseBlock, String groupName) {
        fenceGate(fenceGateBlock.get(), baseBlock.get(), groupName);
    }

    protected void fenceGate(DeferredBlock<Block> fenceGateBlock, DeferredBlock<Block> baseBlock) {
        fenceGate(fenceGateBlock.get(), baseBlock.get());
    }

    protected void wall(DeferredBlock<Block> wallBlock, DeferredBlock<Block> baseBlock, String groupName) {
        wall(wallBlock.get(), baseBlock.get());
    }

    protected void wall(Block wallBlock, Block baseBlock, String groupName) {
        wall(wallBlock, baseBlock);
    }

    protected void wall(Block wallBlock, Block baseBlock) {
        wall(RecipeCategory.BUILDING_BLOCKS, wallBlock, baseBlock);
    }

    protected void wall(DeferredBlock<Block> wallBlock, DeferredBlock<Block> baseBlock) {
        wall(wallBlock.get(), baseBlock.get());
    }

    protected void axe(Item axeItem, Item materialItem, @Nullable Item handleItem) {
        shaped(RecipeCategory.TOOLS, axeItem)
                .pattern("XX")
                .pattern("XS")
                .pattern(" S")
                .define('X', materialItem)
                .define('S', handleItem == null ? Items.STICK : handleItem)
                .unlockedBy(getHasName(axeItem), has(axeItem))
                .save(output);
    }

    protected void axe(DeferredItem<Item> axeItem, DeferredItem<Item> materialItem, @Nullable DeferredItem<Item> handleItem) {
        axe(axeItem.get(), materialItem.get(), handleItem == null ? null : handleItem.get());
    }

    protected void pickaxe(Item pickaxeItem, Item materialItem, @Nullable Item handleItem) {
        shaped(RecipeCategory.TOOLS, pickaxeItem)
                .pattern("XXX")
                .pattern(" S ")
                .pattern(" S ")
                .define('X', materialItem)
                .define('S', handleItem == null ? Items.STICK : handleItem)
                .unlockedBy(getHasName(pickaxeItem), has(pickaxeItem))
                .save(output);
    }

    protected void pickaxe(DeferredItem<Item> pickaxeItem, DeferredItem<Item> materialItem, @Nullable DeferredItem<Item> handleItem) {
        pickaxe(pickaxeItem.get(), materialItem.get(), handleItem == null ? null : handleItem.get());
    }

    protected void sword(Item swordItem, Item materialItem, @Nullable Item handleItem) {
        shaped(RecipeCategory.COMBAT, swordItem)
                .pattern("X")
                .pattern("X")
                .pattern("S")
                .define('X', materialItem)
                .define('S', handleItem == null ? Items.STICK : handleItem)
                .unlockedBy(getHasName(swordItem), has(swordItem))
                .save(output);
    }

    protected void sword(DeferredItem<Item> swordItem, DeferredItem<Item> materialItem, @Nullable DeferredItem<Item> handleItem) {
        sword(swordItem.get(), materialItem.get(), handleItem == null ? null : handleItem.get());
    }

    protected void shovel(Item shovelItem, Item materialItem, @Nullable Item handleItem) {
        shaped(RecipeCategory.TOOLS, shovelItem)
                .pattern("X")
                .pattern("S")
                .pattern("S")
                .define('X', materialItem)
                .define('S', handleItem == null ? Items.STICK : handleItem)
                .unlockedBy(getHasName(shovelItem), has(shovelItem))
                .save(output);
    }

    protected void shovel(DeferredItem<Item> shovelItem, DeferredItem<Item> materialItem, @Nullable DeferredItem<Item> handleItem) {
        shovel(shovelItem.get(), materialItem.get(), handleItem == null ? null : handleItem.get());
    }

    protected void hoe(Item hoeItem, Item materialItem, @Nullable Item handleItem) {
        shaped(RecipeCategory.TOOLS, hoeItem)
                .pattern("XX")
                .pattern(" S")
                .pattern(" S")
                .define('X', materialItem)
                .define('S', handleItem == null ? Items.STICK : handleItem)
                .unlockedBy(getHasName(hoeItem), has(hoeItem))
                .save(output);
    }

    protected void hoe(DeferredItem<Item> hoeItem, DeferredItem<Item> materialItem, @Nullable DeferredItem<Item> handleItem) {
        hoe(hoeItem.get(), materialItem.get(), handleItem == null ? null : handleItem.get());
    }

    protected void spear(Item spearItem, Item materialItem, @Nullable Item handleItem) {
        shaped(RecipeCategory.COMBAT, spearItem)
                .pattern("  X")
                .pattern(" S ")
                .pattern("S  ")
                .define('X', materialItem)
                .define('S', handleItem == null ? Items.STICK : handleItem)
                .unlockedBy(getHasName(spearItem), has(spearItem))
                .save(output);
    }

    protected void spear(DeferredItem<Item> spearItem, DeferredItem<Item> materialItem, @Nullable DeferredItem<Item> handleItem) {
        spear(spearItem.get(), materialItem.get(), handleItem == null ? null : handleItem.get());
    }

    protected void allTools(Item swordItem, Item pickaxeItem, Item axeItem,
                       Item shovelItem, Item hoeItem, Item spearItem,
                       Item materialItem, @Nullable Item handleItem) {
        axe(axeItem, materialItem, handleItem);
        pickaxe(pickaxeItem, materialItem, handleItem);
        sword(swordItem, materialItem, handleItem);
        hoe(hoeItem, materialItem, handleItem);
        spear(spearItem, materialItem, handleItem);
        shovel(shovelItem, materialItem, handleItem);
    }

    protected void allTools(DeferredItem<Item> swordItem, DeferredItem<Item> pickaxeItem, DeferredItem<Item> axeItem,
                       DeferredItem<Item> shovelItem, DeferredItem<Item> hoeItem, DeferredItem<Item> spearItem,
                       DeferredItem<Item> materialItem, @Nullable DeferredItem<Item> handleItem) {
        allTools(swordItem.get(), pickaxeItem.get(), axeItem.get(), shovelItem.get(), hoeItem.get(), spearItem.get(), materialItem.get(), handleItem == null ? null : handleItem.get());
    }

    protected void helmet(Item helmetItem, Item materialItem){
        shaped(RecipeCategory.COMBAT, helmetItem)
                .pattern("XXX")
                .pattern("X X")
                .define('X', materialItem)
                .unlockedBy(getHasName(helmetItem), has(helmetItem))
                .save(output);
    }

    protected void helmet(DeferredItem<Item> helmetItem, DeferredItem<Item> materialItem){
        helmet(helmetItem.get(), materialItem.get());
    }

    protected void chestplate(Item chestplateItem, Item materialItem){
        shaped(RecipeCategory.COMBAT, chestplateItem)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', materialItem)
                .unlockedBy(getHasName(chestplateItem), has(chestplateItem))
                .save(output);
    }

    protected void chestplate(DeferredItem<Item> chestplateItem, DeferredItem<Item> materialItem){
        chestplate(chestplateItem.get(), materialItem.get());
    }

    protected void leggings(Item leggingsItem, Item materialItem){
        shaped(RecipeCategory.COMBAT, leggingsItem)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .define('X', materialItem)
                .unlockedBy(getHasName(leggingsItem), has(leggingsItem))
                .save(output);
    }

    protected void leggings(DeferredItem<Item> leggingsItem, DeferredItem<Item> materialItem){
        leggings(leggingsItem.get(), materialItem.get());
    }

    protected void boots(Item bootsItem, Item materialItem){
        shaped(RecipeCategory.COMBAT, bootsItem)
                .pattern("X X")
                .pattern("X X")
                .define('X', materialItem)
                .unlockedBy(getHasName(bootsItem), has(bootsItem))
                .save(output);
    }

    protected void boots(DeferredItem<Item> bootsItem, DeferredItem<Item> materialItem){
        boots(bootsItem.get(), materialItem.get());
    }

    protected void allArmor(Item helmetItem, Item chestplateItem, Item leggingsItem, Item bootsItem, Item materialItem){
        helmet(helmetItem, materialItem);
        chestplate(chestplateItem, materialItem);
        leggings(leggingsItem, materialItem);
        boots(bootsItem, materialItem);
    }

    protected void allArmor(DeferredItem<Item> helmetItem, DeferredItem<Item> chestplateItem,
                            DeferredItem<Item> leggingsItem, DeferredItem<Item> bootsItem,
                            DeferredItem<Item> materialItem){
        allArmor(helmetItem.get(), chestplateItem.get(), leggingsItem.get(), bootsItem.get(), materialItem.get());
    }

    public void registerBlockToItemRecipes(DeferredBlock<Block> block, DeferredItem<Item> item, String groupName) {
        registerBlockToItemRecipes(block.get(), item.get(), groupName);
    }

    public void registerBlockToItemRecipes(Block block, Item item, String groupName) {
        shaped(RecipeCategory.BUILDING_BLOCKS, block)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', item)
                .unlockedBy("has_" + groupName, has(item))
                .group(groupName)
                .save(output);
        shapeless(RecipeCategory.MISC, item, 9)
                .requires(block)
                .unlockedBy(getHasName(block), has(block))
                .group(groupName)
                .save(output);
    }

    // The following method is done by Kaupenjoe

    @Override
    public <T extends AbstractCookingRecipe> void oreCooking(AbstractCookingRecipe.Factory<T> factory, List<ItemLike> smeltables,
                                                             RecipeCategory craftingCategory, CookingBookCategory cookingCategory, ItemLike result,
                                                             float experience, int cookingTime, String group, String fromDesc) {
        for(ItemLike itemlike : smeltables) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), craftingCategory, cookingCategory, result, experience, cookingTime, factory).group(group).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(output, RuleMaster.MOD_ID + ":" + getItemName(result) + fromDesc + "_" + getItemName(itemlike));
        }
    }
}
