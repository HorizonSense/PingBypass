package me.earth.pingbypass.api.command.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.earth.pingbypass.PingBypass;
import me.earth.pingbypass.api.command.AbstractPbCommand;
import me.earth.pingbypass.api.command.PBCommandSource;
import net.minecraft.client.MinecraftClient;

public class QuitCommand extends AbstractPbCommand {
    public QuitCommand(PingBypass pingBypass, MinecraftClient mc) {
        super("quit", "Quits the game.", pingBypass, mc);
    }

    @Override
    public void build(LiteralArgumentBuilder<PBCommandSource> builder) {
        builder.executes(ctx -> {
            mc.stop();
            return Command.SINGLE_SUCCESS;
        });
    }

}
