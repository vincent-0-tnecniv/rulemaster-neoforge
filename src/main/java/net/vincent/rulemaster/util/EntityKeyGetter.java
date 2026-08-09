package net.vincent.rulemaster.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public class EntityKeyGetter {
    public static ResourceKey<EntityType<?>> getRK(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getResourceKey(entityType).get();
    }
}
