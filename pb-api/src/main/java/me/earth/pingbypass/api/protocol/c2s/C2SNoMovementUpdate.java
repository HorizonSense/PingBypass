package me.earth.pingbypass.api.protocol.c2s;

import me.earth.pingbypass.api.Constants;
import me.earth.pingbypass.api.protocol.C2SPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Identifier;

/**
 * Send instead of a {@link net.minecraft.network.protocol.game.ServerboundMovePlayerPacket}, if the client has ran a tick but not changed its position.
 */
public class C2SNoMovementUpdate implements C2SPacket {
    public static final Identifier ID = new Identifier(Constants.NAME_LOW, "no-movement-update");

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public void writePacket(FriendlyByteBuf buf) {
        // nothing to write
    }

}
