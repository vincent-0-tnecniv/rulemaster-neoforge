package net.vincent.rulemaster.event;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.MobSplitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.attachments.ModAttachments;
import net.vincent.rulemaster.tags.ModTags;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@EventBusSubscriber(modid = RuleMaster.MOD_ID)
public class ModAttachmentEvents {
    @SubscribeEvent
    public static void setPlayerAttachments(PlayerEvent.PlayerLoggedInEvent event) {
        RuleMaster.LOGGER.info("Player attachments are being set!");
        Player player = event.getEntity();
        register(ModAttachments.MARK_OF_CRYSTAL, 0, player);
        if(!player.hasData(ModAttachments.IS_LUNAR)) {
            randomAssign(ModAttachments.IS_LUNAR, player);
            if(isLunar(player)){
                player.sendSystemMessage(Component.translatable("message.rulemaster.assign_player_type.lunar"));
                player.sendSystemMessage(Component.translatable("message.rulemaster.assign_player_type.warn"));
            } else{
                player.sendSystemMessage(Component.translatable("message.rulemaster.assign_player_type.solar"));
                player.sendSystemMessage(Component.translatable("message.rulemaster.assign_player_type.warn"));
            }
        }
        register(ModAttachments.PLAYER_JOINED, true, player);
//        if(!player.getData(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END) && player instanceof ServerPlayer serverPlayer) {
//            serverPlayer.level().getServer().execute(() -> {
//                // The ServerPlayer is somehow coming BEFORE the server?
//                // Anyway, the SERVER can always handle this well
//                BlockPos pos = player.getData(ModAttachments.PLAYER_END_SPAWN_POS);
//                serverPlayer.setPos(pos.getX(), pos.getY(), pos.getZ());
//                ServerLevel endServerLevel = serverPlayer.level().getServer().getLevel(Level.END);
//                if(endServerLevel == null){
//                    throw new IllegalStateException("There is no end somehow???");
//                }
//                serverPlayer.setServerLevel(endServerLevel);
//                serverPlayer.setData(ModAttachments.PLAYER_END_SPAWN_POS, BlockPos.ZERO);
//                serverPlayer.setData(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END, true);
//            });
//        }
        register(ModAttachments.IS_VITALIY_OVERRIDING_HEALTH, false, player);
        register(ModAttachments.VITALITY,100f, player);
        register(ModAttachments.MAXIMUM_VITALITY,100, player);
        register(ModAttachments.VITALITY_REGEN,1.0f, player);
    }

    private static boolean isLunar(Player player) {
        return player.getData(ModAttachments.IS_LUNAR);
    }

    @SubscribeEvent
    public static void setClonedPlayerAttachments(PlayerEvent.Clone event) {
        RuleMaster.LOGGER.info("Player attachments are being cloned!");
        Player player = event.getEntity();
        Player original = event.getOriginal();
        passOnFromParent(ModAttachments.MARK_OF_CRYSTAL, original, player);
        passOn(ModAttachments.IS_LUNAR, player);
        passOn(ModAttachments.PLAYER_JOINED, player);
        passOnFromParent(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END, original, player);
        passOnFromParent(ModAttachments.PLAYER_END_SPAWN_POS, original, player);
    }

    @SubscribeEvent
    public static void setChangedDimensionPlayerAttachments(PlayerEvent.PlayerChangedDimensionEvent event) {
        RuleMaster.LOGGER.info("Player attachments are being set from dimension change!");
        Player player = event.getEntity();
        passOn(ModAttachments.MARK_OF_CRYSTAL, player);
        passOn(ModAttachments.IS_LUNAR, player);
        passOn(ModAttachments.PLAYER_JOINED, player);
    }

