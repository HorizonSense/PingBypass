package me.earth.pingbypass.api.command.impl;

import com.mojang.brigadier.arguments.ArgumentType;
import me.earth.pingbypass.api.command.PBCommandSource;
import me.earth.pingbypass.api.command.impl.arguments.StringArgument;
import me.earth.pingbypass.api.command.impl.builder.ExtendedLiteralArgumentBuilder;
import me.earth.pingbypass.api.command.impl.builder.ExtendedRequiredArgumentBuilder;

public interface UsesExtendedBuilders {
    default <T> ExtendedRequiredArgumentBuilder<PBCommandSource, T> arg(String name, ArgumentType<T> type) {
        return new ExtendedRequiredArgumentBuilder<>(type, name);
    }

    default ExtendedLiteralArgumentBuilder<PBCommandSource> literal(String literal) {
        return new ExtendedLiteralArgumentBuilder<>(literal);
    }

    default ExtendedRequiredArgumentBuilder<PBCommandSource, String> greedy(String name) {
        return new ExtendedRequiredArgumentBuilder<>(StringArgument.greedy(name), name);
    }

}
