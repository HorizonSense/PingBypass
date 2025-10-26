package me.earth.pingbypass.api.event.network;

import me.earth.pingbypass.api.event.api.EventListener;
import me.earth.pingbypass.api.event.listeners.generic.GenericListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.Packet;

public abstract class AsyncReceiveListener<P extends Packet<?>> extends GenericListener<PacketEvent.Receive<P>> {
    private final MinecraftClient mc;

    public AsyncReceiveListener(int priority, MinecraftClient mc) {
        super(PacketEvent.Receive.class, priority);
        this.mc = mc;
    }

    public AsyncReceiveListener(MinecraftClient mc) {
        this(EventListener.DEFAULT_LISTENER_PRIORITY, mc);
    }

    public abstract void onEvent(PacketEvent.Receive<P> event, ClientPlayerEntity player, ClientLevel level, MultiPlayerGameMode gameMode);

    @Override
    public void onEvent(PacketEvent.Receive<P> event) {
        ClientPlayerEntity player = mc.player;
        ClientWorld level = mc.world;
        ClientPlayerInteractionManager gameMode = mc.gameMode;
        if (player != null && level != null && gameMode != null) {
            this.onEvent(event, player, level, gameMode);
        }
    }

}
