package net.vincent.rulemaster.networking.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.vincent.rulemaster.RuleMaster;

public record VitalityHealthToggleC2S(boolean isUsingVitality) implements CustomPacketPayload {

    public static final Type<VitalityHealthToggleC2S> TYPE = new Type<>(Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "vitality_health_toggle"));

    public static final StreamCodec<RegistryFriendlyByteBuf, VitalityHealthToggleC2S> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    VitalityHealthToggleC2S::isUsingVitality,

                    VitalityHealthToggleC2S::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
