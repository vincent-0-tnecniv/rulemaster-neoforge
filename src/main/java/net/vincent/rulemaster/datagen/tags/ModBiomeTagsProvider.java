package net.vincent.rulemaster.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends TagsProvider<Biome> {

    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.BIOME, lookupProvider, RuleMaster.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.StructureBiomes.CRADLE_OF_LIFE_BIOMES, false)
                .add(Biomes.PLAINS)
                .add(Biomes.DESERT)
                .add(Biomes.BADLANDS)
                .addOptionalTag(createCompatibleKey("is_plains"))
                .addOptionalTag(createCompatibleKey("is_desert"))
                .addOptionalTag(createCompatibleKey("is_badlands"));
    }

    private static TagKey<Biome> createCompatibleKey(String searcherKey) {
        // this generates a TagKey<Biome> #c:{searcherKey}
        // this syntax, agreed upon by both NeoForge and Fabric, increases mod compatibility

        // for what the "searcherKey" should be, refer to this link
        // https://github.com/neoforged/NeoForge/tree/1.21.1/src/generated/resources/data/c/tags/worldgen/biome
        return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath("c", searcherKey));
    }

    private static TagKey<Biome> createKey(String structureId) {
        return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID,
                "has_structure/" + structureId + "_biomes"));
    }
}
