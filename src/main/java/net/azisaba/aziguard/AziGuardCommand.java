package net.azisaba.aziguard;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Field;

public final class AziGuardCommand {
    private AziGuardCommand() {
        throw new AssertionError();
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <T> @NotNull RequiredArgumentBuilder<CommandSource, T> argument(@NotNull String name, @NotNull ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull LiteralArgumentBuilder<CommandSource> literal(@NotNull String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    @Contract(value = "-> new", pure = true)
    public static @NotNull BrigadierCommand create() {
        return new BrigadierCommand(createBuilder());
    }

    @Contract(value = "-> new", pure = true)
    public static @NotNull LiteralArgumentBuilder<CommandSource> createBuilder() {
        return literal("aziguard")
                .requires(source -> source.hasPermission("aziguard.use_command"))
                .then(literal("reload")
                        .requires(source -> source.hasPermission("aziguard.command.reload"))
                        .executes(ctx -> executeReload(ctx.getSource()))
                )
                .then(toggle("whitelist"))
                .then(toggle("beta"));
    }

    private static LiteralArgumentBuilder<CommandSource> toggle(String key) {
        return literal(key)
                .requires(source -> source.hasPermission("aziguard.command." + key))
                .then(literal("on").executes(ctx -> executeToggle(ctx.getSource(), key, true)))
                .then(literal("off").executes(ctx -> executeToggle(ctx.getSource(), key, false)));
    }

    private static int executeToggle(CommandSource source, String key, boolean enabled) {
        try {
            AziGuardConfig.setEnabled(key, enabled);
            source.sendMessage(Component.text(key + " is now " + (enabled ? "on" : "off") + ".", NamedTextColor.GREEN));
            return 1;
        } catch (IOException ex) {
            AziGuard.instance.getLogger().warn("Failed to save " + key + " setting", ex);
            source.sendMessage(Component.text("Failed to save configuration.", NamedTextColor.RED));
            return 0;
        }
    }

    private static int executeReload(@NotNull CommandSource source) {
        AziGuardConfig.reload();
        source.sendMessage(Component.text("Reloaded AziGuard configuration.", NamedTextColor.GREEN));
        try {
            for (Field field : AziGuardConfig.class.getFields()) {
                source.sendMessage(Component.text("  " + field.getName() + " -> " + field.get(null)));
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
