package net.vincent.rulemaster.block.custom.block_entity_base;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.vincent.rulemaster.block.custom.template.MobSpawnerBlock;
import net.vincent.rulemaster.block.entity.ModBlockEntities;
import net.vincent.rulemaster.block.entity.custom.CoreOfWombBlockEntity;
import org.jspecify.annotations.Nullable;

public class CoreOfWombBlock extends MobSpawnerBlock {

    public static final MapCodec<CoreOfWombBlock> CODEC = simpleCodec(CoreOfWombBlock::new);

    public CoreOfWombBlock(Properties properties) {
        super(properties.strength(-1, 3600000).noLootTable());
    }

    @Override
    protected BlockEntityType<?> blockEntityType() {
        return ModBlockEntities.CORE_OF_WOMB_BE.get();
    }

    @Override
    protected <T extends BlockEntity> BlockEntityTicker<? super T> ticker() {
        return CoreOfWombBlockEntity::tick;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CoreOfWombBlockEntity(blockPos, blockState);
    }

//    @Override
//    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
//        // This is a summoning block - no interaction on client needed for now
//        if(level.isClientSide()) return null;
//        return createTickerHelper(type, ModBlockEntities.CORE_OF_WOMB_BE.get(), CoreOfWombBlockEntity::tick);
//    }


}
