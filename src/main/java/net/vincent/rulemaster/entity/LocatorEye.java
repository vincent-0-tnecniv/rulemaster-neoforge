package net.vincent.rulemaster.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class LocatorEye extends Entity {

    private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
    private static final float TOO_FAR_SIGNAL_HEIGHT = 8.0F;
    private static final float TOO_FAR_DISTANCE = 12.0F;
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK;
    private @Nullable Vec3 target;
    private int life;
    private boolean surviveAfterDeath;
    private final Item defaultItem;

    public LocatorEye(EntityType<? extends EyeOfEnder> type, Level level, Item defaultItem) {
        this.surviveAfterDeath = false;
        this.defaultItem = defaultItem;
        super(type, level);
    }

    public LocatorEye(Level level, double x, double y, double z, Item defaultItem) {
        this(EntityTypes.EYE_OF_ENDER, level, defaultItem);
        this.setPos(x, y, z);
    }

    public void setItem(ItemStack source) {
        if (source.isEmpty()) {
            this.getEntityData().set(DATA_ITEM_STACK, this.getDefaultItem());
        } else {
            this.getEntityData().set(DATA_ITEM_STACK, source.copyWithCount(1));
        }

    }

    public ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        entityData.define(DATA_ITEM_STACK, this.getDefaultItem());
    }

    public boolean shouldRenderAtSqrDistance(double distance) {
        if (this.tickCount < 2 && distance < (double)12.25F) {
            return false;
        } else {
            double size = this.getBoundingBox().getSize() * (double)4.0F;
            if (Double.isNaN(size)) {
                size = 4.0F;
            }

            size *= 64.0F;
            return distance < size * size;
        }
    }

    public void signalTo(Vec3 target) {
        Vec3 delta = target.subtract(this.position());
        double horizontalDistance = delta.horizontalDistance();
        if (horizontalDistance > (double)12.0F) {
            this.target = this.position().add(delta.x / horizontalDistance * (double)12.0F, (double)8.0F, delta.z / horizontalDistance * (double)12.0F);
        } else {
            this.target = target;
        }

        this.life = 0;
    }

    public void tick() {
        super.tick();
        Vec3 newPosition = this.position().add(this.getDeltaMovement());
        if (!this.level().isClientSide() && this.target != null) {
            this.setDeltaMovement(updateDeltaMovement(this.getDeltaMovement(), newPosition, this.target));
        }

        if (this.level().isClientSide()) {
            Vec3 particleOrigin = newPosition.subtract(this.getDeltaMovement().scale((double)0.25F));
            this.spawnParticles(particleOrigin, this.getDeltaMovement());
        }

        this.setPos(newPosition);
        if (!this.level().isClientSide()) {
            ++this.life;
            if (this.life > 80 && !this.level().isClientSide()) {
                this.playSound(SoundEvents.ENDER_EYE_DEATH, 1.0F, 1.0F);
                this.discard();
                this.level().levelEvent(2003, this.blockPosition(), 0);
            }
        }

    }

    private void spawnParticles(Vec3 origin, Vec3 movement) {
//        if (this.isInWater()) {
//            for(int i = 0; i < 4; ++i) {
//                this.level().addParticle(ParticleTypes.BUBBLE, origin.x, origin.y, origin.z, movement.x, movement.y, movement.z);
//            }
//        }
    }

    private static Vec3 updateDeltaMovement(Vec3 oldMovement, Vec3 position, Vec3 target) {
        Vec3 horizontalDelta = new Vec3(target.x - position.x, (double)0.0F, target.z - position.z);
        double horizontalLength = horizontalDelta.length();
        double wantedSpeed = Mth.lerp(0.0025, oldMovement.horizontalDistance(), horizontalLength);
        double movementY = oldMovement.y;
        if (horizontalLength < (double)1.0F) {
            wantedSpeed *= 0.8;
            movementY *= 0.8;
        }

        double wantedMovementY = position.y - oldMovement.y < target.y ? (double)1.0F : (double)-1.0F;
        return horizontalDelta.scale(wantedSpeed / horizontalLength).add((double)0.0F, movementY + (wantedMovementY - movementY) * 0.015, (double)0.0F);
    }

    protected void addAdditionalSaveData(ValueOutput output) {
        output.store("Item", ItemStack.CODEC, this.getItem());
    }

    protected void readAdditionalSaveData(ValueInput input) {
        this.setItem((ItemStack)input.read("Item", ItemStack.CODEC).orElse(this.getDefaultItem()));
    }

    private ItemStack getDefaultItem() {
        return new ItemStack(defaultItem);
    }

    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    public boolean isAttackable() {
        return false;
    }

    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    static {
        DATA_ITEM_STACK = SynchedEntityData.defineId(LocatorEye.class, EntityDataSerializers.ITEM_STACK);
    }

    public boolean getSurviveAfterDeath() {
        return this.surviveAfterDeath;
    }

    public void setSurviveAfterDeath(boolean surviveAfterDeath) {
        this.surviveAfterDeath = surviveAfterDeath;
    }
}
