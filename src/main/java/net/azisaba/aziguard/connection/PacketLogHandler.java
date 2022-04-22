package net.azisaba.aziguard.connection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import net.azisaba.aziguard.AziGuard;
import net.azisaba.aziguard.AziGuardConfig;
import net.azisaba.aziguard.protocol.Protocol;
import net.azisaba.aziguard.util.ProtocolUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketLogHandler extends ChannelDuplexHandler {
    public static final Map<String, Long> PACKET_COUNT = new ConcurrentHashMap<>();

    private final int protocolVersion;

    public PacketLogHandler(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public void channelRead(@NotNull ChannelHandlerContext ctx, @NotNull Object msg) throws Exception {
        if (AziGuardConfig.logPackets) {
            try {
                String name = "Unmapped packet";
                if (msg instanceof ByteBuf buf) {
                    int idx = buf.readerIndex();
                    int packetId = ProtocolUtil.readVarIntSafely(buf);
                    buf.readerIndex(idx);
                    if (packetId != Integer.MIN_VALUE) {
                        Protocol protocol = Protocol.getSPacket(protocolVersion, packetId);
                        if (protocol != null) {
                            name = protocol.name();
                        }
                    }
                }
                if (PACKET_COUNT.containsKey(name)) {
                    PACKET_COUNT.put(name, PACKET_COUNT.get(name) + 1);
                } else {
                    PACKET_COUNT.put(name, 1L);
                }
            } catch (Exception | LinkageError e) {
                AziGuard.instance.getLogger().warn("Failed to count packet", e);
            }
        }
        super.channelRead(ctx, msg);
    }
}
