package me.earth.pingbypass.api.event.network;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.earth.pingbypass.api.event.api.EventListener;
import me.earth.pingbypass.api.event.listeners.generic.GenericListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.Packet;

/**
 * @param <P> the type of packet processed by this listener, most likely for the {@link ClientPlayPacketListener}, but not guaranteed.
 */
public abstract class ReceiveListener<P extends Packet<?>> extends GenericListener<PacketEvent.Receive<P>> {
    public ReceiveListener() {
        this(EventListener.DEFAULT_LISTENER_PRIORITY);
    }

    public ReceiveListener(int priority) {
        super(PacketEvent.Receive.class, priority);
    }

    /**
     * @param <P> the type of packet processed by this listener, most likely for the {@link ClientPlayPacketListener}, but not guaranteed.
     */
    @NoArgsConstructor
    public static abstract class Direct<P extends Packet<?>> extends ReceiveListener<P> {
        @Override
        public void onEvent(PacketEvent.Receive<P> event) {
            onPacket(event.getPacket());
        }

        public abstract void onPacket(P packet);
    }

    @RequiredArgsConstructor
    public static abstract class Scheduled<P extends Packet<?>> extends ReceiveListener<P> {
        protected final MinecraftClient mc;

        public Scheduled(MinecraftClient mc, int priority) {
            super(priority);
            this.mc = mc;
        }

        public abstract void onEventMainThread(PacketEvent.Receive<P> event);

        @Override
        public void onEvent(PacketEvent.Receive<P> event) {
            mc.submit(() -> onEventMainThread(event));
        }

        public static abstract class Safe<P extends Packet<?>> extends Scheduled<P> {
            public Safe(MinecraftClient mc) {
                super(mc);
            }

            public Safe(MinecraftClient mc, int priority) {
                super(mc, priority);
            }

            public abstract void onSafeEvent(PacketEvent.Receive<P> event, ClientPlayerEntity player, ClientWorld level, ClientPlayerInteractionManager gameMode);

            @Override
            public void onEventMainThread(PacketEvent.Receive<P> event) {
                ClientPlayerEntity player = mc.player;
                ClientWorld level = mc.world;
                ClientPlayerInteractionManager gameMode = mc.interactionManager;
                if (player != null && level != null) {
                    this.onSafeEvent(event, player, level, gameMode);
                }
            }
        }
    }

}
