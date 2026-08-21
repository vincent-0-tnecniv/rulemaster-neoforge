package net.vincent.rulemaster.networking.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.vincent.rulemaster.RuleMaster;

import java.util.Random;

public class CameraShakeManager {
    private static float shakeIntensityX = 0.0f;
    private static float shakeIntensityY = 0.0f;
    private static float shakeIntensityZ = 0.0f;
    private static int shakeDuration = 0;
    private static int shakeTimer = 0;
    private static float intensity = 0.0f;

    public static void triggerShake(float intensityX, float intensityY, float intensityZ, int duration) {
        shakeIntensityX = intensityX;
        shakeIntensityX = intensityY;
        shakeIntensityX = intensityZ;
        shakeDuration = duration;
        shakeTimer = duration;
        intensity = (intensityX + intensityY + intensityZ) / 3.0f;
        RuleMaster.LOGGER.info("Camera shake triggered! X: " + intensityX + ", Y: " + intensityY + ", Z: " + intensityZ + ", Duration: " + duration);
    }

    public static void tick() {
        if (shakeTimer > 0) {
            shakeTimer--;
        }
    }

    public static Vec3 getShakeOffset(float partialTicks) {

        Level level = Minecraft.getInstance().level;
        if(level == null) return Vec3.ZERO;

        if (shakeTimer <= 0) {
            return Vec3.ZERO;
        }

        float progress = 1.0f - (float) shakeTimer / shakeDuration;
        float decayFactor = 1.0f - progress;

        float currentIntensityX = shakeIntensityX * decayFactor;
        float currentIntensityY = shakeIntensityY * decayFactor;
        float currentIntensityZ = shakeIntensityZ * decayFactor;

        double time = level.getGameTime() + partialTicks;
        Random rand = new Random();
        double randomX = rand.nextDouble();
        double randomY = rand.nextDouble();
        double randomZ = rand.nextDouble();

        return new Vec3(
                randomX * currentIntensityX * 0.3,
                randomY * currentIntensityY * 0.3,
                randomZ * currentIntensityZ * 0.3
        );
    }

    public static boolean isShaking() {
        return shakeTimer > 0 && intensity > 0.0f;
    }
}