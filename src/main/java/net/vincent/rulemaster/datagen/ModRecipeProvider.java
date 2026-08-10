package net.vincent.rulemaster.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.vincent.rulemaster.block.ModBlocks;
import net.vincent.rulemaster.item.ModItems;
import net.vincent.rulemaster.util.datagen.BaseRecipeProvider;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends BaseRecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "RuleMaster Mod Recipes";
        }
    }

    @Override
    public void buildRecipes() {
        registerBlockToItemRecipes(ModBlocks.BLOOD_CRYSTAL_BLOCK, ModItems.BLOOD_CRYSTAL, "blood_crystal");

        shapeless(RecipeCategory.MISC, ModItems.LIVO_GUIDE_BOOK)
                .requires(ModItems.BLOOD_CRYSTAL)
                .requires(Items.BOOK)
                .unlockedBy(getHasName(ModItems.BLOOD_CRYSTAL), has(ModItems.BLOOD_CRYSTAL))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.EYE_OF_BIRTH)
                .pattern("XXX")
                .pattern("XOX")
                .pattern("XXX")
                .define('X', ModItems.BLOOD_CRYSTAL)
                .define('O', Items.ENDER_EYE)
                .unlockedBy(getHasName(ModItems.BLOOD_CRYSTAL), has(ModItems.BLOOD_CRYSTAL))
                .save(output);


//        List<ItemLike> AZURITE_SMELTABLES = List.of(ModItems.RAW_AZURITE,
//                ModBlocks.AZURITE_ORE, ModBlocks.AZURITE_DEEPSLATE_ORE,
//                ModBlocks.AZURITE_END_ORE, ModBlocks.AZURITE_NETHER_ORE);
//
//        oreSmelting(AZURITE_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.AZURITE.get(),
//                0.25f, 200, "azurite");
//        oreBlasting(AZURITE_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.AZURITE.get(),
//                0.25f, 100, "azurite");
//
//        oreSmelting(List.of(ModBlocks.RAW_AZURITE_BLOCK), RecipeCategory.MISC, CookingBookCategory.MISC, ModBlocks.AZURITE_BLOCK.get(),
//                2.25f, 1800, "azurite");
//        oreBlasting(List.of(ModBlocks.RAW_AZURITE_BLOCK), RecipeCategory.MISC, CookingBookCategory.MISC, ModBlocks.AZURITE_BLOCK.get(),
//                2.25f, 900, "azurite");
//
//        stairs(ModBlocks.AZURITE_STAIRS, ModBlocks.AZURITE_BLOCK, "azurite");
//        slab(ModBlocks.AZURITE_SLAB, ModBlocks.AZURITE_BLOCK, "azurite");
//        button(ModBlocks.AZURITE_BUTTON, ModBlocks.AZURITE_BLOCK, "azurite");
//        pressurePlate(ModBlocks.AZURITE_PRESSURE_PLATE, ModBlocks.AZURITE_BLOCK, "azurite");
//        fence(ModBlocks.AZURITE_FENCE, ModBlocks.AZURITE_BLOCK, "azurite");
//        fenceGate(ModBlocks.AZURITE_FENCE_GATE, ModBlocks.AZURITE_BLOCK, "azurite");
//        wall(ModBlocks.AZURITE_WALL, ModBlocks.AZURITE_BLOCK,  "azurite");
//        door(ModBlocks.AZURITE_DOOR, ModBlocks.AZURITE_BLOCK, "azurite");
//        trapdoor(ModBlocks.AZURITE_TRAPDOOR, ModBlocks.AZURITE_BLOCK, "azurite");
//
//        allTools(ModItems.AZURITE_SWORD, ModItems.AZURITE_PICKAXE, ModItems.AZURITE_AXE,
//                ModItems.AZURITE_SHOVEL, ModItems.AZURITE_HOE, ModItems.AZURITE_SPEAR,
//                ModItems.AZURITE, null);
//
//        allArmor(ModItems.AZURITE_HELMET, ModItems.AZURITE_CHESTPLATE,
//                ModItems.AZURITE_LEGGINGS, ModItems.AZURITE_BOOTS,
//                ModItems.AZURITE);
    }
}
