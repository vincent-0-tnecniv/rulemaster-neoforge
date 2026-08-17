package net.vincent.rulemaster.item.custom;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.Vec3;
import net.vincent.rulemaster.entity.LocatorEye;

public class LocatorEyeItem extends Item {

    private final TagKey<Structure> findingTag;

    public LocatorEyeItem(Properties properties, TagKey<Structure> structureTags) {
        this.findingTag = structureTags;
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(findingTag == null) {
            player.sendOverlayMessage(Component.translatable("item.rulemaster.locator_eye.no_tag"));
            return InteractionResult.FAIL;
        }
        ItemStack itemStack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if (level instanceof ServerLevel serverLevel) {
            BlockPos nearestMapFeature = serverLevel.findNearestMapStructure(findingTag, player.blockPosition(), 100, false);
            if (nearestMapFeature == null) {
                player.sendOverlayMessage(Component.translatable("item.rulemaster.locator_eye.structure_not_found"));
                return InteractionResult.FAIL;
            }

            if(!player.isCreative()) {
                player.getCooldowns().addCooldown(itemStack, 60);
            }
            LocatorEye locatorEye = new LocatorEye(level, player.getX(), player.getY(0.5F), player.getZ(), this::asItem);
            locatorEye.setItem(itemStack);
            locatorEye.signalTo(Vec3.atLowerCornerOf(nearestMapFeature));
            level.gameEvent(GameEvent.PROJECTILE_SHOOT, locatorEye.position(), GameEvent.Context.of(player));
            level.addFreshEntity(locatorEye);
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.USED_ENDER_EYE.trigger(serverPlayer, nearestMapFeature);
            }

            float pitch = Mth.lerp(level.getRandom().nextFloat(), 0.33F, 0.5F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 1.0F, pitch);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        return InteractionResult.SUCCESS_SERVER;
    }
}
