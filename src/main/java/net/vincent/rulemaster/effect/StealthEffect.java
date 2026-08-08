package net.vincent.rulemaster.effect;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class StealthEffect extends MobEffect {

    protected StealthEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity entity, int amplifier) {
//        if (entity instanceof Player player && player.level().isClientSide()) {
//            MinecraftClient client = MinecraftClient.getInstance();
//            if (client.options.getGamma().getValue() < 1.0) {
//                client.options.getGamma().setValue(1.0);
//            }
//        }
        return super.applyEffectTick(serverLevel, entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }
}