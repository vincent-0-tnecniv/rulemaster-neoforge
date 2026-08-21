package net.vincent.rulemaster;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = RuleMaster.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = RuleMaster.MOD_ID, value = Dist.CLIENT)
public class RuleMasterClient {
    public RuleMasterClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

//    @SubscribeEvent
//    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
//        final PayloadRegistrar registrar = event.registrar(RuleMaster.MOD_ID)
//                .versioned("1.0.0")
//                .optional();
//
//        // Register clientbound payload (server -> client)
//        registrar.playToClient(
//                CameraShakePayload.TYPE,
//                CameraShakePayload.STREAM_CODEC,
//                (payload, context) -> {
//                    // This runs on the client thread
//                    context.enqueueWork(() -> {
//                        // Handle the camera shake on client
//                        CameraShakeManager.sendCameraShake(
//                                (LocalPlayer) context.player(),
//                                payload.intensityX(),
//                                payload.intensityY(),
//                                payload.intensityZ(),
//                                payload.duration()
//                        );
//                    });
//                }
//        );
//    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
//        EntityRenderers.register(ModEntityTypes.EYE_OF_BIRTH.get(), EyeOfBirthRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
//        event.registerLayerDefinition(ModModelLayerLocations.EYE_OF_BIRTH, EyeOfBirthModel::createBodyLayer);
    }
}
