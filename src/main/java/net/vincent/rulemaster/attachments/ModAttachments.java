package net.vincent.rulemaster.attachments;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.vincent.rulemaster.RuleMaster;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, RuleMaster.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> MARK_OF_CRYSTAL =
            register("mark_of_crystal", 0, Codec.INT);

    public static final Supplier<AttachmentType<Boolean>> IS_LUNAR =
            register("is_lunar", true, Codec.BOOL);

    public static final Supplier<AttachmentType<Boolean>> PLAYER_JOINED =
            register("player_joined", false, Codec.BOOL);

    public static final Supplier<AttachmentType<Boolean>> PLAYER_SHOULD_RESPAWN_IN_END =
            register("player_should_respawn_in_end", true, Codec.BOOL);

    public static final Supplier<AttachmentType<BlockPos>> PLAYER_END_SPAWN_POS =
            register("player_end_spawn_pos", new BlockPos(100, 50, 0), BlockPos.CODEC);

    // Use the register() method to create a new attachment.
    // Use by passing in the ID of the attachment, the default value of the attachment,
    // and the codec of that attachment
    // Store the attachment into a <Supplier<AttachmentType<T>>>,
    // where T is the data type of the attachment stored

    public static <T, U extends Codec<T>> Supplier<AttachmentType<T>> register(String name, T defaultValue, U codec){
        return ATTACHMENTS.register(name, () -> AttachmentType.builder(() -> defaultValue).serialize(codec.fieldOf(name)).build());
    }

    public static void register(IEventBus eventBus){
        ATTACHMENTS.register(eventBus);
    }
}
