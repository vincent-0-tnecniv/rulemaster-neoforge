package net.vincent.rulemaster.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.vincent.rulemaster.RuleMaster;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagsProvider extends TagsProvider<DamageType> {
    public ModDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, RuleMaster.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        ResourceKey<DamageType> bleedingKey = ResourceKey.create(
                Registries.DAMAGE_TYPE,
                Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "bleeding")
        );

        tag(DamageTypeTags.SULFUR_CUBE_WITH_BLOCK_IMMUNE_TO)
                .addOptional(bleedingKey); // for some reason, it has to be optional or else it crashes
    }
}
