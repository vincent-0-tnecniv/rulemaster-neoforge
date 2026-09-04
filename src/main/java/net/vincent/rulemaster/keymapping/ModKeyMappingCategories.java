package net.vincent.rulemaster.keymapping;

import net.minecraft.client.KeyMapping;

public class ModKeyMappingCategories {
    public static final KeyMapping.Category RULEMASTER_KEYMAPPING_CATEGORY =
            register("rulemaster");

    private static KeyMapping.Category register(String name) {
        return KeyMapping.Category.register("rulemaster." + name);
    }
}
