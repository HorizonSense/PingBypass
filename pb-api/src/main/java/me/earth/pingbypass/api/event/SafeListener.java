package me.earth.pingbypass.api.event;

import lombok.RequiredArgsConstructor;
import me.earth.pingbypass.api.event.listeners.generic.Listener;
import me.earth.pingbypass.api.util.exceptions.NullabilityUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;

@RequiredArgsConstructor
public abstract class SafeListener<E> extends Listener<E> {
    protected final MinecraftClient mc;

    public SafeListener(MinecraftClient mc, int priority) {
        super(priority);
        this.mc = mc;
    }

    public abstract void onEvent(E event, ClientPlayerEntity player, ClientWorld level, ClientPlayerInteractionManager gameMode);

    @Override
    public void onEvent(E event) {
        NullabilityUtil.safe(mc, (player, world, interactionManager) ->
                onEvent(event, player, world, interactionManager));
    }

}
