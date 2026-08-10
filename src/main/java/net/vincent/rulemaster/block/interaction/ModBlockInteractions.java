package net.vincent.rulemaster.block.interaction;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.vincent.rulemaster.block.interaction.super_objects.SuperUseItemOn;

public class ModBlockInteractions {
    public static class FleshBlock {
        public static InteractionResult useItemOnFleshBlockLike(
                ItemStack itemStack,
                BlockState state,
                Level level,
                BlockPos pos,
                Player player,
                InteractionHand hand,
                BlockHitResult hitResult,
                SuperUseItemOn<InteractionResult> superObject) {

            if (player.isCreative()) {
                return superObject.originalUseItemOn(itemStack, state, level, pos, player, hand, hitResult);
            }

            if(player.getItemInHand(hand).getItem() instanceof AirItem) {
                return InteractionResult.FAIL;
            }

            if (itemStack.getItem() instanceof BlockItem) {
                if (!level.isClientSide()) {
                    player.sendSystemMessage(Component.translatable("block.rulemaster.flesh_block.place_block_failed"));
                }
                return InteractionResult.CONSUME;
            }

            return superObject.originalUseItemOn(itemStack, state, level, pos, player, hand, hitResult);
        }
    }
}
