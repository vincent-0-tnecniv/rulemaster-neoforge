package net.vincent.rulemaster.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.block.ModBlocks;

import java.util.function.Supplier;

public class ModItemGroups {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RuleMaster.MOD_ID);

    public static final Supplier<CreativeModeTab> RULEMASTER_TAB = createTab(
            "azurite_block_tab", ModItems.BLOOD_CRYSTAL,
            "creativetab.rulemaster.items",
            ModItems.BLOOD_CRYSTAL,
            ModItems.BLOOD_CRYSTAL_STAFF,
            ModItems.BLOOD_PIERCER,
            ModItems.FLESH_BLOCK_TEST_ITEM,
            ModItems.LIVO_GUIDE_BOOK,
            ModItems.EYE_OF_BIRTH,
            ModItems.KEY_OF_EMBRYO,

            ModBlocks.FLESH_BLOCK,
            ModBlocks.FLESH_SLAB,
            ModBlocks.BLOOD_CRYSTAL_BLOCK,
            ModBlocks.MUCUS_BLOCK,
            ModBlocks.MUCUS_STAIRS,
            ModBlocks.MUCUS_DOOR,
            ModBlocks.CORE_OF_WOMB,
            ModBlocks.LIFE_FUSED_BLOCK
            );

//    public static final Supplier<CreativeModeTab> RULEMASTER_TAB = CREATIVE_MODE_TABS.register("azurite_block_tab",
//            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BLOOD_CRYSTAL.get()))
//                    .title(Component.translatable("creativetab.rulemaster.items"))
//                    .displayItems((_, output) -> {
//                        output.accept(ModItems.BLOOD_CRYSTAL);
//                        output.accept(ModItems.BLOOD_CRYSTAL_STAFF);
//                        output.accept(ModItems.BLOOD_PIERCER);
//                        output.accept(ModItems.FLESH_BLOCK_TEST_ITEM);
//                        output.accept(ModItems.LIVO_GUIDE_BOOK);
//                        output.accept(ModItems.EYE_OF_BIRTH);
//                        output.accept(ModItems.KEY_OF_EMBRYO);
//
//                        output.accept(ModBlocks.FLESH_BLOCK);
//                        output.accept(ModBlocks.FLESH_SLAB);
//                        output.accept(ModBlocks.BLOOD_CRYSTAL_BLOCK);
//                        output.accept(ModBlocks.MUCUS_BLOCK);
//                        output.accept(ModBlocks.MUCUS_STAIRS);
//                        output.accept(ModBlocks.MUCUS_DOOR);
//                        output.accept(ModBlocks.CORE_OF_WOMB);
//                        output.accept(ModBlocks.LIFE_FUSED_BLOCK);
//                    })
//                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
        RuleMaster.LOGGER.info("Registered creative tab: {}", RULEMASTER_TAB);
    }

    private static Supplier<CreativeModeTab> createTab(String tabId, ItemLike menuItem, String translationKey, ItemLike... items) {
        return CREATIVE_MODE_TABS.register(tabId,
                () -> CreativeModeTab.builder().icon(() -> new ItemStack(menuItem))
                        .title(Component.translatable(translationKey))
                        .displayItems((_, output) -> {
                            for(ItemLike item : items) {
                                output.accept(item);
                            }
                        })
                        .build());
    }
}
