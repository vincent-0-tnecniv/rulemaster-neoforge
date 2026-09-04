package net.vincent.rulemaster.event;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.attachments.ModAttachments;
import net.vincent.rulemaster.block.ModBlocks;
import net.vincent.rulemaster.block.custom.FleshBlock;
import net.vincent.rulemaster.command.ModCommands;
import net.vincent.rulemaster.datagen.datapack.ModDamageTypes;
import net.vincent.rulemaster.item.custom.written_books.LivoGuideBookItem;
import net.vincent.rulemaster.networking.client.CameraShakeManager;

@EventBusSubscriber(modid = RuleMaster.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void registerModCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        ModCommands.registerCommands(dispatcher);
    }

    @SubscribeEvent
    public static void avoidPlacement(PlayerInteractEvent.RightClickBlock event) {
        if(event.getEntity().isCreative()){ return; }
        Block block = event.getLevel().getBlockState(event.getPos()).getBlock();
        if(block instanceof FleshBlock || block == ModBlocks.FLESH_SLAB.get()){
            event.getEntity().sendOverlayMessage(Component.translatable("block.rulemaster.flesh_block.place_block_failed"));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void updateBookContent(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        LivoGuideBookItem.update(player);
    }

    @SubscribeEvent
    public static void performCameraShake(ClientTickEvent.Pre event) {
        CameraShakeManager.tick();
    }

    @SubscribeEvent
    public static void takeVitalityOverDamage(LivingIncomingDamageEvent event) {
        if(!(event.getEntity() instanceof Player playerOnServer)) return;
        if(playerOnServer.level().isClientSide()) return;
        var server = playerOnServer.level().getServer();
        if(server == null) return;
        server.execute(() -> {
            if(playerShouldHaveDied(playerOnServer)) return;
            if(hasRunOutOfVitality(playerOnServer)) {
                // Player has run out of vitality - cannot cancel anymore!
                performOutOfVitalityKill(playerOnServer);
                return;
            }
            if(isPlayerUsingVitality(playerOnServer)) {
                event.setCanceled(false);
                // Player has activated the totem of reborn - vitality is used, no health should go below
                RuleMaster.LOGGER.info("The event has the amount: {}", event.getAmount());
                cancelDamageWithVitality(playerOnServer, event);
            }
        });
    }

//    @SubscribeEvent
//    public static void killPlayerWithoutVitality(PlayerTickEvent.Pre event) {
//        Player player = event.getEntity();
//        if(player.level().isClientSide()) return;
//        if(hasRunOutOfVitality(player)) {
//            performOutOfVitalityKill(player);
//        }
//    }

    private static void cancelDamageWithVitality(Player playerOnServer, LivingIncomingDamageEvent event) {
        playerOnServer.setData(ModAttachments.VITALITY, playerOnServer.getData(ModAttachments.VITALITY) - Math.round(event.getAmount() * 4));
        if(playerOnServer.getData(ModAttachments.VITALITY) <= 0) {
            playerOnServer.setData(ModAttachments.VITALITY, 0f);
            performOutOfVitalityKill(playerOnServer);
        }
        event.setAmount(0);
    }

    private static void performOutOfVitalityKill(Player playerOnServer) {
//        playerOnServer.setData(ModAttachments.IS_VITALIY_OVERRIDING_HEALTH, ModAttachments.IS_VITALIY_OVERRIDING_HEALTH_DEFAULT);
//        playerOnServer.setData(ModAttachments.VITALITY, ModAttachments.VITALITY_DEFAULT);
//        playerOnServer.setData(ModAttachments.MAXIMUM_VITALITY, ModAttachments.MAXIMUM_VITALITY_DEFAULT);
//        playerOnServer.setData(ModAttachments.VITALITY_REGEN, ModAttachments.VITALITY_REGEN_DEFAULT);
        DamageSource bleeding = playerOnServer.damageSources().source(ModDamageTypes.BLEEDING);
        playerOnServer.hurtServer((ServerLevel) (playerOnServer.level()), bleeding, Float.MAX_VALUE);
    }

    @SubscribeEvent
    public static void drawVitalityBar(RegisterGuiLayersEvent event) {
        event.registerAboveAll(Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "vitality_bar"), ((guiGraphics, deltaTracker) -> {

            int x = guiGraphics.guiWidth() / 2;
            int y = guiGraphics.guiHeight();

            Player player = Minecraft.getInstance().player;

            if (player == null || !player.getData(ModAttachments.IS_VITALIY_OVERRIDING_HEALTH)) return;

            float vt = player.getData(ModAttachments.VITALITY);
            float maxVt = player.getData(ModAttachments.MAXIMUM_VITALITY);
            float renderProgress = Math.max(vt / maxVt, 0.0f);

//            if (shouldRender(player)) {
//                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "vitality_bar_bg"),
//                        x - 95, y - 55, 0, 0, 100, 16, 1920, 512);
//
//                if (renderProgress != 0.0f) {
//                    guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "vitality_bar_fill"),
//                            x - 95, y - 55, 0, 0, (int) (100 * renderProgress), 16, 1920, 512);
//                }
//            }

            if (shouldRender(player)) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "vitality_bar_bg"),
                        x - 92, y - 44, 85, 16);

                if (renderProgress != 0.0f) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "vitality_bar_fill"),
                            x - 87, y - 44, (int) (78 * renderProgress), 16);
                }
            }

        }));
    }

    private static boolean shouldRender(Player player) {
        return !player.isCreative() && !player.isSpectator()
                && player.getData(ModAttachments.IS_VITALIY_OVERRIDING_HEALTH);
    }

    public static boolean playerShouldHaveDied(Player player) {
        return !player.getData(ModAttachments.IS_VITALIY_OVERRIDING_HEALTH);
    }

    public static boolean hasRunOutOfVitality(Player player) {
        return isPlayerUsingVitality(player) && player.getData(ModAttachments.VITALITY) <= 0;
    }

    private static boolean isPlayerUsingVitality(Player player) {
        return player.getData(ModAttachments.IS_VITALIY_OVERRIDING_HEALTH.get());
    }
}
