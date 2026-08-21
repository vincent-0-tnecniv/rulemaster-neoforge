package net.vincent.rulemaster.util.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import java.util.List;
import java.util.stream.Collectors;

public interface IEntityMethodProvider {
    default List<EntityType<?>> getEntityTypesFromTag(HolderLookup.Provider provider, TagKey<EntityType<?>> tagKey) {
        var lookup = provider.lookupOrThrow(Registries.ENTITY_TYPE);
        var holders = lookup.getOrThrow(tagKey);
        return holders.stream()
                .map(Holder::value)
                .collect(Collectors.toList());
    }

    default ResourceKey<EntityType<?>> getRK(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getResourceKey(entityType).orElseThrow();
    }
}
