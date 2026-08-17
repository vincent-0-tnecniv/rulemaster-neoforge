package net.vincent.rulemaster.datagen.loot;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.tags.ModTags;
import net.vincent.rulemaster.util.datagen.BaseGlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends BaseGlobalLootModifierProvider {

    public final CompletableFuture<HolderLookup.Provider> registries;

    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        this.registries = registries;
        super(output, registries, RuleMaster.MOD_ID);
    }

    @Override
    protected void start() {
        HolderLookup.Provider provider = this.registries.join();
        HolderGetter<EntityType<?>> entities = provider.lookupOrThrow(Registries.ENTITY_TYPE);

        add("blood_crystal_from_blood_infused_humanoid", entityTagLoot(entities, ModTags.EntityTypes.DROPS_BC_ALL_CHANCE, ModExtraLootProvider.BLOOD_CRYSTAL_FROM_BLOOD_INFUSED_HUMANOID));
        add("blood_crystal_from_non_infused_living_humanoid", entityTagLoot(entities, ModTags.EntityTypes.DROPS_BC_HALF_CHANCE, ModExtraLootProvider.BLOOD_CRYSTAL_FROM_LIVING_HUMANOID));
    }
}
