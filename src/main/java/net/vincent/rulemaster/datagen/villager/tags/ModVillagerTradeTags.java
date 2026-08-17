package net.vincent.rulemaster.datagen.villager.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VillagerTradesTagsProvider;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.VillagerTradeTags;
import net.vincent.rulemaster.datagen.villager.datapack.ModVillagerTrades;

import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeTags extends VillagerTradesTagsProvider {
    public ModVillagerTradeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(VillagerTradeTags.CLERIC_LEVEL_1)
                .add(TagEntry.element(ModVillagerTrades.CLERIC_1_ROTTEN_FLESH_BLOOD_CRYSTAL.identifier()));
        getOrCreateRawBuilder(VillagerTradeTags.CLERIC_LEVEL_2)
                .add(TagEntry.element(ModVillagerTrades.CLERIC_2_EMERALD_BLOOD_CRYSTAL.identifier()));
        getOrCreateRawBuilder(VillagerTradeTags.LIBRARIAN_LEVEL_3)
                .add(TagEntry.element(ModVillagerTrades.LIBRARIAN_3_EMERALD_BLOOD_CRYSTAL_LIVO_GUIDE_BOOK.identifier()));
        getOrCreateRawBuilder(VillagerTradeTags.CARTOGRAPHER_LEVEL_1)
                .add(TagEntry.element(ModVillagerTrades.CARTOGRAPHER_1_BLOOD_CRYSTAL_AND_COMPASS_CRADLE_OF_LIFE_MAP.identifier()));
        getOrCreateRawBuilder(VillagerTradeTags.WANDERING_TRADER_BUYING)
                .add(TagEntry.element(ModVillagerTrades.WANDERING_TRADER_EMERALD_AND_BLOOD_CRYSTAL_CRADLE_OF_LIFE_MAP.identifier()));
    }
}
