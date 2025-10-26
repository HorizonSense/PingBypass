package me.earth.pingbypass.api.util.packet;

import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;

/**
 * Tương thích 1.21.1:
 * - Không còn CodecData, nên ta mô phỏng bằng CustomPacketRegistry.
 */
public interface CustomPacket {
    /**
     * Lấy ID của packet từ registry.
     */
    default Integer getId() {
        return CustomPacketRegistry.getId(this.getClass());
    }

    /**
     * Lấy ConnectionProtocol mà packet này thuộc về (nếu có).
     * Nếu không xác định, trả về null.
     */
    default ConnectionProtocol getProtocol() {
        return null; // 1.21.1: protocol không còn gắn chặt vào packet
    }

    /**
     * Xác định chiều của packet (clientbound hoặc serverbound).
     */
    default PacketFlow getFlow() {
        return PacketFlow.CLIENTBOUND; // hoặc SERVERBOUND tuỳ loại packet
    }

    /**
     * Xác minh tính hợp lệ của packet theo flow và protocol.
     */
    default boolean isValidPacket() {
        Integer id = getId();
        return id != null && id >= 0;
    }
}