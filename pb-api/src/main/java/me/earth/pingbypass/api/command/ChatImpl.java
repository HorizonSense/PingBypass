package me.earth.pingbypass.api.command;

import lombok.RequiredArgsConstructor;
import me.earth.pingbypass.api.ducks.network.IChatComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.minecraft.network.message.MessageSignatureData;

import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class ChatImpl implements Chat {
    private final MinecraftClient mc;

    @Override
    public void send(Text message) {
        mc.gui.getChat().addMessage(message);
    }

    @Override
    public void send(Text message, String identifier) {
        send(mc.gui.getChat(), message, identifier);
    }

    @Override
    public void sendWithoutLogging(Text message) {
        sendWithoutLogging((IChatComponent) mc.gui.getChat(), message, null);
    }

    @Override
    public void sendWithoutLogging(Text message, String identifier) {
        sendWithoutLogging((IChatComponent) mc.gui.getChat(), message, identifier);
    }

    @Override
    public void delete(String identifier) {
        delete(new MessageSignatureData(get256Bytes(identifier)));
    }

    private void send(ChatHud chat, Text message, @Nullable String identifier) {
        MessageSignatureData  signature = identifier != null ? new MessageSignatureData (get256Bytes(identifier)) : null;
        delete(signature);
        chat.addMessage(message.formatted(net.minecraft.util.Formatting.GRAY, net.minecraft.util.Formatting.ITALIC), signature);
    }

    private void sendWithoutLogging(ChatHud chat, Text message, @Nullable String identifier) {
        MessageSignatureData  signature = identifier != null ? new MessageSignatureData (get256Bytes(identifier)) : null;
        delete(signature);
        chat.addMessage(message.formatted(Formatting.GRAY, Formatting.ITALIC), signature);
    }

    private void delete(@Nullable MessageSignatureData  signature) {
        ((IChatComponent) mc.gui.getChat()).deleteImmediately(signature, true);
    }

    private byte[] get256Bytes(String identifier) {
        byte[] bytes = new byte[256];
        byte[] identifierBytes = identifier.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(identifierBytes, 0, bytes, 0, Math.min(bytes.length, identifierBytes.length));
        return bytes;
    }

}
