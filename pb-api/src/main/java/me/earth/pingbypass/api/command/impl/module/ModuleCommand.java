package me.earth.pingbypass.api.command.impl.module;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import lombok.experimental.ExtensionMethod;
import me.earth.pingbypass.api.command.PBCommandSource;
import me.earth.pingbypass.api.command.impl.AbstractCommand;
import me.earth.pingbypass.api.command.impl.PrintsInContextChat;
import me.earth.pingbypass.api.command.impl.UsesExtendedBuilders;
import me.earth.pingbypass.api.command.impl.arguments.SettingArgument;
import me.earth.pingbypass.api.command.impl.builder.SuccessfulCommand;
import me.earth.pingbypass.api.command.impl.suggestions.SettingSuggestionProvider;
import me.earth.pingbypass.api.command.impl.util.ComponentUtil;
import me.earth.pingbypass.api.module.Module;
import me.earth.pingbypass.api.setting.Setting;
import me.earth.pingbypass.api.setting.impl.SettingUtil;
import net.minecraft.text.Text;

import static net.minecraft.util.Formatting.*;

@ExtensionMethod(SettingUtil.class)
public class ModuleCommand extends AbstractCommand implements PrintsInContextChat, UsesExtendedBuilders {
    private final Module module;

    public ModuleCommand(Module module) {
        super(module.getName(), "Configures %s.".formatted(module.getName()));
        this.module = module;
    }

    @Override
    public void build(LiteralArgumentBuilder<PBCommandSource> builder) {
        if (module instanceof HasCustomModuleCommand hasCustomModuleCommand) {
            hasCustomModuleCommand.build(builder);
            if (hasCustomModuleCommand.overrideCompletely()) {
                return;
            }
        }

        builder.executes((SuccessfulCommand<PBCommandSource>) ctx -> {
            print(ctx, ComponentUtil.getComponent(module, AQUA)
                    .append(Text.literal(" - ").formatted(GRAY))
                    .append(Text.literal(module.getCategory().getName()).formatted(WHITE)));
            module.stream().forEach(setting ->
                print(ctx, ComponentUtil.getComponent(setting, WHITE)
                    .append(Text.literal(" : ").formatted(GRAY))
                        // TODO: see TODOS in ComponentUtil and SettingImpl
                    .append(setting.getValueComponent())));
        }).then(arg("setting", new SettingArgument(module)).executes(ctx -> {
            Setting<?> setting = ctx.getArgument("setting", Setting.class);
            print(ctx, ComponentUtil.getComponent(setting, WHITE).append(Text.literal(": ").formatted(GRAY).append(setting.getValueComponent())));
        }).then(arg("value", StringArgumentType.greedyString()) // TODO: might regret greedyString for modules with custom commands?
            .suggests(SettingSuggestionProvider.of("setting", "value"))
            .executes(ctx -> {
                Setting<?> setting = ctx.getArgument("setting", Setting.class);
                String value = ctx.getArgument("value", String.class);
                Object parsed = setting.getArgumentType().parse(new StringReader(value));
                setting.setValueUnchecked(parsed);
                print(ctx, ComponentUtil.getComponent(module, WHITE)
                        .append(" ")
                        .append(ComponentUtil.getComponent(setting, AQUA))
                        .append(Text.literal(" set to ").formatted(WHITE))
                        .append(setting.getValueComponent()));
            })));
    }

}
