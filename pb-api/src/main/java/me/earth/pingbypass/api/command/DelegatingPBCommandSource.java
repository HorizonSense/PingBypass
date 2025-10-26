package me.earth.pingbypass.api.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import me.earth.pingbypass.PingBypass;
import net.minecraft.command.CommandSource;
import net.minecraft.util.annotation.MethodsReturnNonnullByDefault;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@MethodsReturnNonnullByDefault
public class DelegatingPBCommandSource implements PBCommandSource {
    @Getter(onMethod_ = {@NotNull}) // idk this does not seem to work? have to suppress warnings in ContextImpl
    private final MinecraftClient minecraft;
    @Getter
    private final PingBypass pingBypass;

    @Delegate
    @SuppressWarnings("unused")
    public CommandSource getSuggestionProvider() {
        ClientPlayerEntity player = minecraft.player;
        if (player != null) {
            return player.connection.getSuggestionsProvider();
        }

        return EmptySuggestionProvider.INSTANCE;
    }

}
