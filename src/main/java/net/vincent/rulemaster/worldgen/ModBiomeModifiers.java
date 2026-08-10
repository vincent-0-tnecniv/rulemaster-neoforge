package net.vincent.rulemaster.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.vincent.rulemaster.RuleMaster;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_CRADLE_OF_LIFE = registerKey("add_cradle_of_life");


    public static void bootstrap(BootstrapContext<BiomeModifier> context){
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
        var structureSets = context.lookup(Registries.STRUCTURE_SET);

    }

    public static ResourceKey<BiomeModifier> registerKey(String name){
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
    }
}
