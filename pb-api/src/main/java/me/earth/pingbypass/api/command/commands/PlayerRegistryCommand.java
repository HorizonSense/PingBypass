package me.earth.pingbypass.api.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import me.earth.pingbypass.api.command.PBCommandSource;
import me.earth.pingbypass.api.command.impl.AbstractCommand;
import me.earth.pingbypass.api.command.impl.UsesExtendedBuilders;
import me.earth.pingbypass.api.command.impl.arguments.NameableArgumentTypeImpl;
import me.earth.pingbypass.api.command.impl.arguments.StringArgument;
import me.earth.pingbypass.api.command.impl.util.ComponentUtil;
import me.earth.pingbypass.api.players.PlayerInfo;
import me.earth.pingbypass.api.players.PlayerRegistry;
import me.earth.pingbypass.api.players.UUIDLookupService;
import me.earth.pingbypass.api.players.impl.MojangApiService;
import me.earth.pingbypass.api.players.impl.UUIDLookupServiceImpl;
import net.minecraft.util.Formatting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.function.Function;

import static net.minecraft.util.Formatting.*;

public class PlayerRegistryCommand extends AbstractCommand implements UsesExtendedBuilders {
    private final UUIDLookupService lookupService;
    private final PlayerRegistry registry;
    private final String verb;
    private final String as;
    private final String list;

    public PlayerRegistryCommand(String name, MinecraftClient mc, PlayerRegistry playerRegistry, String verb, String as,
                                 String list) {
        super(name, "(un)%s players.".formatted(name));
        // TODO: make UUIDLookupService part of the API?
        // TODO: give PingBypass a global ThreadExecutor?
        this.lookupService = new UUIDLookupServiceImpl(new MojangApiService(Executors.newSingleThreadExecutor()), mc);
        this.registry = playerRegistry;
        this.verb = verb;
        this.as = as;
        this.list = list;
    }

    @Override
    public void build(LiteralArgumentBuilder<PBCommandSource> builder) {
        builder.then(literal("add").then(arg("player", StringArgument.word("player"))
            .suggests((ctx, suggestionsBuilder) ->
                    SharedSuggestionProvider.suggest(ctx.getSource().getOnlinePlayerNames(), suggestionsBuilder))
            .executes(ctx -> {
                String name = ctx.getArgument("player", String.class);
                lookupService.getUuid(name).thenAccept(uuid -> ctx.getSource().getMinecraft().submit(() -> {
                    PlayerInfo playerInfo = new PlayerInfo(name, uuid);
                    registry.register(playerInfo);
                    ctx.getSource().getChat().send(
                            Text.literal("Added ").formatted(Formatting.GREEN)
                                    .append(ComponentUtil.getComponent(playerInfo, Formatting.AQUA))
                                    .append(Text.literal(" as ").formatted(Formatting.GREEN))
                                    .append(Text.literal(as + ".").formatted(Formatting.GREEN)));
                })).exceptionally(log(ctx));
        }))).then(literal("del").then(arg("player", new NameableArgumentTypeImpl<>(registry, "player"))
            .executes(ctx -> {
                PlayerInfo playerInfo = ctx.getArgument("player", PlayerInfo.class);
                registry.unregister(playerInfo);
                ctx.getSource().getChat().send(
                        Text.literal("Un").formatted(Formatting.RED)
                                .append(Text.literal(verb + " ").formatted(Formatting.RED))
                                .append(ComponentUtil.getComponent(playerInfo, Formatting.AQUA))
                                .append(Text.literal(".").formatted(Formatting.RED)));
        }))).then(literal("refresh").executes(ctx -> {
            ctx.getSource().getChat().send(
                    Text.literal("Checking the server for players that have changed their name recently."));
            ClientPlayNetworkHandler connection = ctx.getSource().getMinecraft().getConnection();
            if (connection != null) {
                for (net.minecraft.client.multiplayer.PlayerInfo info : connection.getOnlinePlayers()) {
                    Optional<PlayerInfo> playerInfo = registry.getByUUID(info.getProfile().getId());
                    if (playerInfo.isPresent() && !playerInfo.get().name().equals(info.getProfile().getName())) {
                        PlayerInfo newInfo = new PlayerInfo(info.getProfile().getName(), info.getProfile().getId());
                        registry.register(newInfo);
                        ctx.getSource().getChat().send(Text.literal("Player ")
                                .append(ComponentUtil.getComponent(newInfo, AQUA))
                                .append(Text.literal(" used to be named ").formatted(WHITE))
                                .append(ComponentUtil.getComponent(playerInfo.get(), AQUA))
                                .append(Text.literal(".").formatted(WHITE)));
                    }
                }
            }
        })).then(literal("list").executes(ctx -> {
            var component = Text.literal(list).append(Text.literal(": ").formatted(GRAY));
            Iterator<PlayerInfo> itr = registry.iterator();
            while (itr.hasNext()) {
                PlayerInfo playerInfo = itr.next();
                component.append(ComponentUtil.getComponent(playerInfo, AQUA));
                if (itr.hasNext()) {
                    component.append(Text.literal(", ").formatted(GRAY));
                }
            }

            ctx.getSource().getChat().send(component);
        }));
    }

    private Function<Throwable, Void> log(CommandContext<PBCommandSource> ctx) {
        return t -> {
            ctx.getSource().getMinecraft().submit(() ->
                ctx.getSource().getChat().send(Text.literal(t.getMessage()).formatted(ChatFormatting.RED)));
            return null;
        };
    }

}
