package me.earth.pingbypass.api.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import lombok.experimental.UtilityClass;
import net.minecraft.command.CommandSource;

@UtilityClass
public class ParseResultUtil {
    @SuppressWarnings("rawtypes")
    private static final CommandDispatcher DUMMY = new CommandDispatcher();

    @SuppressWarnings("unchecked")
    public static <S> ParseResults<S> dummy(S source) {
        return DUMMY.parse("", source);
    }

    public static ParseResults<CommandSource> dummy() {
        return dummy((CommandSource) EmptySuggestionProvider.INSTANCE);
    }

}
