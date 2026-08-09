package net.vincent.rulemaster.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.vincent.rulemaster.util.BlockWithState;

public class FleshBlock extends BlockWithState {

    public static final IntegerProperty TOC = IntegerProperty.create("toc", 0, 35);

    public FleshBlock(Properties properties) {
        super(properties.strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn(Blocks::never).sound(SoundType.SLIME_BLOCK));
        this.registerDefaultState(this.defaultBlockState().setValue(TOC, 0));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(itemStack.getItem() instanceof BlockItem){
            player.sendOverlayMessage(Component.literal("The flesh is consuming your block, but you pulled it out of the block."));
            return InteractionResult.CONSUME;
        }
        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TOC);
    }
}
