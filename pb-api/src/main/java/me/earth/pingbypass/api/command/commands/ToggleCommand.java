package me.earth.pingbypass.api.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.earth.pingbypass.PingBypass;
import me.earth.pingbypass.api.command.AbstractPbCommand;
import me.earth.pingbypass.api.command.PBCommandSource;
import me.earth.pingbypass.api.command.impl.arguments.ModuleArgument;
import me.earth.pingbypass.api.module.Module;
import net.minecraft.util.Formatting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ToggleCommand extends AbstractPbCommand {
    public ToggleCommand(PingBypass pingBypass, MinecraftClient mc) {
        super("toggle", "Toggles a module.", pingBypass, mc);
    }

    @Override
    public void build(LiteralArgumentBuilder<PBCommandSource> builder) {
        builder.then(arg("module", new ModuleArgument(pingBypass.getModuleManager())).executes(ctx -> {
            Module module = ctx.getArgument("module", Module.class);
            module.toggle();
            print(Text.literal("Toggling ")
                            .formatted(module.isEnabled() ? Formatting.GREEN : Formatting.RED)
                            .append(Text.literal(module.getName())
                                    .formatted(Formatting.WHITE, Formatting.BOLD)), module.getName());
        }));
    }

}
