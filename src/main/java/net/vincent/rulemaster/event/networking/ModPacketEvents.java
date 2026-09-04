package net.vincent.rulemaster.event.networking;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.attachments.ModAttachments;
import net.vincent.rulemaster.event.ModEvents;
import net.vincent.rulemaster.networking.ClientPayloadHandler;
import net.vincent.rulemaster.networking.packet.CameraShakePacketS2C;
import net.vincent.rulemaster.networking.packet.VitalityActivationC2S;

@EventBusSubscriber(modid = RuleMaster.MOD_ID)
public class ModPacketEvents {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1")
                .executesOn(HandlerThread.MAIN);

        registrar.playToClient(CameraShakePacketS2C.TYPE, CameraShakePacketS2C.STREAM_CODEC, ClientPayloadHandler::handleCameraShakePacket);
        registrar.playToServer(VitalityActivationC2S.TYPE, VitalityActivationC2S.STREAM_CODEC, ClientPayloadHandler::handleVitalityActivationPacket);
    }

    @SubscribeEvent
    public static void handleVitalityRegen(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if(player.level().isClientSide()) return;
        if(ModEvents.hasRunOutOfVitality(player)) return; // disable vitality regen when out of
        if(player instanceof ServerPlayer serverPlayer) {
            serverPlayer.level().getServer().execute(() -> {
                var regenRate = player.getData(ModAttachments.VITALITY_REGEN);
                regenVitality(player, regenRate);
                if(player.getData(ModAttachments.VITALITY) % 10 == 0) {
                    player.playSound(SoundEvents.AMETHYST_BLOCK_BREAK, 2.0F, 1.0F);
                }
            });
        }

    }

    private static void regenVitality(Player player, float data) {
        if(((double) player.getData(ModAttachments.VITALITY)) == (float) (player.getData(ModAttachments.MAXIMUM_VITALITY))) return;
        // vitality is already maxed - no overflow allowed!
        player.setData(ModAttachments.VITALITY, player.getData(ModAttachments.VITALITY) + data * player.getData(ModAttachments.MAXIMUM_VITALITY));
        // Every tick, the player is healed with the following amount of vitality:
        // new vitality = old vitality + (vitality regen) * maximum vitality
        // The default value of vitality regen is 0.005f
        // Meaning 10% of the maximum vitality will be healed every second
    }
}
