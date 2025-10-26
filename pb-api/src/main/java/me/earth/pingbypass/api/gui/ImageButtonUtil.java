package me.earth.pingbypass.api.gui;

import lombok.experimental.UtilityClass;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@UtilityClass
public class ImageButtonUtil {
    private static final Identifier ICON_BUTTON = Identifier.of("pingbypass", "icon/icon_button");
    private static final Identifier ICON_BUTTON_FOCUSED = Identifier.of("pingbypass", "icon/icon_button_focused");

    public static TexturedButtonWidget getIconButton(int x, int y, ButtonWidget callback) {
        return new TexturedButtonWidget(x, y, 20, 20, new TexturedButtonWidget(ICON_BUTTON, ICON_BUTTON_FOCUSED), callback, Text.literal("PingBypassCommandsScreen"));
    }

}
