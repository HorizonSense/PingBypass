package me.earth.pingbypass.api.util.packet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hệ thống mô phỏng PacketSet cũ.
 * Dùng để đăng ký và truy vấn ID của các packet tuỳ chỉnh.
 */
public class CustomPacketRegistry {
    private static final Map<Class<?>, Integer> packetIds = new ConcurrentHashMap<>();

    /**
     * Đăng ký một packet với ID của nó.
     */
    public static void register(Class<?> packetClass, int id) {
        packetIds.put(packetClass, id);
    }

    /**
     * Lấy ID của packet.
     */
    public static Integer getId(Class<?> packetClass) {
        return packetIds.get(packetClass);
    }

    /**
     * Xoá tất cả các packet đã đăng ký (nếu cần reset).
     */
    public static void clear() {
        packetIds.clear();
    }
}