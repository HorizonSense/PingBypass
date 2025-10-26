package me.earth.pingbypass.api.protocol;

import me.earth.pingbypass.api.util.packet.CustomPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.protocol.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;

/**
 * Base class for packets sent from the PingBypassServer to the PingBypassClient.
 */
public interface S2CPacket extends CustomPacket, PBPacket {
    // Trong 1.21.1, ClientboundCustomPayloadPacket.TYPE là PacketType tương ứng
    PacketType<ClientboundCustomPayloadPacket> TYPE = ClientboundCustomPayloadPacket.TYPE;

    @Override
    default void write(FriendlyByteBuf buffer) {
        new ClientboundCustomPayloadPacket(new Payload(this)).write(buffer);
    }

    /**
     * Thay thế cho getId(ConnectionProtocol.CodecData<?> codecData)
     * CodecData bị loại bỏ từ 1.21.1, nên sử dụng PacketType thay thế.
     */
    default Integer getId(PacketType<?> packetType) {
        // 1.21.1 không còn id số cố định, ta dùng hashCode để tạo mã duy nhất ổn định
        return packetType.hashCode();
    }

    @Override
    default ConnectionProtocol getProtocol() {
        return ConnectionProtocol.PLAY;
    }

    @Override
    default PacketFlow getFlow() {
        return PacketFlow.CLIENTBOUND;
    }
}