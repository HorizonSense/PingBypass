package me.earth.pingbypass.api.mixins.entity;

import me.earth.pingbypass.PingBypassApi;
import me.earth.pingbypass.api.event.loop.LocalPlayerUpdateEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinLocalPlayer {
    private void tickHook(CallbackInfo ci) {
        var event = new LocalPlayerUpdateEvent(ClientPlayerEntity.class.cast(this));
        PingBypassApi.getEventBus().post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

}
