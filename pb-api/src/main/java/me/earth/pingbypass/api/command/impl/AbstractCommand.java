package me.earth.pingbypass.api.command.impl;

import me.earth.pingbypass.api.command.Command;
import me.earth.pingbypass.api.command.PBCommandSource;

public abstract class AbstractCommand extends AbstractGenericCommand<PBCommandSource> implements Command {
    public AbstractCommand(String name, String description) {
        super(name, description);
    }

}
