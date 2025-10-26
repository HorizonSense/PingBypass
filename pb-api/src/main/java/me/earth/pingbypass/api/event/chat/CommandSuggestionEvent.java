package me.earth.pingbypass.api.event.chat;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.earth.pingbypass.api.command.PBCommandSource;
import me.earth.pingbypass.api.event.event.CancellableEvent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.command.CommandSource;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@AllArgsConstructor
public class CommandSuggestionEvent extends CancellableEvent {
    private final StringReader stringReader;
    private final boolean keepSuggestions;
    private final EditBox input;
    private final Object suggestions;
    private @Nullable CompletableFuture<Suggestions> pendingSuggestions;
    private @Nullable ParseResults<CommandSource> currentParse;
    private @Nullable ParseResults<PBCommandSource> customParse;
    private boolean updatingPendingSuggestions;

}
