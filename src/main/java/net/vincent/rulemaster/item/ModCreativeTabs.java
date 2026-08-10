package net.vincent.rulemaster.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RuleMaster.MOD_ID);

    public static final Supplier<CreativeModeTab> RULEMASTER_TAB = CREATIVE_MODE_TABS.register("azurite_block_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BLOOD_CRYSTAL.get()))
                    .title(Component.translatable("creativetab.rulemaster.items"))
                    .displayItems((_, output) -> {
                        output.accept(ModItems.BLOOD_CRYSTAL);
                        output.accept(ModItems.BLOOD_CRYSTAL_STAFF);
                        output.accept(ModItems.BLOOD_PIERCER);
                        output.accept(ModItems.FLESH_BLOCK_TEST_ITEM);
                        output.accept(ModItems.LIVO_GUIDE_BOOK);
                        output.accept(ModItems.EYE_OF_BIRTH);

                        output.accept(ModBlocks.FLESH_BLOCK);
                        output.accept(ModBlocks.FLESH_SLAB);
                        output.accept(ModBlocks.BLOOD_CRYSTAL_BLOCK);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
