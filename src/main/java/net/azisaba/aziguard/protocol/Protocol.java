package net.azisaba.aziguard.protocol;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public interface Protocol {
    //  protocol version  ------>  packet id  ------>  protocol
    Map<Integer, Function<@NotNull Integer, @Nullable Protocol>> CLIENTBOUND = new ConcurrentHashMap<>();
    Map<Integer, Function<@NotNull Integer, @Nullable Protocol>> SERVERBOUND = new ConcurrentHashMap<>();

    int getId();

    @NotNull
    String name();

    @Nullable
    static Protocol getCPacket(int pv, int id) {
        Function<Integer, Protocol> getter = CLIENTBOUND.get(pv);
        if (getter == null) {
            return null;
        }
        return getter.apply(id);
    }

    @Nullable
    static Protocol getSPacket(int pv, int id) {
        Function<Integer, Protocol> getter = SERVERBOUND.get(pv);
        if (getter == null) {
            return null;
        }
        return getter.apply(id);
    }

    static void registerProtocol(int pv, @NotNull Function<@NotNull Integer, @Nullable Protocol> cb, @NotNull Function<@NotNull Integer, @Nullable Protocol> sb) {
        if (CLIENTBOUND.containsKey(pv) || SERVERBOUND.containsKey(pv)) {
            throw new IllegalArgumentException("Protocol version " + pv + " is already registered");
        }
        CLIENTBOUND.put(pv, cb);
        SERVERBOUND.put(pv, sb);
    }

    static void initProtocols() {
        Protocol.registerProtocol(758, Protocol_758::getCPacket, Protocol_758::getSPacket); // 1.18.2
        Protocol.registerProtocol(757, Protocol_757::getCPacket, Protocol_757::getSPacket); // 1.18.0/1
        Protocol.registerProtocol(756, Protocol_756::getCPacket, Protocol_756::getSPacket); // 1.17.1
        Protocol.registerProtocol(755, Protocol_755::getCPacket, Protocol_755::getSPacket); // 1.17
        Protocol.registerProtocol(754, Protocol_754::getCPacket, Protocol_754::getSPacket); // 1.16.4/5
        Protocol.registerProtocol(753, Protocol_753::getCPacket, Protocol_753::getSPacket); // 1.16.3
        Protocol.registerProtocol(578, Protocol_578::getCPacket, Protocol_578::getSPacket); // 1.15.2
        Protocol.registerProtocol(498, Protocol_498::getCPacket, Protocol_498::getSPacket); // 1.14.4
        Protocol.registerProtocol(404, Protocol_404::getCPacket, Protocol_404::getSPacket); // 1.13.2
        Protocol.registerProtocol(401, Protocol_401::getCPacket, Protocol_401::getSPacket); // 1.13.1
        Protocol.registerProtocol(340, Protocol_340::getCPacket, Protocol_340::getSPacket); // 1.12.2
        Protocol.registerProtocol(336, Protocol_336::getCPacket, Protocol_336::getSPacket); // 1.12.1
        Protocol.registerProtocol(335, Protocol_335::getCPacket, Protocol_335::getSPacket); // 1.12
        Protocol.registerProtocol(316, Protocol_316::getCPacket, Protocol_316::getSPacket); // 1.11.1/2
        Protocol.registerProtocol(315, Protocol_315::getCPacket, Protocol_315::getSPacket); // 1.11
        Protocol.registerProtocol(210, Protocol_210::getCPacket, Protocol_210::getSPacket); // 1.10.0/1/2
        Protocol.registerProtocol(110, Protocol_110::getCPacket, Protocol_110::getSPacket); // 1.9.3/4
        Protocol.registerProtocol(109, Protocol_109::getCPacket, Protocol_109::getSPacket); // 1.9.2
        Protocol.registerProtocol(108, Protocol_108::getCPacket, Protocol_108::getSPacket); // 1.9.1
        Protocol.registerProtocol(107, Protocol_107::getCPacket, Protocol_107::getSPacket); // 1.9
        Protocol.registerProtocol(47, Protocol_47::getCPacket, Protocol_47::getSPacket); // 1.8-1.8.9
        Protocol.registerProtocol(5, Protocol_5::getCPacket, Protocol_5::getSPacket); // 1.7.6-1.7.10
        Protocol.registerProtocol(4, Protocol_4::getCPacket, Protocol_4::getSPacket); // 1.7.2-1.7.5
    }
}
