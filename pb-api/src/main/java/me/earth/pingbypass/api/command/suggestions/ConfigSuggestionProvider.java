package me.earth.pingbypass.api.command.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import lombok.RequiredArgsConstructor;
import me.earth.pingbypass.api.command.PBCommandSource;
import me.earth.pingbypass.api.command.impl.FindsArgument;
import me.earth.pingbypass.api.config.Config;
import me.earth.pingbypass.api.config.ConfigManager;
import me.earth.pingbypass.api.traits.Nameable;
import net.minecraft.command.CommandSource;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor(staticName = "of")
public class ConfigSuggestionProvider implements SuggestionProvider<PBCommandSource>, FindsArgument {
    private final String configArgName;

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<PBCommandSource> context,
                                                         SuggestionsBuilder builder) {
        Config<?> config = getArgument(context, configArgName, Config.class);
        if (config == null || config.stream().findAny().isEmpty()) {
            return CommandSource.suggestMatching(new String[]{"<name>"}, builder);
        }

        return config instanceof ConfigManager cm
                ? CommandSource.suggestMatching(cm.stream().flatMap(Config::stream).map(Nameable::getName), builder)
                : CommandSource.suggestMatching(config.stream().map(Nameable::getName), builder);
    }

}
