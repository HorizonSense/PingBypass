package me.earth.pingbypass.api.command.commands;

import me.earth.pingbypass.PingBypass;
import me.earth.pingbypass.api.command.Command;
import me.earth.pingbypass.api.command.PBCommandSource;
import org.w3c.dom.Text;

public final class HelpCommand extends AbstractHelpCommand<PBCommandSource> implements Command {
    private final PingBypass pingBypass;

    public HelpCommand(PingBypass pingBypass) {
        super(pingBypass.getCommandManager(), "help", "Displays available commands.");
        this.pingBypass = pingBypass;
    }

    @Override
    protected void print(PBCommandSource source, Text component) {
        pingBypass.getChat().send(component);
    }

}
