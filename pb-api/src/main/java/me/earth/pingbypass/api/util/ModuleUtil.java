package me.earth.pingbypass.api.util;

import lombok.experimental.UtilityClass;
import me.earth.pingbypass.api.command.Chat;
import me.earth.pingbypass.api.module.Module;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@UtilityClass
public class ModuleUtil {
    public static void sendModuleToggleMessage(Chat chat, Module module, boolean sendBeforeActuallyToggled) {
        boolean willBeEnabled = sendBeforeActuallyToggled != module.isEnabled();
        String message = willBeEnabled ? " enabled." : " disabled.";
        Formatting formatting = willBeEnabled ? Formatting.GREEN : Formatting.RED;
        chat.sendWithoutLogging(Text.literal("")
                .append(Text.literal(module.getName()).formatted(Formatting.BOLD))
                .append(Text.literal(message).formatted(formatting)), module.getName());
    }

}
