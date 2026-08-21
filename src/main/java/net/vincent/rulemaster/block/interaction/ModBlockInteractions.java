package net.vincent.rulemaster.block.interaction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.vincent.rulemaster.block.interaction.super_objects.SuperAnimateTick;
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

            if(player.getMainHandItem().isEmpty()) {
                return InteractionResult.FAIL;
            }

            if (itemStack.getItem() instanceof BlockItem) {
                if (!level.isClientSide()) {
                    player.sendOverlayMessage(Component.translatable("block.rulemaster.flesh_block.place_block_failed"));
                }
                return InteractionResult.CONSUME;
            }

            return superObject.originalUseItemOn(itemStack, state, level, pos, player, hand, hitResult);
        }
    }

    public static class MucusBlock {
        public static void animateTickForMucusBlockLike(BlockState state, Level level, BlockPos pos, RandomSource random, SuperAnimateTick superObject) {
            superObject.animateTick(state, level, pos, random);
            if (random.nextInt(5) == 0) {
                double x = pos.getX() + random.nextDouble();
                double y = pos.getY() + 1.0D;
                double z = pos.getZ() + random.nextDouble();

                // Water drip particles
                level.addParticle(ParticleTypes.FALLING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);

                // Or use slime particles for green drips
                // level.addParticle(ParticleTypes.ITEM_SLIME, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
