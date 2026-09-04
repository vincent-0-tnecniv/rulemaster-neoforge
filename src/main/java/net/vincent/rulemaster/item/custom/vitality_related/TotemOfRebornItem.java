package net.vincent.rulemaster.item.custom.vitality_related;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.vincent.rulemaster.keymapping.ModKeyMappings;

import java.util.function.Consumer;

public class TotemOfRebornItem extends Item {

    public static final float STARTING_VITALITY = 100;
    public static final int MAXIMUM_VITALITY = 100;
    public static final float VITALITY_REGEN = 0.005f;

    public TotemOfRebornItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable("item.rulemaster.totem_of_reborn.tooltip",
                ModKeyMappings.KEY_MAPPING_TOTEM_ACTIVATION.getTranslatedKeyMessage().getString()
        ));
    }
}
