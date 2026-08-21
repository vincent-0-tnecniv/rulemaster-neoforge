package net.vincent.rulemaster.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.vincent.rulemaster.attachments.ModAttachments;
import net.vincent.rulemaster.datagen.datapack.ModDamageTypes;
import net.vincent.rulemaster.tags.ModTags;

import java.util.List;

public class BloodCrystalBlock extends Block {
    public BloodCrystalBlock(Properties properties) {
        super(properties.strength(1.5f).requiresCorrectToolForDrops().randomTicks());
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(random.nextInt(4200) == 67){
            // around one in 7 minutes
            AABB boundingBox = new AABB(pos).inflate(4);
            List<Entity> entities = level.getEntities(null, boundingBox);
            for(Entity entity : entities){
                if(!entity.is(ModTags.EntityTypes.LIVING_HUMANOID)) {return;}
                if(!(entity instanceof LivingEntity livingEntity)) {return;}
                DamageSource bleeding = livingEntity.damageSources().source(ModDamageTypes.BLEEDING);
                int newMarkOfCrystalValue = entity.getData(ModAttachments.MARK_OF_CRYSTAL) + 1;
                livingEntity.setData(ModAttachments.MARK_OF_CRYSTAL, newMarkOfCrystalValue);
                livingEntity.handleDamageEvent(bleeding);
                livingEntity.playSound(SoundEvents.TRIAL_SPAWNER_OMINOUS_ACTIVATE, 1.0f, 2f / 3f * newMarkOfCrystalValue);
                if(newMarkOfCrystalValue == 3){
                    if(livingEntity instanceof Player player){
                        player.sendSystemMessage(Component.literal("You feel some of your power drained..."));
                    }
                    livingEntity.hurtServer(level, bleeding, 6);
                    livingEntity.setData(ModAttachments.MARK_OF_CRYSTAL, 0);
                }
            }
        }
        super.randomTick(state, level, pos, random);
    }
}
