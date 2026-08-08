package net.vincent.rulemaster.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.vincent.rulemaster.client.CameraShakeManager;
import net.vincent.rulemaster.data.ModDataComponents;
import net.vincent.rulemaster.datagen.datapack.damage.ModDamageTypes;
import net.vincent.rulemaster.effect.ModEffects;
import net.vincent.rulemaster.item.ModToolMaterials;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class BloodPiercerItem extends Item {
    public BloodPiercerItem(Properties properties) {
        super(properties.spear(ModToolMaterials.BLOOD_CRYSTAL,
                0.5F, 1.2F, 0.3F, 2.0F, 8.0F,
                5.0F, 5.1F, 8.0F, 4.6F)
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE));
    }

    public static float addPierceDamage = (ModToolMaterials.BLOOD_CRYSTAL.attackDamageBonus() + 1) * 0.5f;

    public static final int TICKS_PER_SECOND = 20;
    public static final int ABILITY_COOLDOWN = 20; // i.e. 20 ticks (1 second) cooldown
    public static boolean hasDiscoveredSecretAbility = false;

    public static boolean isNight;
    public static long dayTime;
    private static float dayNightMultiplier;

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        dayTime = level.getGameTime() % 24000;
        if (dayTime >= 0 && dayTime < 13000) {
            isNight = false;
        }
        if (dayTime >= 13000) {
            isNight = true;
        }
        dayNightMultiplier = isNight ? 1.15f : 0.85f;
        handleDynamicTextureChange(owner, itemStack);
        super.inventoryTick(itemStack, level, owner, slot);
    }

    @Override
    public @NonNull InteractionResult interactLivingEntity(ItemStack itemStack, Player user, LivingEntity entity, InteractionHand type) {
        if (user.hasEffect(ModEffects.STUN)) {return InteractionResult.FAIL;}

        boolean canUseAbility = !user.getCooldowns().isOnCooldown(itemStack);

        float targetMissingPercentageHealth = (entity.getMaxHealth() - entity.getHealth()) / entity.getMaxHealth();
        float attackerMissingPercentageHealth = (user.getMaxHealth() - user.getHealth()) / user.getMaxHealth();

        float adjustedPierceDamage = addPierceDamage * (1 + targetMissingPercentageHealth + attackerMissingPercentageHealth) * dayNightMultiplier;

        if (!entity.level().isClientSide()) {
            if (canUseAbility) {
                useRightClickAbility((ServerLevel) user.level(), user, entity, adjustedPierceDamage);
                user.getCooldowns().addCooldown(itemStack, ABILITY_COOLDOWN);
            }
        }
        if (entity.level().isClientSide()) {
            if (canUseAbility) {
                playNormalAttackSound(entity, user);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.hasEffect(ModEffects.STUN)) {
            super.hurtEnemy(stack, target, attacker);
            return;
        }

        float targetMissingPercentageHealth = (target.getMaxHealth() - target.getHealth()) / target.getMaxHealth();
        float attackerMissingPercentageHealth = (attacker.getMaxHealth() - attacker.getHealth()) / attacker.getMaxHealth();

        float adjustedPierceDamage = addPierceDamage * (1 + targetMissingPercentageHealth + attackerMissingPercentageHealth) * dayNightMultiplier;

        boolean targetDied = false;

        if (!target.level().isClientSide()) {
            ServerLevel level = (ServerLevel) attacker.level();
            if (target.getName().getString().toLowerCase().contains("hazel")) {
                targetDied = true;
                adjustedPierceDamage = Float.MAX_VALUE;
                attacker.heal(Float.MAX_VALUE);
                if(attacker instanceof ServerPlayer player){
                    CameraShakeManager.sendCameraShake(player, 5f, 3f, 5f, 20);
                }
                hasDiscoveredSecretAbility = true;
            }
            DamageSource bleeding = target.damageSources().source(ModDamageTypes.BLEEDING, attacker);
            float shouldRemainHealth = target.getHealth() - adjustedPierceDamage;
            boolean shouldDie = shouldRemainHealth <= 0;
            boolean belowOneFifths = shouldRemainHealth / target.getMaxHealth() <= 0.2f;
            if (shouldDie && target.isAlive()) {
                target.hurtServer(level, bleeding, Float.MAX_VALUE);
                targetDied = true;

            } else if (belowOneFifths) {
                target.hurtServer(level, bleeding, Float.MAX_VALUE);
                targetDied = true;
            } else if (target.isAlive()) {
                target.hurtServer(level, bleeding, adjustedPierceDamage);
            }
        }
        if (!attacker.level().isClientSide()) {
            playOverHitSound(target, attacker);
            handlePostAttackChanges(attacker, target, targetDied);
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.accept(Component.literal("§6Ability: Heart Strikethrough§r §e§lHIT§r"));
        tooltip.accept(Component.literal("§750% of this weapon's base damage is dealt to the target as armor-piercing damage.§r"));
        tooltip.accept(Component.literal("§7Grants Weakness 5 to the target for 5 seconds on melee hits.§r"));
        tooltip.accept(Component.literal("§7Deals a §4fatal strike§7 to the target when it is below 20% health.§r"));
        tooltip.accept(Component.literal("§7Bonus damage: §c" + Math.round(addPierceDamage * 10) / 10f + "§r"));
        tooltip.accept(Component.literal(""));
        tooltip.accept(Component.literal("§6Ability: Double-edged Strike!§r §e§lRIGHT CLICK§r"));
        tooltip.accept(Component.literal("§7Deals §c1.75x§7 damage to the target at the cost of 1§c♥§7.§r"));
        tooltip.accept(Component.literal("§7When under or at 50% health, deals §c2x§7 damage to the target at the cost of 2§c♥§7.§r"));
        tooltip.accept(Component.literal("§7When under or at 20% health, deals a §4fatal strike§7 to both the target AND yourself!§r"));
        tooltip.accept(Component.literal("§7Cooldown: §a" + Math.round((float) ABILITY_COOLDOWN / TICKS_PER_SECOND) + "s§r"));
        tooltip.accept(Component.literal(""));
        tooltip.accept(Component.literal("§6Ability: Bloody Assassin's Night Strike§r"));
        tooltip.accept(Component.literal("§7Increase damage dealt by §c15%§7 when at night, decreasing otherwise.§r"));
        tooltip.accept(Component.literal("§7Currently is: " + (isNight ? "§4Nighttime§7" : "§eDaytime§r")));
        tooltip.accept(Component.literal("§7Grants Night Vision, Speed II and Invisibility for §a60§7 seconds on kill.§r"));
        tooltip.accept(Component.literal("§7Also steals their heart and heal for 0.5§c♥§7.§r"));
        tooltip.accept(Component.literal(""));
        tooltip.accept(Component.literal("§6Ability: Dangerous Dagger§r"));
        tooltip.accept(Component.literal("§7Every §a1%§7 of the attacker's missing health and §a1%§7 of the target's missing health deals §a+1%§7 damage.§r"));
        tooltip.accept(Component.literal(""));
        if (hasDiscoveredSecretAbility) {
            tooltip.accept(Component.literal("§6Secret Ability: The Bloody Past§r §e§lHIT, RIGHT CLICK§r"));
            tooltip.accept(Component.literal("§7Hitting any target with Hazel in any parts of its name kills it immediately!!§r"));
            tooltip.accept(Component.literal("§7Your rage is calmed upon taming from slaying a Hazel, healing you back to full health.§r"));
            tooltip.accept(Component.literal(""));
        }
        tooltip.accept(Component.literal("§6§lLEGENDARY LANCER WEAPON§r"));
        super.appendHoverText(itemStack, context, display, tooltip, tooltipFlag);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    private void handlePostAttackChanges(LivingEntity attacker, LivingEntity target, boolean targetDied) {
        if (target.isAlive() && !targetDied) {
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,
                    100, 4, false, false));
            attacker.level().playSound(null, attacker.blockPosition(), SoundEvents.AXE_STRIP,
                    SoundSource.MASTER, 1.0f, 1.0f);
        } else if (!target.isAlive() || targetDied) {
            attacker.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,
                    1200, 0, false, false));
            float rollingNumber = -0.5f * ((attacker.getMaxHealth() * 2f - attacker.getHealth()) / attacker.getMaxHealth()) + 1.25f;
            boolean isVisible = Math.random() >= rollingNumber;
            attacker.addEffect(new MobEffectInstance(ModEffects.STEALTH,
                    1200, 0, false, isVisible));
            attacker.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,
                    1200, 0, false, false));
            attacker.heal(1);
            attacker.level().playSound(null, attacker.blockPosition(), SoundEvents.WOLF_ARMOR_DAMAGE,
                    SoundSource.MASTER, 1.0f, 1.0f);
            attacker.setArrowCount(0);
        }
        if(attacker instanceof Player player) {
            player.getFoodData().addExhaustion(2.0f);
        }
    }

    private void playOverHitSound(LivingEntity target, LivingEntity attacker) {
        if (target.getName().getString().toLowerCase().contains("hazel")) {
            attacker.level().playSound(null, attacker.blockPosition(), SoundEvents.WARDEN_SONIC_CHARGE,
                    SoundSource.MASTER, 1.0f, 1.0f);
            attacker.level().playSound(null, attacker.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM,
                    SoundSource.MASTER, 1.0f, 1.0f);
        }
    }

    private void playNormalAttackSound(Entity entity, Player user) {
        if (entity.getName().getString().toLowerCase().contains("hazel")) {
            hasDiscoveredSecretAbility = true;
            user.playSound(SoundEvents.WARDEN_SONIC_CHARGE, 1, 1);
            user.playSound(SoundEvents.WARDEN_SONIC_BOOM, 1, 1);
        } else if (user.getHealth() / user.getMaxHealth() <= 0.2) {
            user.playSound(SoundEvents.SPEAR_HIT.value(), 1, 1);
            user.playSound(SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, 1, 1);
        } else if (user.getHealth() / user.getMaxHealth() <= 0.5) {
            user.playSound(SoundEvents.SPEAR_HIT.value(), 1, 1);
            user.playSound(SoundEvents.WOLF_ARMOR_DAMAGE, 1, 1);
        } else {
            user.playSound(SoundEvents.SPEAR_HIT.value(), 1, 1);
            user.playSound(SoundEvents.AXE_STRIP, 1, 1);
        }
    }

    private void handleDynamicTextureChange(Entity owner, ItemStack swordItemStack) {
        if(owner instanceof Player player){
            swordItemStack.remove(ModDataComponents.HALF);
            swordItemStack.remove(ModDataComponents.LOW);
            if(player.getHealth() / player.getMaxHealth() <= 0.2){
                swordItemStack.set(ModDataComponents.LOW, true);
            } else if(player.getHealth() / player.getMaxHealth() <= 0.5){
                swordItemStack.set(ModDataComponents.HALF, true);
            }
        }
    }

    private void useRightClickAbility(ServerLevel level, Player user, LivingEntity entity, float adjustedPierceDamage) {
        DamageSource bleeding = entity.damageSources().source(ModDamageTypes.BLEEDING, user);
        if (entity.getName().getString().toLowerCase().contains("hazel")) {
            user.heal(Float.MAX_VALUE);
            entity.hurtServer(level, bleeding, Float.MAX_VALUE);
        } else if (user.getHealth() / user.getMaxHealth() <= 0.2) {
            entity.hurtServer(level, bleeding, Float.MAX_VALUE);
            user.hurtServer(level, bleeding, Float.MAX_VALUE);
        } else if (user.getHealth() / user.getMaxHealth() <= 0.5) {
            float damageMultiplier = 2f;
            float currentHealth = entity.getHealth();
            float remHealth = currentHealth - adjustedPierceDamage * damageMultiplier;
            boolean canSurvive = remHealth > 0;
            user.hurtServer(level, entity.damageSources().starve(), 4);
            // starvation does not harm creative mode players and bypasses resistance and armor in survival
            // i.e. survival-only armor-piercing damage
            if (canSurvive) {
                entity.hurtServer(level, bleeding, adjustedPierceDamage * damageMultiplier);

            } else {
                entity.hurtServer(level, bleeding, Float.MAX_VALUE);
            }
        } else {
            float damageMultiplier = 1.75f;
            float currentHealth = entity.getHealth();
            float remHealth = currentHealth - adjustedPierceDamage * adjustedPierceDamage;
            boolean canSurvive = remHealth > 0;
            user.hurtServer(level, bleeding, 2);
            // starvation does not harm creative mode players and bypasses resistance and armor in survival
            // i.e. survival-only armor-piercing damage
            if (canSurvive) {
                entity.hurtServer(level, bleeding, adjustedPierceDamage * damageMultiplier);
                entity.setHealth(remHealth);

            } else if (entity.isAlive()) {
                entity.hurtServer(level, bleeding, Float.MAX_VALUE);
            }
        }
    }
}
