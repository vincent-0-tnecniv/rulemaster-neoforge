package net.vincent.rulemaster.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.vincent.rulemaster.RuleMaster;

public record CameraShakePayload(float intensityX, float intensityY, float intensityZ, int duration) implements CustomPacketPayload {

    public static final Type<CameraShakePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(RuleMaster.MOD_ID, "camera_shake"));

    public static final StreamCodec<FriendlyByteBuf, CameraShakePayload> STREAM_CODEC =
            StreamCodec.ofMember(CameraShakePayload::write, CameraShakePayload::new);

    private CameraShakePayload(FriendlyByteBuf buf) {
        this(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt());
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeFloat(intensityX);
        buf.writeFloat(intensityY);
        buf.writeFloat(intensityZ);
        buf.writeInt(duration);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}