package net.vincent.rulemaster.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {

    public static final KeyMapping KEY_MAPPING_TOTEM_ACTIVATION = new KeyMapping("key.rulemaster.totem_activation",
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J, ModKeyMappingCategories.RULEMASTER_KEYMAPPING_CATEGORY);

    public static final Lazy<KeyMapping> PRESS_TOTEM_ACTIVATION = Lazy.of(() -> KEY_MAPPING_TOTEM_ACTIVATION);

    public static final KeyMapping KEY_MAPPING_VITALITY_SWAP = new KeyMapping("key.rulemaster.vitality_swap",
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, ModKeyMappingCategories.RULEMASTER_KEYMAPPING_CATEGORY);

    public static final Lazy<KeyMapping> PRESS_VITALITY_SWAP = Lazy.of(() -> KEY_MAPPING_VITALITY_SWAP);

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(KEY_MAPPING_TOTEM_ACTIVATION);
        event.register(KEY_MAPPING_VITALITY_SWAP);
    }
}
