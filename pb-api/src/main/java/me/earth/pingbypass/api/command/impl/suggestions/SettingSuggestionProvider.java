package me.earth.pingbypass.api.command.impl.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import lombok.RequiredArgsConstructor;
import me.earth.pingbypass.api.command.PBCommandSource;
import me.earth.pingbypass.api.command.impl.FindsArgument;
import me.earth.pingbypass.api.setting.Setting;
import net.minecraft.command.CommandSource;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor(staticName = "of")
public class SettingSuggestionProvider implements SuggestionProvider<PBCommandSource>, FindsArgument {
    private final String settingArgName;
    private final String argName;

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<PBCommandSource> context,
                                                         SuggestionsBuilder builder) {
        Setting<?> setting = getArgument(context, settingArgName, Setting.class);
        if (setting == null) {
            return Suggestions.empty();
        }

        if (!hasArgument(context, argName)) {
            return CommandSource.suggestMatching(setting.getArgumentType().getExamples(), builder);
        }

        return setting.getArgumentType().listSuggestions(context, builder);
    }

}
