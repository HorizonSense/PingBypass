package me.earth.pingbypass.api.launch;

import net.minecraft.client.MinecraftClient;

public interface Initializer {
    void init(MinecraftClient mc, PreLaunchService preLaunchService);

}
