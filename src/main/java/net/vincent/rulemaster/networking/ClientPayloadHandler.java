package net.vincent.rulemaster.networking;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.networking.client.CameraShakeManager;
import net.vincent.rulemaster.networking.packet.CameraShakePacketS2C;

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
}
