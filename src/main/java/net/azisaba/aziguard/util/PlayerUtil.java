package net.azisaba.aziguard.util;

import com.velocitypowered.api.proxy.Player;
import io.netty.channel.Channel;
import org.jetbrains.annotations.NotNull;

public class PlayerUtil {
    @NotNull
    public static Channel getChannel(@NotNull Player player) {
        try {
            Object minecraftConnection = player.getClass().getMethod("getConnection").invoke(player);
            return (Channel) minecraftConnection.getClass().getMethod("getChannel").invoke(minecraftConnection);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
