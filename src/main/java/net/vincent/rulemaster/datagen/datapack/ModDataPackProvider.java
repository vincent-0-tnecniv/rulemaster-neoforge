package net.vincent.rulemaster.datagen.datapack;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.datagen.datapack.damage.ModDamageTypes;
import net.vincent.rulemaster.datagen.villager.datapack.ModTradeSets;
import net.vincent.rulemaster.datagen.villager.datapack.ModVillagerTrades;
import net.vincent.rulemaster.worldgen.ModBiomeModifiers;
import net.vincent.rulemaster.worldgen.ModConfiguredFeatures;
import net.vincent.rulemaster.worldgen.ModPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDataPackProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap)
            .add(Registries.TRADE_SET, ModTradeSets::bootstrap) // doesn't do anything else yet
            .add(Registries.VILLAGER_TRADE, ModVillagerTrades::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    public ModDataPackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(RuleMaster.MOD_ID));
    }
}
