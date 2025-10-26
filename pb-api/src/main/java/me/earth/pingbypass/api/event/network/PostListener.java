package me.earth.pingbypass.api.event.network;

import me.earth.pingbypass.api.event.api.EventListener;
import me.earth.pingbypass.api.event.listeners.generic.GenericListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.Packet;

/**
 * @param <P> the type of packet processed by this listener, most likely for the {@link ClientGamePacketListener}, but not guaranteed.
 */
public abstract class PostListener<P extends Packet<?>> extends GenericListener<PacketEvent.PostReceive<P>> {
    protected final MinecraftClient mc;

    public PostListener(int priority, MinecraftClient mc) {
        super(PacketEvent.PostReceive.class, priority);
        this.mc = mc;
    }

    public PostListener(MinecraftClient mc) {
        this(EventListener.DEFAULT_LISTENER_PRIORITY, mc);
    }

    public abstract void onEventMainthread(PacketEvent.PostReceive<P> event);

    @Override
    public void onEvent(PacketEvent.PostReceive<P> event) {
        mc.submit(() -> onEventMainthread(event));
    }

    public static abstract class Safe<P extends Packet<?>> extends PostListener<P> {
        public Safe(int priority, MinecraftClient mc) {
            super(priority, mc);
        }

        public Safe(MinecraftClient mc) {
            super(mc);
        }

        public abstract void onSafeEvent(PacketEvent.PostReceive<P> event, ClientPlayerEntity player, ClientLevel level, MultiPlayerGameMode gameMode);

        @Override
        public void onEventMainthread(PacketEvent.PostReceive<P> event) {
            ClientPlayerEntity player = mc.player;
            ClientWorld level = mc.world;
            ClientPlayerInteractionManager gameMode = mc.interactionManager;
            if (player != null && level != null) {
                this.onSafeEvent(event, player, level, gameMode);
            }
        }

        public static abstract class Direct<P extends Packet<?>> extends Safe<P> {
            public Direct(int priority, MinecraftClient mc) {
                super(priority, mc);
            }

            public Direct(MinecraftClient mc) {
                super(mc);
            }

            public abstract void onSafePacket(P packet, ClientPlayerEntity player, ClientLevel level, MultiPlayerGameMode gameMode);

            @Override
            public void onSafeEvent(PacketEvent.PostReceive<P> event, ClientPlayerEntity player, ClientLevel level, MultiPlayerGameMode gameMode) {
                onSafePacket(event.getPacket(), player, level, gameMode);
            }
        }
    }

}
