package me.earth.pingbypass.api.mixins.network;

import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin vào PacketCodec (class_9139) cho 1.21.1
 * Phải remap = false vì mapping Yarn không có entry encode().
 */
@Mixin(targets = "net.minecraft.class_9139", remap = false)
public class MixinPacketEncoder {
    @Inject(
        method = "encode(Lio/netty/buffer/ByteBuf;Ljava/lang/Object;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onEncode(ByteBuf buf, Object packet, CallbackInfo ci) {
        System.out.println("[PingBypass] Encoding packet: " + packet);
    }
}