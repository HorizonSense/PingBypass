package me.earth.pingbypass.api.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.earth.pingbypass.PingBypass;
import me.earth.pingbypass.api.command.PBCommandSource;
import me.earth.pingbypass.api.command.impl.arguments.DynamicDescriptionArgument;
import me.earth.pingbypass.api.command.impl.arguments.ModuleArgument;
import me.earth.pingbypass.api.module.Module;
import me.earth.pingbypass.api.setting.Setting;
import me.earth.pingbypass.api.setting.impl.SettingUtil;
import me.earth.pingbypass.api.command.AbstractPbCommand;
import net.minecraft.util.Formatting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ResetCommand extends AbstractPbCommand {
    public ResetCommand(PingBypass pingBypass, MinecraftClient mc) {
        super("reset", "Resets the settings of a module.", pingBypass, mc);
    }

    @Override
    public void build(LiteralArgumentBuilder<PBCommandSource> builder) {
        DynamicDescriptionArgument.SavingArgumentType<Module> moduleArg = new DynamicDescriptionArgument.SavingArgumentType<>(new ModuleArgument(pingBypass.getModuleManager()));
        builder.then(arg("module", moduleArg).executes(ctx -> {
            Module module = ctx.getArgument("module", Module.class);
            print(Text.literal("Resetting ").withStyle(Formatting.RED)
                    .append(Text.literal(module.getName()).withStyle(Formatting.WHITE, Formatting.BOLD)));
            module.stream().forEach(setting -> SettingUtil.setValueUnchecked(setting, setting.getDefaultValue()));
        }).then(arg("setting", new DynamicDescriptionArgument<>(moduleArg, "setting")).executes(ctx -> {
            Module module = ctx.getArgument("module", Module.class);
            Setting<?> setting = ctx.getArgument("setting", Setting.class);
            print(Text.literal("Resetting ").withStyle(Formatting.RED)
                    .append(Text.literal(module.getName()).withStyle(Formatting.WHITE, Formatting.BOLD))
                    .append(Text.literal(" - ").withStyle(Formatting.GRAY))
                    .append(Text.literal(setting.getName()).withStyle(Formatting.AQUA)));
            SettingUtil.setValueUnchecked(setting, setting.getDefaultValue());
        })));
    }

}
