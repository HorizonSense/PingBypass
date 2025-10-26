package me.earth.pingbypass.api.command.impl;

import com.mojang.brigadier.context.CommandContext;
import me.earth.pingbypass.api.command.PBCommandSource;
import net.minecraft.text.Text;

public interface PrintsInContextChat {
    default void print(CommandContext<PBCommandSource> context, Text message) {
        context.getSource().getChat().send(message);
    }

}
