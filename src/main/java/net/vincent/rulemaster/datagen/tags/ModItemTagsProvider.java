package net.vincent.rulemaster.datagen.tags;

import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.item.ModItems;
import net.vincent.rulemaster.tags.ModTags;
import net.vincent.rulemaster.util.datagen.BaseItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends BaseItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RuleMaster.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
//        addToTag(ModTags.Items.TRANSFORMABLE_ITEMS,
//            List.of(
//                Items.IRON_INGOT,
//                Items.REDSTONE,
//                Items.COPPER_INGOT
//            ));
//        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
//                .add(ModItems.getRK(ModItems.AZURITE));
        tag(ModTags.Items.BLOOD_CRYSTAL_REPAIRABLE)
                .add(ModItems.getRK(ModItems.BLOOD_CRYSTAL));

//        allTools(ModItems.AZURITE_SWORD, ModItems.AZURITE_PICKAXE, ModItems.AZURITE_AXE,
//                ModItems.AZURITE_SHOVEL, ModItems.AZURITE_HOE, ModItems.AZURITE_SPEAR);
//
//        allArmor(ModItems.AZURITE_HELMET, ModItems.AZURITE_CHESTPLATE,
//                ModItems.AZURITE_LEGGINGS, ModItems.AZURITE_BOOTS, true);

//        tag(ItemTags.BOW_ENCHANTABLE)
//                .add(ModItems.getRK(ModItems.KAUPEN_BOW));
//
//        tag(ItemTags.CREEPER_DROP_MUSIC_DISCS)
//                .add(ModItems.getRK(ModItems.BAR_BRAWL_MUSIC_DISC));

        spear(ModItems.BLOOD_PIERCER);

    }
}
