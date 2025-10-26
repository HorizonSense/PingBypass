package me.earth.pingbypass.api.command.impl.util;

import lombok.experimental.UtilityClass;
import me.earth.pingbypass.api.traits.HasDescription;
import me.earth.pingbypass.api.traits.Nameable;
import net.minecraft.util.Formatting;
import net.minecraft.text.Text;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.text.MutableText;

@UtilityClass
public class ComponentUtil {
    public static <T extends Nameable & HasDescription> MutableText getComponent(T t, Formatting formatting) {
        return Text
                .literal(t.getName())
                .withStyle(style -> style
                        .withColor(formatting)
                        .withHoverEvent(new HoverEvent(
                                HoverEvent.Action.SHOW_TEXT, Text.literal(t.getDescription()))));
    }

    // TODO: make every Setting return its own component!!!!!
    public static Formatting getColor(Object value) {
        if (value instanceof Boolean bool) {
            return bool ? Formatting.GREEN : Formatting.RED;
        } else if (value instanceof Number) {
            return Formatting.AQUA;
        } else if (value instanceof String) {
            return Formatting.GOLD;
        }

        return Formatting.WHITE;
    }

    public static Text getSimpleValueComponent(Object value) {
        return Text.literal(String.valueOf(value))
                .withStyle(ComponentUtil.getColor(value));
    }

}
