package net.vincent.rulemaster.block.custom;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.BlockHitResult;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.attachments.ModAttachments;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class RevivalBlock extends RespawnAnchorBlock {

    private final ArrayList<Pair<ServerPlayer, ServerPlayer.RespawnConfig>> spawnPositionsList;
    private static final BooleanProperty SET = BooleanProperty.create("set");

    public RevivalBlock(Properties properties) {
        this.spawnPositionsList = new ArrayList<>();
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SET, false));
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @org.jspecify.annotations.Nullable BlockEntity blockEntity, ItemStack destroyedWith) {
        player.setData(ModAttachments.PLAYER_END_SPAWN_POS, BlockPos.ZERO);
        player.setData(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END, false);
        super.playerDestroy(level, player, pos, state, blockEntity, destroyedWith);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level instanceof ServerLevel serverLevel) {
            if (!canSetSpawn(serverLevel)) {
                player.sendOverlayMessage(Component.translatable("message.rulemaster.revival_block.cannot_set_spawn"));
                return InteractionResult.SUCCESS_SERVER;
            }
            if (player instanceof ServerPlayer serverPlayer) {
                if(!level.getBlockState(pos.above(2)).is(BlockTags.AIR) || !level.getBlockState(pos.above(1)).is(BlockTags.AIR)){
                    serverPlayer.sendOverlayMessage(Component.translatable("message.rulemaster.revival_block.obstructed_block"));
                    return InteractionResult.PASS;
                }
                if(state.getValue(SET)){
                    serverPlayer.setData(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END, false);
                    serverPlayer.setData(ModAttachments.PLAYER_END_SPAWN_POS, BlockPos.ZERO);
                    serverLevel.setBlockAndUpdate(pos, state.setValue(SET, false));
                    serverPlayer.sendSystemMessage(Component.translatable("message.rulemaster.revival_block.respawn_data_reset"));
                    serverLevel.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(),  SoundSource.BLOCKS, 1.0F, 1.0F);
                } else{
                    serverPlayer.setData(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END, true);
                    serverPlayer.setData(ModAttachments.PLAYER_END_SPAWN_POS, pos.above());
                    serverLevel.setBlockAndUpdate(pos, state.setValue(SET, true));
                    serverPlayer.sendSystemMessage(Component.translatable("message.rulemaster.revival_block.respawn_data_set"));
                    serverLevel.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN,  SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return InteractionResult.SUCCESS_SERVER;
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.CONSUME;
    }

    private @Nullable ServerPlayer.RespawnConfig getSpawnForPlayer(ServerPlayer serverPlayer) {
        for(Pair<ServerPlayer, ServerPlayer.RespawnConfig> KVP : this.spawnPositionsList){
            if(KVP.getFirst().equals(serverPlayer)){
                return KVP.getSecond();
            }
        }
        return null;
    }

    public static boolean canSetSpawn(ServerLevel level) {
        return level.dimension() == Level.END;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SET);
        super.createBlockStateDefinition(builder);
    }
}
