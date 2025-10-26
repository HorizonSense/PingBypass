package me.earth.pingbypass.api.protocol;

import me.earth.pingbypass.api.util.packet.CustomPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.network.protocol.PacketFlow;

/**
 * Base class for packets sent from the PingBypassClient to the PingBypassServer.
 * Updated for Minecraft 1.21.1
 */
public interface C2SPacket extends CustomPacket, PBPacket {
    PacketType<CustomPayloadC2SPacket> TYPE = CustomPayloadC2SPacket.TYPE;

    @Override
    default void write(FriendlyByteBuf buffer) {
        new CustomPayloadC2SPacket(new Payload(this)).write(buffer);
    }

    @Override
    default Integer getId(PacketType<?> type) {
        return null;
    }

    @Override
    default Object getProtocol() {
        return null;
    }

    @Override
    default PacketFlow getFlow() {
        return PacketFlow.SERVERBOUND;
    }
}