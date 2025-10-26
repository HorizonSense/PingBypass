package me.earth.pingbypass.api.command.components;

import lombok.RequiredArgsConstructor;
import me.earth.pingbypass.api.traits.HasDescription;
import me.earth.pingbypass.api.traits.Nameable;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.text.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.function.Function;

@RequiredArgsConstructor(staticName = "builder")
public class NameableComponent<T extends Nameable & HasDescription> {
    private final Iterable<T> nameables;
    private Function<T, Formatting> formatting = t -> Formatting.AQUA;
    private Function<T, @Nullable ClickEvent> clickEvent = t -> null;
    private String name;

    public NameableComponent<T> withFormatting(Function<T, Formatting> formatting) {
        this.formatting = formatting;
        return this;
    }

    public NameableComponent<T> withClickEvent(Function<T, ClickEvent> clickEvent) {
        this.clickEvent = clickEvent;
        return this;
    }

    public NameableComponent<T> suggestCommand(Function<T, String> command) {
        return this.withClickEvent(t -> new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command.apply(t)));
    }

    public NameableComponent<T> withName(String name) {
        this.name = name;
        return this;
    }

    public Text build() {
        MutableText root = Text.literal(name == null ? "" : (name + ": "));
        Iterator<T> itr = nameables.iterator();
        while (itr.hasNext()) {
            T t = itr.next();
            root.append(Text.literal(t.getName()).withStyle(style -> {
                Style result = style
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(t.getDescription())))
                    .withColor(formatting.apply(t));
                ClickEvent event = clickEvent.apply(t);
                if (event != null) {
                    result = result.withClickEvent(event);
                }

                return result;
            }));

            if (itr.hasNext()) {
                root.append(Text.literal(", ").withStyle(Formatting.WHITE));
            }
        }

        return root;
    }

}
