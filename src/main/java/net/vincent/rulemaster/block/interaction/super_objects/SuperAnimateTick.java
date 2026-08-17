package net.vincent.rulemaster.block.interaction.super_objects;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface SuperAnimateTick {
    void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random);
}
