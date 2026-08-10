package net.vincent.rulemaster.block.interaction.super_objects;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@FunctionalInterface
public interface SuperUseItemOn<T> {
    T originalUseItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                        Player player, InteractionHand hand, BlockHitResult hit);
}
// Functional Interface is something that, when created, stores an object inside?
// The object can be a method object?