package me.earth.pingbypass.api.command.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.earth.pingbypass.PingBypass;
import me.earth.pingbypass.api.command.PBCommandSource;
import me.earth.pingbypass.api.command.impl.arguments.CategoryArgument;
import me.earth.pingbypass.api.command.impl.util.ComponentUtil;
import me.earth.pingbypass.api.module.Category;
import me.earth.pingbypass.api.module.ModuleManager;
import me.earth.pingbypass.api.command.AbstractPbCommand;
import me.earth.pingbypass.api.command.components.NameableComponent;
import net.minecraft.util.Formatting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;

public class ModulesCommand extends AbstractPbCommand {
    public ModulesCommand(PingBypass pingBypass, MinecraftClient mc) {
        super("modules", "Lists all modules currently registered in the client.", pingBypass, mc);
    }

    @Override
    public void build(LiteralArgumentBuilder<PBCommandSource> builder) {
        ModuleManager modules = pingBypass.getModuleManager();
        builder.executes(ctx -> {
            print(NameableComponent
                    .builder(modules)
                    .withName("Modules")
                    .withFormatting(module -> module.isEnabled() ? Formatting.GREEN : Formatting.RED)
                    .withClickEvent(module -> new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                            pingBypass.getCommandManager().getPrefix() + "toggle " + module.getName()))
                    .build());

            return Command.SINGLE_SUCCESS;
        }).then(arg("category", CategoryArgument.of(modules.getCategoryManager())).executes(ctx -> {
            Category category = ctx.getArgument("category", Category.class);
            print(Text.literal("Category ")
                .append(Text.literal(category.getName()).withStyle(style ->
                        style.withColor(category.getColor()).applyFormats(Formatting.BOLD)))
                .append(Text.literal(":").withStyle(Formatting.WHITE)));

            pingBypass.getModuleManager().getModulesByCategory(category).forEach(module ->
                    print(ComponentUtil.getComponent(module, Formatting.AQUA)));
        }));
    }

}
