package net.vincent.rulemaster.datagen.tags;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.block.ModBlocks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RuleMaster.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // adding to non-block block sets is not STRICTLY necessary
        // however, still good to add as convention so that other mods, if needed,
        //  support the use of the non-block blocks

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.getRK(ModBlocks.BLOOD_CRYSTAL_BLOCK.get()));

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.getRK(ModBlocks.BLOOD_CRYSTAL_BLOCK.get()));

        tag(BlockTags.SLABS)
                .add(ModBlocks.getRK(ModBlocks.FLESH_SLAB));

        // For a block family, these are MANDATORY
//        tag(BlockTags.WOODEN_FENCES)
//                .add(ModBlocks.getRK(ModBlocks.AZURITE_FENCE));
        // For wooden fences, the above tag is needed as they don't connect to those that aren't

    }

    // Use these methods for any adding of vanilla blocks

    protected void addToTag(TagKey<Block> tag, Block block) {
        ResourceKey<Block> key = block.builtInRegistryHolder().getKey();
        if(key == null) {
            throw new NullPointerException(block.getDescriptionId() + " not found in registry");
        }
        tag(tag).add(key);
    }

    protected void addToTag(TagKey<Block> tag, List<Block> blocks) {
        for(Block block : blocks) {
            if(block.builtInRegistryHolder().getKey() == null) {
                throw new NullPointerException(block.getDescriptionId() + " not found in registry");
            }
            addToTag(tag, block);
        }
    }
}
