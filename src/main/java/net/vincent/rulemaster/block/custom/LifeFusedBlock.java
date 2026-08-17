package net.vincent.rulemaster.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.vincent.rulemaster.attachments.ModAttachments;
import org.jetbrains.annotations.NotNull;

public class LifeFusedBlock extends RespawnAnchorBlock {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public LifeFusedBlock(Properties properties) {
        super(properties.requiresCorrectToolForDrops().strength(50f, 1200f).lightLevel(_ -> 15));
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @org.jspecify.annotations.Nullable BlockEntity blockEntity, ItemStack destroyedWith) {
        MinecraftServer server = level.getServer();
        if(server == null) return;
        clearEndRespawnWithPos(server, pos);
        super.playerDestroy(level, player, pos, state, blockEntity, destroyedWith);
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        MinecraftServer server = level.getServer();
        if(server == null) return;
        clearEndRespawnWithPos(server, pos);
        super.destroy(level, pos, state);
    }

    private void clearEndRespawnWithPos(@NotNull MinecraftServer server, BlockPos pos) {
        for(ServerPlayer serverPlayer : server.getPlayerList().getPlayers()) {
            if(serverPlayer.getData(ModAttachments.PLAYER_END_SPAWN_POS).below().equals(pos)){
                serverPlayer.setData(ModAttachments.PLAYER_END_SPAWN_POS, BlockPos.ZERO);
                serverPlayer.setData(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END, false);
            }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level instanceof ServerLevel serverLevel) {
            if (!canSetSpawn(serverLevel)) {
                player.sendOverlayMessage(Component.translatable("message.rulemaster.life_fused_block.cannot_set_spawn"));
                return InteractionResult.SUCCESS_SERVER;
            }
            if (player instanceof ServerPlayer serverPlayer) {
                if(!level.getBlockState(pos.above(2)).is(BlockTags.AIR) || !level.getBlockState(pos.above(1)).is(BlockTags.AIR)){
                    serverPlayer.sendOverlayMessage(Component.translatable("message.rulemaster.life_fused_block.obstructed_block"));
                    return InteractionResult.PASS;
                }
                if(player.isShiftKeyDown()){
                    serverPlayer.setData(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END, false);
                    serverPlayer.setData(ModAttachments.PLAYER_END_SPAWN_POS, BlockPos.ZERO);
                    serverPlayer.sendSystemMessage(Component.translatable("message.rulemaster.life_fused_block.respawn_data_reset"));
                    serverLevel.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(),  SoundSource.BLOCKS, 1.0F, 1.0F);
                } else{
                    serverPlayer.setData(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END, true);
                    serverPlayer.setData(ModAttachments.PLAYER_END_SPAWN_POS, pos.above());
                    serverPlayer.sendSystemMessage(Component.translatable("message.rulemaster.life_fused_block.respawn_data_set"));
                    serverLevel.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN,  SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return InteractionResult.SUCCESS_SERVER;
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.CONSUME;
    }

    public static boolean canSetSpawn(ServerLevel level) {
        return level.dimension() == Level.END;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
        super.createBlockStateDefinition(builder);
    }
}
