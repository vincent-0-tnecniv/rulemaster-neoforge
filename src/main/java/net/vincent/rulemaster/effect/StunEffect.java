package net.vincent.rulemaster.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;

public class StunEffect extends MobEffect {

    protected StunEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if(entity instanceof Mob mob){
            mob.setTarget(null);
            mob.setLastHurtByMob(null);
            if(mob instanceof ZombifiedPiglin piggie){
                piggie.setTimeToRemainAngry(0);
            }
            float entityPostYaw = entity.getYRot() + 15.0F * (amplifier + 1);
            if(entityPostYaw > 180.0F){
                entityPostYaw -= 360.0F;
            }
            mob.setYRot(entityPostYaw);
            mob.setYHeadRot(entityPostYaw);
            mob.setYBodyRot(entityPostYaw);
        }
        if(entity instanceof Player player){
            float entityPostYaw = entity.getYRot() + 15.0F * (amplifier + 1);
            if(entityPostYaw > 180.0F){
                entityPostYaw -= 360.0F;
            }
            player.setYRot(entityPostYaw);
        }
        return super.applyEffectTick(level, entity, amplifier);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        super.onEffectAdded(entity, amplifier);
        if(entity instanceof Mob mob){
            mob.setTarget(null);
            mob.setLastHurtByMob(null);
            if(mob instanceof ZombifiedPiglin piggie){
                piggie.setTimeToRemainAngry(0);
            }
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }
}