    @SubscribeEvent
    public static void setRespawnedPlayerAttachments(PlayerEvent.PlayerRespawnEvent event) {
        RuleMaster.LOGGER.info("Player attachments are being from respawn!");
        Player player = event.getEntity();
        passOn(ModAttachments.MARK_OF_CRYSTAL, player);
        passOn(ModAttachments.IS_LUNAR, player);
        passOn(ModAttachments.PLAYER_JOINED, player);
        passOn(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END, player);
        passOn(ModAttachments.PLAYER_END_SPAWN_POS, player);
        if (player instanceof ServerPlayer serverPlayer) {
            ServerLevel playerCurrentLevel = serverPlayer.level();
            boolean shouldRespawnInEnd = serverPlayer.getData(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END);
            BlockPos respawnPos = serverPlayer.getData(ModAttachments.PLAYER_END_SPAWN_POS);
            if (shouldRespawnInEnd) {
                boolean shouldRefundGlowstone = false;
                if(playerCurrentLevel.dimension() == Level.NETHER){
                    // Player would have used up a glowstone to respawn in the nether to tp to the end - refund a glowstone
                    shouldRefundGlowstone = true;
                    player.sendSystemMessage(Component.translatable("message.rulemaster.nether_respawn_using_glowstone_refunded_0"));
                    player.sendSystemMessage(Component.translatable("message.rulemaster.nether_respawn_using_glowstone_refunded_1"));
                }
                ServerLevel endLevel = serverPlayer.level().getServer().getLevel(Level.END);
                if (endLevel != null) {
                    serverPlayer.teleportTo(
                            endLevel,
                            respawnPos.getX() + 0.5,
                            respawnPos.getY() + 0.5,
                            respawnPos.getZ() + 0.5,
                            Set.of(),
                            serverPlayer.getYRot(),
                            serverPlayer.getXRot(),
                            false
                    );
                    if(shouldRefundGlowstone){
                        ItemEntity itemEntity = new ItemEntity(
                                serverPlayer.level(),
                                respawnPos.getX() + 0.5,
                                respawnPos.getY() + 0.5,
                                respawnPos.getZ() + 0.5,
                                new ItemStack(Blocks.GLOWSTONE)
                        );
                        serverPlayer.level().addFreshEntity(itemEntity);
                    }
                }
            }
        }
        player.setData(ModAttachments.IS_VITALIY_OVERRIDING_HEALTH, false);
        player.setData(ModAttachments.VITALITY, 100f);
        player.setData(ModAttachments.MAXIMUM_VITALITY, 100);
        player.setData(ModAttachments.VITALITY_REGEN, 1.0f);
    }

    @SubscribeEvent
    public static void setMobAttachments(FinalizeSpawnEvent event) {
        Mob spawnedMob = event.getEntity();
        if(spawnedMob.is(ModTags.EntityTypes.LIVING_HUMANOID)){
            register(ModAttachments.MARK_OF_CRYSTAL, 0, spawnedMob);
            randomAssign(ModAttachments.IS_LUNAR, spawnedMob);
        }
    }

    @SubscribeEvent
    public static void setMobSplitAttachments(MobSplitEvent event) {
        List<Mob> spawnedMobs = event.getChildren();
        Mob parentMob = event.getParent();
        if(parentMob.is(ModTags.EntityTypes.LIVING_HUMANOID)) {
            for(Mob spawnedMob : spawnedMobs){
                passOnFromParent(ModAttachments.MARK_OF_CRYSTAL, parentMob, spawnedMob);
                passOnFromParent(ModAttachments.IS_LUNAR, parentMob, spawnedMob);
            }
        }
    }

    private static <T, U extends Entity> void register(Supplier<AttachmentType<T>> attachment, T defaultValue, U entity) {
        AttachmentType<T> type = attachment.get();
        if(!entity.hasData(type)){
            entity.setData(type, defaultValue);
            RuleMaster.LOGGER.info("set {} to {}", type, defaultValue);
        }
    }

    private static <T, U extends Entity> void set(Supplier<AttachmentType<T>> attachment, T value, U entity) {
        entity.setData(attachment, value);
    }

    private static <T, U extends Entity> void passOn(Supplier<AttachmentType<T>> attachment, U entity) {
        entity.setData(attachment, entity.getData(attachment));
    }

    private static <T, U extends Entity> void passOnFromParent(Supplier<AttachmentType<T>> attachment, U parent, U child) {
        child.setData(attachment, parent.getData(attachment));
    }

    private static <U extends Entity> void randomAssign(Supplier<AttachmentType<Boolean>> attachment, U entity) {
        entity.setData(attachment, RandomSource.create().nextBoolean());
    }
}
