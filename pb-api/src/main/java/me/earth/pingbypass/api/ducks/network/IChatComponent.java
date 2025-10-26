package me.earth.pingbypass.api.ducks.network;

import net.minecraft.network.chat.MessageSignature;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public interface IChatComponent {
    void invokeAddMessage(Text component, @Nullable MessageSignature signature);
    void deleteImmediately(@Nullable MessageSignature signature, boolean all);
}
