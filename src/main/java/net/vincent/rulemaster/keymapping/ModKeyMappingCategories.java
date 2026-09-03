package net.vincent.rulemaster.keymapping;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.vincent.rulemaster.RuleMaster;

public class ModKeyMappingCategories {
    public static final KeyMapping.Category RULEMASTER_KEYMAPPING_CATEGORY =
            register("rulemaster");

    private static KeyMapping.Category register(String name) {
        return KeyMapping.Category.register(Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, name));
    }
}
