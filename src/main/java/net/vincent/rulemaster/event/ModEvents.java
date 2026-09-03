package net.vincent.rulemaster.event;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
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
    public static void handleVitalitySystem(LivingIncomingDamageEvent event) {
        if(!(event.getEntity() instanceof Player playerOnServer)) return;
        if(playerOnServer.level().isClientSide()) return;
        if(hasRunOutOfVitality(playerOnServer)) {
            // Player has run out of vitality - cannot cancel anymore!
            performOutOfVitalityKill(playerOnServer);
            return;
        }
        if(isPlayerUsingVitality(playerOnServer)) {
            // Player has activated the totem of reborn - vitality is used, no health should go below
            RuleMaster.LOGGER.info("The event has the amount: {}", event.getAmount());
            cancelDamageWithVitality(playerOnServer, event);
        }
    }

    private static void cancelDamageWithVitality(Player playerOnServer, LivingIncomingDamageEvent event) {
        playerOnServer.setData(ModAttachments.VITALITY, playerOnServer.getData(ModAttachments.VITALITY) - Math.round(event.getAmount() * 4));
        event.setAmount(0);
    }

    private static void performOutOfVitalityKill(Player playerOnServer) {
        DamageSource bleeding = playerOnServer.damageSources().source(ModDamageTypes.BLEEDING);
        playerOnServer.hurtServer((ServerLevel) (playerOnServer.level()), bleeding, Float.MAX_VALUE);
    }

    @SubscribeEvent
    public static void killPlayersOutOfVitality(LivingIncomingDamageEvent event) {
        if(event.getEntity() instanceof Player playerOnServer) {
            if(playerOnServer.level().isClientSide()) return;

        }
    }

    private static boolean hasRunOutOfVitality(Player player) {
        return isPlayerUsingVitality(player) && player.getData(ModAttachments.VITALITY) <= 0;
    }

    private static boolean isPlayerUsingVitality(Player player) {
        return player.getData(ModAttachments.IS_VITALIY_OVERRIDING_HEALTH.get());
    }
}
