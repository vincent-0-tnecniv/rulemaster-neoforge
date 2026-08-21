package net.vincent.rulemaster.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public interface IConfigValidator {
    default boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(Identifier.parse(itemName));
    }

    default boolean validateBlockName(final Object obj) {
        return obj instanceof String blockName && BuiltInRegistries.BLOCK.containsKey(Identifier.parse(blockName));
    }

    default boolean validateEntityType(final Object obj) {
        return obj instanceof String entityType && BuiltInRegistries.ENTITY_TYPE.containsKey(Identifier.parse(entityType));
    }
}
