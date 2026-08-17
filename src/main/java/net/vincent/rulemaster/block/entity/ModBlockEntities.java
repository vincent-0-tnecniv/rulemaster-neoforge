package net.vincent.rulemaster.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.block.ModBlocks;
import net.vincent.rulemaster.block.entity.custom.CoreOfWombBlockEntity;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, RuleMaster.MOD_ID);

    public static Supplier<BlockEntityType<CoreOfWombBlockEntity>> CORE_OF_WOMB_BE =
            BLOCK_ENTITIES.register("core_of_womb_be",
                    () -> new BlockEntityType<>(CoreOfWombBlockEntity::new, ModBlocks.CORE_OF_WOMB.get()));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }

}
