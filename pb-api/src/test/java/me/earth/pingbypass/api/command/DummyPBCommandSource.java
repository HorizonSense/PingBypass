package me.earth.pingbypass.api.command;

import me.earth.pingbypass.PingBypass;
import net.minecraft.client.Minecraft;

public enum DummyPBCommandSource implements PBCommandSource, EmptySuggestionProvider {
    INSTANCE;

    @Override
    public PingBypass getPingBypass() {
        return null;
    }

    @Override
    public Minecraft getMinecraft() {
        return null;
    }

}
