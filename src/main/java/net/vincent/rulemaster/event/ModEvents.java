package net.vincent.rulemaster.event;

import com.electronwill.nightconfig.core.io.IoUtils;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.MobSplitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.vincent.rulemaster.RuleMaster;
import net.vincent.rulemaster.attachments.ModAttachments;
import net.vincent.rulemaster.block.ModBlocks;
import net.vincent.rulemaster.block.custom.FleshBlock;
import net.vincent.rulemaster.command.ModCommands;
import net.vincent.rulemaster.item.ModItems;
import net.vincent.rulemaster.item.custom.LivoGuideBookItem;
import net.vincent.rulemaster.tags.ModTags;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

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
    public static void setPlayerAttachments(PlayerEvent.PlayerLoggedInEvent event) {
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
        RuleMaster.LOGGER.info("Player joined!");
        if(!player.getData(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END) && player instanceof ServerPlayer serverPlayer) {
            BlockPos pos = player.getData(ModAttachments.PLAYER_END_SPAWN_POS);
            serverPlayer.setPos(pos.getX(), pos.getY(), pos.getZ());
            ServerLevel endServerLevel = serverPlayer.level().getServer().getLevel(Level.END);
            if(endServerLevel == null){
                throw new IllegalStateException("There is no end somehow???");
            }
            serverPlayer.setServerLevel(endServerLevel);
            serverPlayer.setData(ModAttachments.PLAYER_END_SPAWN_POS, BlockPos.ZERO);
            serverPlayer.setData(ModAttachments.PLAYER_SHOULD_RESPAWN_IN_END, true);
        }
    }

    private static boolean isLunar(Player player) {
        return player.getData(ModAttachments.IS_LUNAR);
    }

    @SubscribeEvent
    public static void setClonedPlayerAttachments(PlayerEvent.Clone event) {
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
        Player player = event.getEntity();
        passOn(ModAttachments.MARK_OF_CRYSTAL, player);
        passOn(ModAttachments.IS_LUNAR, player);
        passOn(ModAttachments.PLAYER_JOINED, player);
    }

    @SubscribeEvent
    public static void setRespawnedPlayerAttachments(PlayerEvent.PlayerRespawnEvent event) {
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
                boolean shouldRefunedGlowstone = false;
                if(playerCurrentLevel.dimension() == Level.NETHER){
                    // Player would have used up a glowstone to respawn in the nether to tp to the end - refund a glowstone
                    shouldRefunedGlowstone = true;
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
                    if(shouldRefunedGlowstone){
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

        if(entity.hasData(attachment)){
            passOn(attachment, entity);
        } else{
            set(attachment, defaultValue, entity);
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
