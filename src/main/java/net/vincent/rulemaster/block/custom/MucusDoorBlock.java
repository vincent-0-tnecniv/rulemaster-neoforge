package net.vincent.rulemaster.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TriState;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.block.interaction.ModBlockInteractions;
import net.vincent.rulemaster.tags.ModTags;
import org.jspecify.annotations.Nullable;

public class MucusDoorBlock extends DoorBlock {

    public static IntegerProperty TIMES_CLICKED = IntegerProperty.create("times_clicked", 0, 2);

    public MucusDoorBlock(Properties properties) {
        super(BlockSetType.ACACIA, properties.strength(-1.0F, 3600000.0F).friction(0.9999999f).noLootTable().isValidSpawn(Blocks::never).sound(SoundType.SLIME_BLOCK).lightLevel(_ -> 15));
        this.registerDefaultState(this.defaultBlockState().setValue(TIMES_CLICKED, 0));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        RuleMaster.LOGGER.info("return value 0 (base value)");
        if(player.isCreative() || player.getMainHandItem().is(ModTags.Items.MUCUS_DOOR_INFINITELY_OPENABLE)) {
            RuleMaster.LOGGER.info("return value 1");
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
        if(state.getValue(TIMES_CLICKED) == 2) {
            player.sendOverlayMessage(Component.translatable("block.rulemaster.mucus_door.open_failed"));
            RuleMaster.LOGGER.info("return value 2");
            return InteractionResult.FAIL;
        }
        BlockState newState = state.setValue(TIMES_CLICKED, state.getValue(TIMES_CLICKED) + 1);
        level.setBlockAndUpdate(pos, newState);
        RuleMaster.LOGGER.info("return value 3");
        return super.useWithoutItem(newState, level, pos, player, hitResult);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return false;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return false;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        return TriState.FALSE;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        ModBlockInteractions.MucusBlock.animateTickForMucusBlockLike(state, level, pos, random, super::animateTick);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIMES_CLICKED);
        super.createBlockStateDefinition(builder);
    }
}
