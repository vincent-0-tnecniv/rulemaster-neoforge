package net.vincent.rulemaster.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ConditionalItemModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.conditional.HasComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.block.ModBlocks;
import net.vincent.rulemaster.block.custom.FleshBlock;
import net.vincent.rulemaster.data.ModDataComponents;
import net.vincent.rulemaster.item.ModItems;
import net.vincent.rulemaster.util.datagen.FixedBlockModelGenerators;
import net.vincent.rulemaster.util.datagen.FixedItemModelGenerators;
import net.vincent.rulemaster.util.datagen.FixedModelProvider;

import java.util.Optional;

public class ModModelProvider extends FixedModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, RuleMaster.MOD_ID);
    }


    @Override
    protected void registerModels(FixedBlockModelGenerators blockModels, FixedItemModelGenerators itemModels) {
        PropertyDispatch<MultiVariant> dispatch = PropertyDispatch.initial(FleshBlock.TOC)
                .generate((value) -> BlockModelGenerators.plainVariant(
                        blockModels.createSuffixedVariant(
                                ModBlocks.FLESH_BLOCK.get(),
                                (value == 0 ? "" : "_" + value),
                                ModelTemplates.CUBE_ALL,
                                TextureMapping::cube
                        )
                ));

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.FLESH_BLOCK.get())
                        .with(dispatch)
        );

        blockModels.familyWithExistingFullBlock(ModBlocks.FLESH_BLOCK.get())
                .slab(ModBlocks.FLESH_SLAB.get());

        blockModels.createTrivialCube(ModBlocks.BLOOD_CRYSTAL_BLOCK.get());

        ItemModel.Unbaked piercerNormalModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(ModItems.BLOOD_PIERCER.get(), ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked piercerHalfHealthModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(ModItems.BLOOD_PIERCER.get(), "_half_health", ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked piercerLowHealthModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(ModItems.BLOOD_PIERCER.get(), "_low_health", ModelTemplates.FLAT_HANDHELD_ITEM));
        HasComponent hasHalfComponent = new HasComponent(ModDataComponents.HALF.get(), false);
        HasComponent hasLowComponent = new HasComponent(ModDataComponents.LOW.get(), false);
        ConditionalItemModel.Unbaked piercerConditionalItemModel = new ConditionalItemModel.Unbaked(Optional.empty(), hasLowComponent, piercerLowHealthModel, new ConditionalItemModel.Unbaked(Optional.empty(), hasHalfComponent, piercerHalfHealthModel, piercerNormalModel));
        ClientItem.Properties clientItemProperties = new ClientItem.Properties(false, false, 1f);

        ClientItem clientItem = new ClientItem(piercerConditionalItemModel, clientItemProperties);

        itemModels.itemModelOutput.register(ModItems.BLOOD_PIERCER.get(), clientItem);

        itemModels.generateFlatItem(ModItems.BLOOD_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FLESH_BLOCK_TEST_ITEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LIVO_GUIDE_BOOK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.EYE_OF_BIRTH.get(), ModelTemplates.FLAT_ITEM);

        itemModels.declareCustomModelItem(ModItems.BLOOD_CRYSTAL_STAFF.get());
    }

    public static MultiVariant plainVariant(Identifier model) {
        return variant(new Variant(model));
    }

    public static MultiVariant variant(Variant variant) {
        return new MultiVariant(WeightedList.of(variant));
    }

    public static BlockModelDefinitionGenerator createSlab(Block block, MultiVariant bottom, MultiVariant top, MultiVariant full) {
        return MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(BlockStateProperties.SLAB_TYPE).select(SlabType.BOTTOM, bottom).select(SlabType.TOP, top).select(SlabType.DOUBLE, full));
    }

    public static MultiVariantGenerator createSimpleBlock(Block block, MultiVariant variant) {
        return MultiVariantGenerator.dispatch(block, variant);
    }

    protected void registerDataComponentModels(FixedItemModelGenerators itemModels, Item item, String switchSuffix, DataComponentType<?> component, ModelTemplate template) {
        itemModels.itemModelOutput.register(item, new ClientItem(new ConditionalItemModel.Unbaked(Optional.empty(), new HasComponent(component, false), ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, switchSuffix, template)), ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, template))), new ClientItem.Properties(false, false, 1f)));
    }

    protected void registerDataComponentModels(FixedItemModelGenerators itemModels, Item item, String switchSuffix, DataComponentType<?> component) {
        registerDataComponentModels(itemModels, item, switchSuffix, component, ModelTemplates.FLAT_ITEM);
    }

    protected void registerDataComponentModels(FixedItemModelGenerators itemModels, DeferredItem<Item> item, String switchSuffix, DataComponentType<?> component) {
        registerDataComponentModels(itemModels, item.get(), switchSuffix, component);
    }

    protected void registerDataComponentModels(FixedItemModelGenerators itemModels, DeferredItem<Item> item, String switchSuffix, DataComponentType<?> component, ModelTemplate template){
        registerDataComponentModels(itemModels, item.get(), switchSuffix, component, template);
    }
}
