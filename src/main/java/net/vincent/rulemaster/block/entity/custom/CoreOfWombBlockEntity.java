package net.vincent.rulemaster.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.vincent.rulemaster.block.entity.ModBlockEntities;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class CoreOfWombBlockEntity extends BlockEntity {
    public CoreOfWombBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.CORE_OF_WOMB_BE.get(), worldPosition, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, CoreOfWombBlockEntity blockEntity) {
        if(level.isClientSide()){
            return;
            // no logic added for now
        }
        ServerLevel serverLevel = (ServerLevel) level;
        AABB summoningArea = new AABB(blockPos).inflate(8);
        List<Entity> entities = serverLevel.getEntities(null, summoningArea);
        for(Entity entity : entities){
            if(entity instanceof Player player && canSpawnMob(player, serverLevel)) {
                serverLevel.destroyBlock(blockPos, false);
                EntityTypes.WITHER.spawn(serverLevel, blockPos, EntitySpawnReason.TRIGGERED);
            }
        }
    }

    private static boolean canSpawnMob(Player player, ServerLevel level) {
        return isValidGameMode(player) && level.getDifficulty() != Difficulty.PEACEFUL;
    }

    private static boolean isValidGameMode(Player player) {
        return !player.isCreative() && !player.isSpectator();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
