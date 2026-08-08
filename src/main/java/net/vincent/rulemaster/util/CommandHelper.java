package net.vincent.rulemaster.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

import java.util.UUID;

public class CommandHelper {
    protected static Entity findEntityByUUID(ServerLevel level, UUID uuid) {
        // Check all loaded entities in the level
        for (Entity entity : level.getAllEntities()) {
            if (entity.getUUID().equals(uuid)) {
                return entity;
            }
        }
        return null;
    }
}
