package me.earth.pingbypass.api.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.util.annotation.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandSource;
import net.minecraft.registry.Registry;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
public interface EmptySuggestionProvider extends CommandSource {
    EmptySuggestionProvider INSTANCE = new EmptySuggestionProvider() {};

    @Override
    default Collection<String> getOnlinePlayerNames() {
        return Collections.emptyList();
    }

    @Override
    default Collection<String> getAllTeams() {
        return Collections.emptyList();
    }

    @Override
    default Stream<Identifier> getAvailableSounds() {
        return Stream.empty();
    }

    @Override
    default Stream<Identifier> getRecipeNames() {
        return Stream.empty();
    }

    @Override
    default CompletableFuture<Suggestions> customSuggestion(CommandContext<?> commandContext) {
        return Suggestions.empty();
    }

    @Override
    default Set<RegistryKey<World>> levels() {
        return Collections.emptySet();
    }

    @Override
    default DynamicRegistryManager dynamicRegistryManager() {
        return DynamicRegistryManager.EMPTY;
    }

    @Override
    default FeatureSet enabledFeatures() {
        return FeatureSet.of();
    }

    @Override
    default CompletableFuture<Suggestions> suggestRegistryElements(RegistryKey<? extends Registry<?>> arg,
                                                                   CommandRegistryAccess arg2,
                                                                  SuggestionsBuilder suggestionsBuilder,
                                                                  CommandContext<?> commandContext) {
        return Suggestions.empty();
    }

    @Override
    default boolean hasPermission(int i) {
        return false;
    }

}
