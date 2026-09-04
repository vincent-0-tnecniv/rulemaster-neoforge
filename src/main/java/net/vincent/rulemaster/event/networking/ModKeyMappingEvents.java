package net.vincent.rulemaster.event.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.attachments.ModAttachments;
import net.vincent.rulemaster.item.ModItems;
import net.vincent.rulemaster.item.custom.vitality_related.TotemOfRebornItem;
import net.vincent.rulemaster.keymapping.ModKeyMappings;
import net.vincent.rulemaster.networking.packet.VitalityActivationC2S;
import net.vincent.rulemaster.networking.packet.VitalityHealthToggleC2S;
import net.vincent.rulemaster.tags.ModTags;

@EventBusSubscriber(modid = RuleMaster.MOD_ID)
public class ModKeyMappingEvents {
    @SubscribeEvent
    public static void registerKeyClicks(ClientTickEvent.Post event) {
        // CLIENT SIDE
        if(ModKeyMappings.PRESS_TOTEM_ACTIVATION.get().consumeClick()) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if(localPlayer == null) return;
            if(!localPlayer.getMainHandItem().is(ModTags.Items.VITALITY_TOGGLE)) return;
            activateVitality(localPlayer.getMainHandItem(), localPlayer);
        }
        if(ModKeyMappings.PRESS_VITALITY_SWAP.get().consumeClick()) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if(localPlayer == null) return;
            toggleVitality(localPlayer);
        }
    }

    private static void toggleVitality(LocalPlayer localPlayer) {
        ClientPacketDistributor.sendToServer(new VitalityHealthToggleC2S(localPlayer.getData(ModAttachments.IS_VITALIY_OVERRIDING_HEALTH)));
    }

    private static void activateVitality(ItemStack totem, LocalPlayer player) {
        // CLIENT SIDE
        if(totem.is(ModItems.TOTEM_OF_REBORN)){
            float startingVitality = player.hasData(ModAttachments.VITALITY) ? player.getData(ModAttachments.VITALITY) : TotemOfRebornItem.STARTING_VITALITY;
            ClientPacketDistributor.sendToServer(new VitalityActivationC2S(startingVitality, TotemOfRebornItem.MAXIMUM_VITALITY, TotemOfRebornItem.VITALITY_REGEN));
            // activate vitality on the server side
        }
        // do nothing if it is not held
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        ModKeyMappings.register(event);
    }
}