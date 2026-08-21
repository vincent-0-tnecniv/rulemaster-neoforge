package net.vincent.rulemaster.networking.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.vincent.rulemaster.RuleMaster;

public record CameraShakePacketS2C(float intensityX, float intensityY,
                                   float intensityZ, int duration) implements CustomPacketPayload {

    public static final Type<CameraShakePacketS2C> TYPE = new Type<>(Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "camera_shake_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CameraShakePacketS2C> STREAM_CODEC =
        StreamCodec.composite(
                ByteBufCodecs.FLOAT,
                CameraShakePacketS2C::intensityX,

                ByteBufCodecs.FLOAT,
                CameraShakePacketS2C::intensityY,

                ByteBufCodecs.FLOAT,
                CameraShakePacketS2C::intensityZ,

                ByteBufCodecs.VAR_INT,
                CameraShakePacketS2C::duration,

                CameraShakePacketS2C::new
        );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
