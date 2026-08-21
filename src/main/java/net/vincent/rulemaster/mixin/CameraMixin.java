package net.vincent.rulemaster.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.world.phys.Vec3;
import net.vincent.rulemaster.networking.client.CameraShakeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {

    @Shadow
    private Vec3 position;

    @Inject(method = "update", at = @At("TAIL"))
    private void onUpdate(DeltaTracker deltaTracker, CallbackInfo ci) {
        if (CameraShakeManager.isShaking()) {
            float partialTicks = deltaTracker.getGameTimeDeltaPartialTick(true);
            Vec3 shake = CameraShakeManager.getShakeOffset(partialTicks);
            this.position = this.position.add(shake);
        }
    }
}
