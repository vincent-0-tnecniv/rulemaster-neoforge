package net.vincent.rulemaster.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.datagen.structure.structures.ModStructures;
import net.vincent.rulemaster.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModStructureTagsProvider extends StructureTagsProvider {
    public ModStructureTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RuleMaster.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(ModTags.Structures.EYE_OF_BIRTH_LOCATED)
                .addOptional(ModStructures.CRADLE_OF_LIFE);
    }
}
