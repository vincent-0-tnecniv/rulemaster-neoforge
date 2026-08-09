package net.vincent.rulemaster;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.vincent.rulemaster.datagen.ModBlockLootTableProvider;
import net.vincent.rulemaster.datagen.ModModelProvider;
import net.vincent.rulemaster.datagen.datapack.ModDataPackProvider;
import net.vincent.rulemaster.datagen.tags.ModBlockTagsProvider;
import net.vincent.rulemaster.datagen.tags.ModDamageTypeTagsProvider;
import net.vincent.rulemaster.datagen.tags.ModEntityTypeTagsProvider;
import net.vincent.rulemaster.datagen.tags.ModItemTagsProvider;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = RuleMaster.MOD_ID)
public class RuleMasterDataGen {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new ModModelProvider(packOutput));
        generator.addProvider(true, new ModDataPackProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModItemTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModDamageTypeTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModBlockTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModEntityTypeTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));

    }
}
