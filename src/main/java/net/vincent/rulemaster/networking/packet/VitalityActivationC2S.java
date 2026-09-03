package net.vincent.rulemaster.networking.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.vincent.rulemaster.RuleMaster;

public record VitalityActivationC2S(float startingVitality, int maximumVitality, float vitalityRegen) implements CustomPacketPayload{

    public static final Type<VitalityActivationC2S> TYPE = new Type<>(Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "vitality_activation_c2s"));

    public static final StreamCodec<RegistryFriendlyByteBuf, VitalityActivationC2S> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT,
                    VitalityActivationC2S::startingVitality,

                    ByteBufCodecs.INT,
                    VitalityActivationC2S::maximumVitality,

                    ByteBufCodecs.FLOAT,
                    VitalityActivationC2S::vitalityRegen,

                    VitalityActivationC2S::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
