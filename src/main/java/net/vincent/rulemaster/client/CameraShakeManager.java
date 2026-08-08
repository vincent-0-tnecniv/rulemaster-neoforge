package net.vincent.rulemaster.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class CameraShakeManager {
    private static float shakeIntensityX = 0.0f;
    private static float shakeIntensityY = 0.0f;
    private static float shakeIntensityZ = 0.0f;
    private static int shakeDuration = 0;
    private static int shakeTimer = 0;
    private static float intensity = 0.0f;

    public static void triggerShake(float intensityX, float intensityY, float intensityZ, int duration) {
        shakeIntensityX = intensityX;
        shakeIntensityY = intensityY;
        shakeIntensityZ = intensityZ;
        shakeDuration = duration;
        shakeTimer = duration;
        intensity = (intensityX + intensityY + intensityZ) / 3.0f;
    }

    public static float getIntensity() {
        if (shakeTimer <= 0) return 0.0f;
        float progress = 1.0f - (float) shakeTimer / shakeDuration;
        return intensity * (1.0f - progress);
    }

    public static Vec3 getShakeOffset(float partialTicks) {
        if (shakeTimer <= 0) {
            return Vec3.ZERO;
        }

        float progress = 1.0f - (float) shakeTimer / shakeDuration;
        float decayFactor = 1.0f - progress;

        float currentIntensityX = shakeIntensityX * decayFactor;
        float currentIntensityY = shakeIntensityY * decayFactor;
        float currentIntensityZ = shakeIntensityZ * decayFactor;

        double time = Minecraft.getInstance().level.getGameTime() + partialTicks;
        double randomX = Math.sin(time * 37.7 + 1.3) * 0.7 + Math.cos(time * 53.3 + 0.7) * 0.3;
        double randomY = Math.sin(time * 41.1 + 2.1) * 0.7 + Math.cos(time * 47.9 + 1.2) * 0.3;
        double randomZ = Math.sin(time * 43.3 + 3.5) * 0.7 + Math.cos(time * 59.7 + 2.8) * 0.3;

        return new Vec3(
                randomX * currentIntensityX * 0.3,
                randomY * currentIntensityY * 0.3,
                randomZ * currentIntensityZ * 0.3
        );
    }

    public static void tick() {
        if (shakeTimer > 0) {
            shakeTimer--;
        }
    }

    public static boolean isShaking() {
        return shakeTimer > 0 && intensity > 0.0f;
    }

    public static void sendCameraShake(ServerPlayer player, float intensityX, float intensityY, float intensityZ, int duration) {
        CameraShakePayload payload = new CameraShakePayload(intensityX, intensityY, intensityZ, duration);
        ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(payload);
        player.connection.send(packet);
    }
}
