/*
 * This is done by vincent00tencniv
 * This helper parent class fixes some problems in the ModelProvider.java class, and is used by the ModRecipeProvider
 * For that class, refer to ModModelProvider.java
 * */
package net.vincent.rulemaster.util.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FixedBlockModelGenerators extends BlockModelGenerators {
    public FixedBlockModelGenerators(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    public BlockFamilyProvider createFamilyWithExistingFullBlock(Block fullBlock) {
        BlockFamilyProvider provider = new BlockFamilyProvider(TextureMapping.cube(fullBlock));
        provider.fullBlock = plainModel(ModelLocationUtils.getModelLocation(fullBlock));
        return provider;
    }

    public BlockFamilyProvider createFamilyWithExistingFullBlock(DeferredBlock<Block> fullBlock) {
        return createFamilyWithExistingFullBlock(fullBlock.get());
    }

    public BlockFamilyProvider createFamily(Block block) {
        TexturedModel model = TEXTURED_MODELS.getOrDefault(block, TexturedModel.CUBE.get(block));
        return (new BlockFamilyProvider(model.getMapping())).fullBlock(block, model.getTemplate());
    }

    public BlockFamilyProvider createFamily(DeferredBlock<Block> block) {
        return createFamily(block.get());
    }

    public class BlockFamilyProvider {
        private final TextureMapping mapping;
        private final Map<ModelTemplate, Identifier> models;
        private @Nullable BlockFamily family;
        private @Nullable Variant fullBlock;
        private final Set<Block> skipGeneratingModelsFor;

        public BlockFamilyProvider(TextureMapping mapping) {
            super();
            this.models = new HashMap();
            this.skipGeneratingModelsFor = new HashSet();
            this.mapping = mapping;
        }

        public BlockFamilyProvider fullBlock(Block block, ModelTemplate template) {
            this.fullBlock = BlockModelGenerators.plainModel(template.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            if (BlockModelGenerators.FULL_BLOCK_MODEL_CUSTOM_GENERATORS.containsKey(block)) {
                FixedBlockModelGenerators.this.blockStateOutput.accept(((BlockStateGeneratorSupplier)BlockModelGenerators.FULL_BLOCK_MODEL_CUSTOM_GENERATORS.get(block)).create(block, this.fullBlock, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            } else {
                FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.variant(this.fullBlock)));
            }

            return this;
        }

        public BlockFamilyProvider donateModelTo(Block donor, Block copyTo) {
            Identifier donorModelLocation = ModelLocationUtils.getModelLocation(donor);
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(copyTo, BlockModelGenerators.plainVariant(donorModelLocation)));
            FixedBlockModelGenerators.this.itemModelOutput.copy(donor.asItem(), copyTo.asItem());
            this.skipGeneratingModelsFor.add(copyTo);
            return this;
        }

        public BlockFamilyProvider button(Block block) {
            MultiVariant normal = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant pressed = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON_PRESSED.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createButton(block, normal, pressed));
            Identifier inventory = ModelTemplates.BUTTON_INVENTORY.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput);
            FixedBlockModelGenerators.this.registerSimpleItemModel(block, inventory);
            return this;
        }

        public BlockFamilyProvider wall(Block block) {
            MultiVariant post = BlockModelGenerators.plainVariant(ModelTemplates.WALL_POST.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant low = BlockModelGenerators.plainVariant(ModelTemplates.WALL_LOW_SIDE.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant high = BlockModelGenerators.plainVariant(ModelTemplates.WALL_TALL_SIDE.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createWall(block, post, low, high));
            Identifier inventory = ModelTemplates.WALL_INVENTORY.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput);
            FixedBlockModelGenerators.this.registerSimpleItemModel(block, inventory);
            return this;
        }

        public BlockFamilyProvider customFence(Block block) {
            TextureMapping mapping = TextureMapping.customParticle(block);
            MultiVariant post = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_POST.create(block, mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant north = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_NORTH.create(block, mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant east = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_EAST.create(block, mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant south = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_SOUTH.create(block, mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant west = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_WEST.create(block, mapping, FixedBlockModelGenerators.this.modelOutput));
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createCustomFence(block, post, north, east, south, west));
            Identifier inventory = ModelTemplates.CUSTOM_FENCE_INVENTORY.create(block, mapping, FixedBlockModelGenerators.this.modelOutput);
            FixedBlockModelGenerators.this.registerSimpleItemModel(block, inventory);
            return this;
        }

        public BlockFamilyProvider fence(Block block) {
            MultiVariant post = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_POST.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant side = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_SIDE.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFence(block, post, side));
            Identifier inventory = ModelTemplates.FENCE_INVENTORY.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput);
            FixedBlockModelGenerators.this.registerSimpleItemModel(block, inventory);
            return this;
        }

        public BlockFamilyProvider customFenceGate(Block block) {
            TextureMapping mapping = TextureMapping.customParticle(block);
            MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_GATE_OPEN.create(block, mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_GATE_CLOSED.create(block, mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant openWall = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_GATE_WALL_OPEN.create(block, mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant closedWall = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_GATE_WALL_CLOSED.create(block, mapping, FixedBlockModelGenerators.this.modelOutput));
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFenceGate(block, open, closed, openWall, closedWall, false));
            return this;
        }

        public BlockFamilyProvider fenceGate(Block block) {
            MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_OPEN.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_CLOSED.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant openWall = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_OPEN.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant closedWall = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_CLOSED.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFenceGate(block, open, closed, openWall, closedWall, true));
            return this;
        }

        public BlockFamilyProvider pressurePlate(Block block) {
            MultiVariant off = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_UP.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant on = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_DOWN.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(block, off, on));
            return this;
        }

        public BlockFamilyProvider sign(Block sign) {
            if (this.family == null) {
                throw new IllegalStateException("Family not defined");
            } else {
                TextureMapping mapping = (new TextureMapping()).put(TextureSlot.ALL, TextureMapping.getBlockTexture(sign)).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(this.family.getBaseBlock()));
                MultiVariant standingRot0 = BlockModelGenerators.plainVariant(ModelTemplates.SIGN_ROT_0.create(ModelLocationUtils.getModelLocation(sign, "_rot_0"), mapping, FixedBlockModelGenerators.this.modelOutput));
                MultiVariant standingRot1 = BlockModelGenerators.plainVariant(ModelTemplates.SIGN_ROT_1.create(ModelLocationUtils.getModelLocation(sign, "_rot_1"), mapping, FixedBlockModelGenerators.this.modelOutput));
                MultiVariant standingRot2 = BlockModelGenerators.plainVariant(ModelTemplates.SIGN_ROT_2.create(ModelLocationUtils.getModelLocation(sign, "_rot_2"), mapping, FixedBlockModelGenerators.this.modelOutput));
                MultiVariant standingRot3 = BlockModelGenerators.plainVariant(ModelTemplates.SIGN_ROT_3.create(ModelLocationUtils.getModelLocation(sign, "_rot_3"), mapping, FixedBlockModelGenerators.this.modelOutput));
                FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSign(sign, standingRot0, standingRot1, standingRot2, standingRot3));
                Block wallSign = (Block)this.family.getVariants().get(BlockFamily.Variant.WALL_SIGN);
                MultiVariant wallModel = BlockModelGenerators.plainVariant(ModelTemplates.WALL_SIGN.create(wallSign, mapping, FixedBlockModelGenerators.this.modelOutput));
                FixedBlockModelGenerators.this.blockStateOutput.accept(MultiVariantGenerator.dispatch(wallSign, wallModel).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT));
                FixedBlockModelGenerators.this.registerSimpleFlatItemModel(sign.asItem());
                return this;
            }
        }

        public BlockFamilyProvider customHangingSign(Block hangingSign) {
            return this.hangingSign(hangingSign, this.family.getBaseBlock(), BlockFamily.Variant.CUSTOM_WALL_HANGING_SIGN);
        }

        public BlockFamilyProvider hangingSign(Block hangingSign) {
            return this.hangingSign(hangingSign, this.family.get(BlockFamily.Variant.STRIPPED_LOG), BlockFamily.Variant.WALL_HANGING_SIGN);
        }

        public BlockFamilyProvider hangingSign(Block hangingSign, Block particleBlock, BlockFamily.Variant wallVarient) {
            TextureMapping mapping = (new TextureMapping()).put(TextureSlot.ALL, TextureMapping.getBlockTexture(hangingSign)).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particleBlock));
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createHangingSign(hangingSign, BlockModelGenerators.plainVariant(ModelTemplates.HANGING_SIGN_ROT_0.create(ModelLocationUtils.getModelLocation(hangingSign, "_rot_0"), mapping, FixedBlockModelGenerators.this.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.HANGING_SIGN_ROT_1.create(ModelLocationUtils.getModelLocation(hangingSign, "_rot_1"), mapping, FixedBlockModelGenerators.this.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.HANGING_SIGN_ROT_2.create(ModelLocationUtils.getModelLocation(hangingSign, "_rot_2"), mapping, FixedBlockModelGenerators.this.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.HANGING_SIGN_ROT_3.create(ModelLocationUtils.getModelLocation(hangingSign, "_rot_3"), mapping, FixedBlockModelGenerators.this.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.ATTACHED_HANGING_SIGN_ROT_0.create(ModelLocationUtils.getModelLocation(hangingSign, "_attached_rot_0"), mapping, FixedBlockModelGenerators.this.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.ATTACHED_HANGING_SIGN_ROT_1.create(ModelLocationUtils.getModelLocation(hangingSign, "_attached_rot_1"), mapping, FixedBlockModelGenerators.this.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.ATTACHED_HANGING_SIGN_ROT_2.create(ModelLocationUtils.getModelLocation(hangingSign, "_attached_rot_2"), mapping, FixedBlockModelGenerators.this.modelOutput)), BlockModelGenerators.plainVariant(ModelTemplates.ATTACHED_HANGING_SIGN_ROT_3.create(ModelLocationUtils.getModelLocation(hangingSign, "_attached_rot_3"), mapping, FixedBlockModelGenerators.this.modelOutput))));
            Block wallSign = this.family.get(wallVarient);
            MultiVariant wallModel = BlockModelGenerators.plainVariant(ModelTemplates.WALL_HANGING_SIGN.create(wallSign, mapping, FixedBlockModelGenerators.this.modelOutput));
            FixedBlockModelGenerators.this.blockStateOutput.accept(MultiVariantGenerator.dispatch(wallSign, wallModel).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT));
            FixedBlockModelGenerators.this.registerSimpleFlatItemModel(hangingSign.asItem());
            return this;
        }

        public BlockFamilyProvider slab(Block slab) {
            if (this.fullBlock == null) {
                throw new IllegalStateException("Full block not generated yet");
            } else {
                Identifier bottom = this.getOrCreateModel(ModelTemplates.SLAB_BOTTOM, slab);
                MultiVariant top = BlockModelGenerators.plainVariant(this.getOrCreateModel(ModelTemplates.SLAB_TOP, slab));
                FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, BlockModelGenerators.plainVariant(bottom), top, BlockModelGenerators.variant(this.fullBlock)));
                FixedBlockModelGenerators.this.registerSimpleItemModel(slab, bottom);
                return this;
            }
        }

        public BlockFamilyProvider stairs(Block stairs) {
            MultiVariant inner = BlockModelGenerators.plainVariant(this.getOrCreateModel(ModelTemplates.STAIRS_INNER, stairs));
            Identifier straight = this.getOrCreateModel(ModelTemplates.STAIRS_STRAIGHT, stairs);
            MultiVariant outer = BlockModelGenerators.plainVariant(this.getOrCreateModel(ModelTemplates.STAIRS_OUTER, stairs));
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, inner, BlockModelGenerators.plainVariant(straight), outer));
            FixedBlockModelGenerators.this.registerSimpleItemModel(stairs, straight);
            return this;
        }

        public BlockFamilyProvider fullBlockVariant(Block variant) {
            TexturedModel model = (TexturedModel)BlockModelGenerators.TEXTURED_MODELS.getOrDefault(variant, TexturedModel.CUBE.get(variant));
            MultiVariant variantModel = BlockModelGenerators.plainVariant(model.create(variant, FixedBlockModelGenerators.this.modelOutput));
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(variant, variantModel));
            return this;
        }

        public BlockFamilyProvider pillar(Block variant) {
            MultiVariant verticalModel = BlockModelGenerators.plainVariant(TexturedModel.COLUMN.create(variant, FixedBlockModelGenerators.this.modelOutput));
            MultiVariant horizontalModel = BlockModelGenerators.plainVariant(TexturedModel.COLUMN_HORIZONTAL.create(variant, FixedBlockModelGenerators.this.modelOutput));
            FixedBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant(variant, verticalModel, horizontalModel));
            return this;
        }

        public BlockFamilyProvider door(Block door) {
            FixedBlockModelGenerators.this.createDoor(door);
            return this;
        }

        public BlockFamilyProvider trapdoor(Block result) {
            if (BlockModelGenerators.NON_ORIENTABLE_TRAPDOOR.contains(result)) {
                FixedBlockModelGenerators.this.createTrapdoor(result);
            } else {
                FixedBlockModelGenerators.this.createOrientableTrapdoor(result);
            }
            return this;
        }

        public Identifier getOrCreateModel(ModelTemplate modelTemplate, Block block) {
            return (Identifier)this.models.computeIfAbsent(modelTemplate, (template) -> template.create(block, this.mapping, FixedBlockModelGenerators.this.modelOutput));
        }

        public BlockFamilyProvider generateFor(BlockFamily family) {
            this.family = family;
            family.getVariants().forEach((variant, result) -> {
                boolean modelAlreadyRegisteredAsAnotherFamilyBase = BlockFamilies.getAllFamilies().anyMatch((b) -> b.getBaseBlock() == result);
                if (!this.skipGeneratingModelsFor.contains(result) && !modelAlreadyRegisteredAsAnotherFamilyBase) {
                    BiConsumer<BlockFamilyProvider, Block> consumer = (BiConsumer)BlockModelGenerators.SHAPE_CONSUMERS.get(variant);
                    if (consumer != null) {
                        consumer.accept(this, result);
                    }
                }

            });
            return this;
        }
    }

    public void createBerryBush(Block berryBush, Item berry) {
        this.registerSimpleFlatItemModel(berry);
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(berryBush).with(PropertyDispatch.initial(BlockStateProperties.AGE_3).generate((age) -> plainVariant(this.createSuffixedVariant(berryBush, "_stage" + age, ModelTemplates.CROSS, TextureMapping::cross)))));
    }

    public void createBerryBush(DeferredBlock<Block> block, DeferredItem<Item> item){
        createBerryBush(block.get(), item.get());
    }

    public void createBerryBush(Block block, DeferredItem<Item> item){
        createBerryBush(block, item.get());
    }

    public void createBerryBush(DeferredBlock<Block> block, Item item){
        createBerryBush(block.get(), item);
    }
}
