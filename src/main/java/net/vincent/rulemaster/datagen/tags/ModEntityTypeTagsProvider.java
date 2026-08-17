package net.vincent.rulemaster.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends TagsProvider<EntityType<?>> {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ENTITY_TYPE, lookupProvider, RuleMaster.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ModTags.EntityTypes.BLOOD_INFUSED_HUMANOID)
                .add(getRK(EntityTypes.WITCH));
        tag(ModTags.EntityTypes.LIVING_HUMANOID)
                .add(getRK(EntityTypes.VILLAGER))
                .add(getRK(EntityTypes.WANDERING_TRADER))
                .add(getRK(EntityTypes.PIGLIN))
                .add(getRK(EntityTypes.PIGLIN_BRUTE))
                .add(getRK(EntityTypes.PLAYER))
                .add(getRK(EntityTypes.PILLAGER))
                .add(getRK(EntityTypes.VINDICATOR))
                .add(getRK(EntityTypes.EVOKER))
                .addTag(ModTags.EntityTypes.BLOOD_INFUSED_HUMANOID);
        tag(ModTags.EntityTypes.HUMANOID)
                .add(getRK(EntityTypes.ZOMBIE))
                .add(getRK(EntityTypes.ZOMBIE_VILLAGER))
                .add(getRK(EntityTypes.DROWNED))
                .add(getRK(EntityTypes.HUSK))
                .add(getRK(EntityTypes.ZOMBIFIED_PIGLIN))
                .addTag(ModTags.EntityTypes.LIVING_HUMANOID);
        tag(ModTags.EntityTypes.DROPS_BC_ALL_CHANCE)
                .addTag(ModTags.EntityTypes.BLOOD_INFUSED_HUMANOID);
        tag(ModTags.EntityTypes.DROPS_BC_HALF_CHANCE)
                .addTag(ModTags.EntityTypes.LIVING_HUMANOID)
                .remove(ModTags.EntityTypes.BLOOD_INFUSED_HUMANOID)
                .remove(getRK(EntityTypes.PLAYER));
    }

    protected ResourceKey<EntityType<?>> getRK(EntityType<?> entityType){
        return BuiltInRegistries.ENTITY_TYPE.getResourceKey(entityType).get();
    }
}
