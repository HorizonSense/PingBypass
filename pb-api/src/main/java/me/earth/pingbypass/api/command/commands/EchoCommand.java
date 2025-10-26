package me.earth.pingbypass.api.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.earth.pingbypass.PingBypass;
import me.earth.pingbypass.api.command.AbstractPbCommand;
import me.earth.pingbypass.api.command.PBCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public final class EchoCommand extends AbstractPbCommand {
    public EchoCommand(PingBypass pingBypass, MinecraftClient minecraft) {
        super("echo", "Prints a message in chat.", pingBypass, minecraft);
    }

    @Override
    public void build(LiteralArgumentBuilder<PBCommandSource> builder) {
        builder.then(greedy("message").executes(ctx -> {
            String message = ctx.getArgument("message", String.class);
            print(Text.literal(message));
        }));
    }

}
