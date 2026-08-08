/*
 * This is done by vincent00tencniv
 * This helper parent class fixes some problems in the ModelProvider.java class, and is used by the ModRecipeProvider
 * For that class, refer to ModModelProvider.java
 * */
package net.vincent.rulemaster.util.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public abstract class FixedModelProvider extends ModelProvider {
    public FixedModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Deprecated
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        FixedBlockModelGenerators fixedBlockModelGenerators = new FixedBlockModelGenerators(blockModels.blockStateOutput, blockModels.itemModelOutput, blockModels.modelOutput);
        FixedItemModelGenerators fixedItemModelGenerators = new FixedItemModelGenerators(itemModels.itemModelOutput, itemModels.modelOutput);
        registerModels(fixedBlockModelGenerators, fixedItemModelGenerators);
    }

    protected void registerModels(FixedBlockModelGenerators blockModels, ItemModelGenerators itemModels){
        super.registerModels(blockModels, new FixedItemModelGenerators(itemModels.itemModelOutput, itemModels.modelOutput));
    }

    protected void registerModels(BlockModelGenerators blockModels, FixedItemModelGenerators itemModels){
        super.registerModels(new FixedBlockModelGenerators(blockModels.blockStateOutput, blockModels.itemModelOutput, blockModels.modelOutput), itemModels);
    }

    protected abstract void registerModels(FixedBlockModelGenerators blockModels, FixedItemModelGenerators itemModels);
}
