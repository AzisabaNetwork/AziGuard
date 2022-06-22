package net.azisaba.aziguard;

import com.google.inject.Inject;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.azisaba.aziguard.connection.PacketLogHandler;
import net.azisaba.aziguard.protocol.Protocol;
import net.azisaba.aziguard.util.PlayerUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Plugin(id = "aziguard", name = "AziGuard", version = "1.0", authors = "Azisaba Network")
public class AziGuard {
    public static AziGuard instance;
    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    @Inject
    public AziGuard(@NotNull ProxyServer server, @NotNull Logger logger, @NotNull @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        instance = this;
        AziGuardConfig.reload();
        Protocol.initProtocols();
    }

    @NotNull
    public Logger getLogger() {
        return logger;
    }

    @NotNull
    public Path getDataDirectory() {
        return dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent e) {
        server.getCommandManager().register(AziGuardCommand.create());
        server.getScheduler().buildTask(this, () -> {
            if (AziGuardConfig.logPackets) {
                List<Map.Entry<String, Long>> list = new ArrayList<>(PacketLogHandler.PACKET_COUNT.entrySet());
                PacketLogHandler.PACKET_COUNT.clear();
                list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
                long total = list.stream().map(Map.Entry::getValue).reduce(0L, Long::sum);
                logger.info("Received packets in last minute: " + total);
                for (int i = 0; i < list.size(); i++) {
                    if (i < 10) {
                        logger.info("- {}: {}", list.get(i).getKey(), list.get(i).getValue());
                    }
                }
            }
        }).delay(1, TimeUnit.MINUTES).repeat(1, TimeUnit.MINUTES).schedule();
    }

    @Subscribe
    public void onPreLogin(PreLoginEvent e) {
        if (AziGuardConfig.blockProtocols.contains(e.getConnection().getProtocolVersion().getProtocol())) {
            // blocked protocol version
            var message = LegacyComponentSerializer.legacyAmpersand().deserialize(AziGuardConfig.blockedProtocolMessage);
            e.setResult(PreLoginEvent.PreLoginComponentResult.denied(message));
            //return;
        }
    }

    @Subscribe
    public void onLogin(LoginEvent e) {
        if (AziGuardConfig.whitelist) {
            if (!e.getPlayer().hasPermission(AziGuardConfig.whitelistNode)) {
                e.setResult(ResultedEvent.ComponentResult.denied(Component.text("You are not whitelisted in this server!")));
            }
        }
        try {
            PlayerUtil.getChannel(e.getPlayer())
                    .pipeline()
                    .addBefore("minecraft-decoder", "packet_logger", new PacketLogHandler(e.getPlayer().getProtocolVersion().getProtocol()));
        } catch (Exception ex) {
            logger.warn("Failed to inject packet log handler for " + e.getPlayer().getUsername(), ex);
        }
    }
}
