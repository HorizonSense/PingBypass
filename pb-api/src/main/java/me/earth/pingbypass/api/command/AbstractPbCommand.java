package me.earth.pingbypass.api.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import me.earth.pingbypass.PingBypass;
import me.earth.pingbypass.api.command.impl.AbstractCommand;
import me.earth.pingbypass.api.command.impl.builder.SuccessfulCommand;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public abstract class AbstractPbCommand extends AbstractCommand {
    // TODO: this is not really needed anymore, use PBCommandSource everywhere!
    protected final PingBypass pingBypass;
    protected final MinecraftClient mc;

    public AbstractPbCommand(String name, String description, PingBypass pingBypass, MinecraftClient mc) {
        super(name, description);
        this.pingBypass = pingBypass;
        this.mc = mc;
    }

    public AbstractPbCommand(String name, String description, PingBypass pingBypass) {
        this(name, description, pingBypass, pingBypass.getMinecraft());
    }

    @SafeVarargs
    protected final <T extends ArgumentBuilder<PBCommandSource, T>> void executesWithOptionalArguments(
            SuccessfulCommand<PBCommandSource> command,
            T builder,
            ArgumentBuilder<PBCommandSource, ?>... arguments) {
        builder.executes(command);
        for (var argument : arguments) {
            builder.then(argument.executes(command));
        }
    }

    protected Chat getChat() {
        return pingBypass.getChat();
    }

    protected void print(Text message) {
        getChat().send(message);
    }

    protected void error(String message) {
        getChat().send(Text.literal(message).formatted(Formatting.RED));
    }

    protected void print(Text message, String identifier) {
        getChat().send(message, identifier);
    }

}
