package me.earth.pingbypass.api.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.earth.pingbypass.PingBypass;
import me.earth.pingbypass.api.command.AbstractPbCommand;
import me.earth.pingbypass.api.command.PBCommandSource;
import me.earth.pingbypass.api.command.impl.arguments.ConfigTypeArgument;
import me.earth.pingbypass.api.command.impl.arguments.StringArgument;
import me.earth.pingbypass.api.command.impl.builder.ExtendedLiteralArgumentBuilder;
import me.earth.pingbypass.api.config.Config;
import me.earth.pingbypass.api.config.ConfigException;
import me.earth.pingbypass.api.util.ThrowingConsumer;
import me.earth.pingbypass.api.util.exceptions.ThrowingBiConsumer;
import me.earth.pingbypass.api.command.suggestions.ConfigSuggestionProvider;
import net.minecraft.util.Formatting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ConfigCommand extends AbstractPbCommand {
    public ConfigCommand(PingBypass pingBypass, MinecraftClient mc) {
        super("config", "Loads, saves and deletes configs.", pingBypass, mc);
    }

    @Override
    public void build(LiteralArgumentBuilder<PBCommandSource> builder) {
        // TODO: why does "help config" not display the fourth name arg?
        builder.then(
            arg("type", new ConfigTypeArgument(pingBypass.getConfigManager()))
                .then(executeWithoutNameArg(literal("refresh"), Config::refresh))
                .then(executeWithNameArg(literal("load"), Config::load))
                .then(executeWithNameArg(literal("delete"), Config::delete))
                .then(executeWithoutNameArg(executeWithNameArg(literal("save"), Config::save), Config::save)));
    }

    private ExtendedLiteralArgumentBuilder<PBCommandSource> executeWithoutNameArg(
            ExtendedLiteralArgumentBuilder<PBCommandSource> builder,
            ThrowingConsumer<Config<?>, ConfigException> action) {
        builder.executes(ctx -> {
            Config<?> config = ctx.getArgument("type", Config.class);
            try {
                action.acceptWithException(config);
            } catch (ConfigException e) {
                print(Text.literal(e.getMessage()).withStyle(Formatting.RED));
            }
        });


        return builder;
    }

    private ExtendedLiteralArgumentBuilder<PBCommandSource> executeWithNameArg(
            ExtendedLiteralArgumentBuilder<PBCommandSource> builder,
            ThrowingBiConsumer<Config<?>, String, ConfigException> action) {
        builder.then(
            arg("name", StringArgument.word("name"))
                .suggests(ConfigSuggestionProvider.of("type"))
                .executes(ctx -> {
                    Config<?> config = ctx.getArgument("type", Config.class);
                    try {
                        action.acceptWithException(config, ctx.getArgument("name", String.class));
                    } catch (ConfigException e) {
                        print(Text.literal(e.getMessage()).withStyle(Formatting.RED));
                    }
                }));

        return builder;
    }

}
