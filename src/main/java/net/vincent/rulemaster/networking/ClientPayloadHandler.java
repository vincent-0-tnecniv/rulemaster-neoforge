package net.vincent.rulemaster.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.attachments.ModAttachments;
import net.vincent.rulemaster.networking.client.CameraShakeManager;
import net.vincent.rulemaster.networking.packet.CameraShakePacketS2C;
import net.vincent.rulemaster.networking.packet.VitalityActivationC2S;

// SERVER PACKETS TO CLIENT
public class ClientPayloadHandler {
    // ON SIDE: CLIENT
    public static void handleCameraShakePacket(CameraShakePacketS2C S2Cpacket, IPayloadContext context) {
        // This should do the camera shaking
        RuleMaster.LOGGER.info("Received a CameraShakePacketS2C!");
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if(mc.player != null) {
                RuleMaster.LOGGER.info("Handling the CameraShakePacketS2C!");
                CameraShakeManager.triggerShake(S2Cpacket.intensityX(), S2Cpacket.intensityY(), S2Cpacket.intensityZ(), S2Cpacket.duration());
            }
        });
    }

    public static void handleVitalityActivationPacket(VitalityActivationC2S C2Spacket, IPayloadContext context) {
        RuleMaster.LOGGER.info("Received a VitalityActivationC2S!");
        // Although impossible, may cause crash if not returning,
        // and the player is server side for some reason

        var player = context.player();

        if(!player.isCreative()) {
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        }

        player.level().playSound(null, player.blockPosition(), SoundEvents.TRIAL_SPAWNER_OMINOUS_ACTIVATE, SoundSource.PLAYERS, 1.0f, 1.0f);
        player.setData(ModAttachments.IS_VITALIY_OVERRIDING_HEALTH, true);
        player.setData(ModAttachments.VITALITY.get(), C2Spacket.startingVitality());
        player.setData(ModAttachments.MAXIMUM_VITALITY.get(), C2Spacket.maximumVitality());
        player.setData(ModAttachments.VITALITY_REGEN.get(), C2Spacket.vitalityRegen());
    }
}
