package net.vincent.rulemaster.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vincent.rulemaster.RuleMaster;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, RuleMaster.MOD_ID);



    public static final Holder<MobEffect> STEALTH = MOB_EFFECTS.register("stealth",
            () -> new StealthEffect(MobEffectCategory.BENEFICIAL, 0x000000)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "stealth"), 0.4f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final Holder<MobEffect> STUN = MOB_EFFECTS.register("stun",
            () -> new StunEffect(MobEffectCategory.NEUTRAL, 0xffffff)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "stun"), -10.0f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE,
                            Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "stun"), -10.0f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE,
                            Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "stun"), -10.0f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.JUMP_STRENGTH,
                            Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "stun"), -10.0f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE,
                            Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "stun"), -10.0f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.FLYING_SPEED,
                            Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "stun"), -10.0f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}