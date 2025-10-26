package me.earth.pingbypass.api.command;

import net.minecraft.text.Text;

public interface Chat {
    void send(Text message);

    void send(Text message, String identifier);

    void sendWithoutLogging(Text message);

    void sendWithoutLogging(Text message, String identifier);

    void delete(String identifier);

    default void send(String string) {
        this.send(Text.literal(string));
    }

}
