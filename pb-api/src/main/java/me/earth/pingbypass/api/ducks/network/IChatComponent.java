package me.earth.pingbypass.api.ducks.network;

import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public interface IChatComponent {
    void invokeAddMessage(Text component, @Nullable MessageSignatureData signature);
    void deleteImmediately(@Nullable MessageSignatureData signature, boolean all);
}
