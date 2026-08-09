package net.vincent.rulemaster.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.vincent.rulemaster.block.ModBlocks;
import net.vincent.rulemaster.block.custom.FleshBlock;

import java.util.function.Consumer;

public class FleshBlockTestItem extends Item {
    public FleshBlockTestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide()) {return super.use(level, player, hand);}
        if(player.isShiftKeyDown()) {
            handleMaximumFleshBlockStateChanges((ServerLevel) level, player);
        } else{
            handleNormalFleshBlockStateChanges((ServerLevel) level, player);
        }
        return super.use(level, player, hand);
    }

    private void handleNormalFleshBlockStateChanges(ServerLevel level, Player player) {
        handleFleshBlockStateChanges(level, player, 28);
    }

    private void handleMaximumFleshBlockStateChanges(ServerLevel level, Player player) {
        handleFleshBlockStateChanges(level, player, 35);
    }

    private void handleFleshBlockStateChanges(ServerLevel level, Player player, int maxPropertyCount) {
        BlockPos pos = player.getOnPos();
        for(int i = -60; i <= 60; i++) {
            for(int j = -60; j <= 60; j++) {
                for(int k = -60; k <= 60; k++) {
                    BlockPos newPos = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
                    BlockState state = level.getBlockState(newPos);
                    if(state.is(ModBlocks.FLESH_BLOCK.get())) {
                        level.setBlockAndUpdate(newPos, state.setValue(FleshBlock.TOC, (state.getValue(FleshBlock.TOC) + 1) % (maxPropertyCount + 1)));
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.literal("Click to test phase 1 changes (from 0 to 28)"));
        builder.accept(Component.literal("Sneak click to test phase 2 changes (from 0 to 35)"));
        builder.accept(Component.literal(""));
        builder.accept(Component.literal("If you cannot see a texture - don't worry, you're fine."));
        builder.accept(Component.literal("This is a test item, no texture is given to it."));
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }
}
