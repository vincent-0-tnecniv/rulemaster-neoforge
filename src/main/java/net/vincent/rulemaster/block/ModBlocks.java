package net.vincent.rulemaster.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.block.custom.BloodCrystalBlock;
import net.vincent.rulemaster.block.custom.FleshBlock;
import net.vincent.rulemaster.item.ModItems;

import java.util.function.Consumer;
import java.util.function.Function;

public class ModBlocks {

    public static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RuleMaster.MOD_ID);

    public static DeferredBlock<Block> FLESH_BLOCK = registerBlock("flesh_block",
            FleshBlock::new);

    public static DeferredBlock<Block> FLESH_SLAB = registerBlock("flesh_slab",
            properties -> new SlabBlock(properties.strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn(Blocks::never).sound(SoundType.SLIME_BLOCK)) {
                @Override
                protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
                    if(itemStack.getItem() instanceof BlockItem){
                        player.sendOverlayMessage(Component.literal("The flesh is consuming your block, but you pulled it out of the block."));
                        return InteractionResult.CONSUME;
                    }
                    return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
                }
            });

    public static DeferredBlock<Block> BLOOD_CRYSTAL_BLOCK = registerBlock("blood_crystal_block",
            BloodCrystalBlock::new);

    private static DeferredBlock<Block> registerExperienceDroppingOre(String name, int minXp, int maxXp, float strength, SoundType soundType) {
        return registerBlock(name,
                properties -> new DropExperienceBlock(UniformInt.of(minXp, maxXp), properties.strength(strength)
                        .requiresCorrectToolForDrops().sound(soundType)));
    }

    private static DeferredBlock<Block> registerExperienceDroppingOre(String name, int minXp, int maxXp, float strength) {
        return registerBlock(name,
                properties -> new DropExperienceBlock(UniformInt.of(minXp, maxXp), properties.strength(strength)
                        .requiresCorrectToolForDrops().sound(SoundType.STONE)));
    }

    private static <T extends Block> DeferredBlock<T> registerBlockWithoutItem(String name, Function<BlockBehaviour.Properties, T> function) {
        return BLOCKS.registerBlock(name, function);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function, Component... components) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn, components);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block, Component... components) {
        ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()) {
            @Override
            public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
                for(Component component : components) {
                    builder.accept(component);
                }
                super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
            }
        });
    }

    public static ResourceKey<Block> getRK(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).get();
    }

    public static ResourceKey<Block> getRK(DeferredBlock<Block> block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block.get()).get();
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

}
