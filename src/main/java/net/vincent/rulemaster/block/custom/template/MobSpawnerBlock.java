package net.vincent.rulemaster.block.custom.template;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.vincent.rulemaster.RuleMaster;
import org.jspecify.annotations.Nullable;

public abstract class MobSpawnerBlock extends BaseEntityBlock {

    public MobSpawnerBlock(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("unchecked") // Try-catch statements deals with unchecked casting
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        try {
            return getTicker(level, type, (BlockEntityType<T>) blockEntityType(), ticker());
        }
        catch (Exception _) {
            RuleMaster.LOGGER.error("Failed to load blockEntityTicker for mob spawner");
            return null;
        }
    }

    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockEntityType<T> type, BlockEntityType<T> summonerBlock, BlockEntityTicker<? super T> summonerBlockTicker) {
        // This is a summoning block - no interaction on client needed for now
        if(level.isClientSide()) return null;
        return createTickerHelper(type, summonerBlock, summonerBlockTicker);
    }

    protected abstract BlockEntityType<? extends BlockEntity> blockEntityType();

    protected abstract <T extends BlockEntity> BlockEntityTicker<? super T> ticker();
}