
package net.azisaba.aziguard.protocol;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Protocol_107 implements Protocol {
    SPacketTeleportConfirm(0x00),
    SPacketTabComplete(0x01),
    SPacketChatMessage(0x02),
    SPacketClientStatus(0x03),
    SPacketClientSettings(0x04),
    SPacketWindowConfirm(0x05),
    SPacketEnchantItem(0x06),
    SPacketClickWindow(0x07),
    SPacketCloseWindow(0x08),
    SPacketPluginMessage(0x09),
    SPacketInteractEntity(0x0A),
    SPacketKeepAlive(0x0B),
    SPacketPlayerPosition(0x0C),
    SPacketPlayerPositionRot(0x0D),
    SPacketPlayerRot(0x0E),
    SPacketPlayerMovement(0x0F),
    SPacketVehicleMove(0x10),
    SPacketSteerBoat(0x11),
    SPacketPlayerAbilities(0x12),
    SPacketPlayerDigging(0x13),
    SPacketEntityAction(0x14),
    SPacketSteerVehicle(0x15),
    SPacketResourcePackStatus(0x16),
    SPacketHeldItemChange(0x17),
    SPacketCreativeInventoryAction(0x18),
    SPacketUpdateSign(0x19),
    SPacketAnimation(0x1A),
    SPacketSpectate(0x1B),
    SPacketPlayerBlockPlacement(0x1C),
    SPacketUseItem(0x1D),
    ;

    private static final Map<Integer, Protocol_107> CLIENTBOUND = new ConcurrentHashMap<>();
    private static final Map<Integer, Protocol_107> SERVERBOUND = new ConcurrentHashMap<>();
    private final int id;

    Protocol_107(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public static Protocol_107 getCPacket(int id) {
        return CLIENTBOUND.get(id);
    }

    @Nullable
    public static Protocol_107 getSPacket(int id) {
        return SERVERBOUND.get(id);
    }

    static {
        for (Protocol_107 protocol : values()) {
            if (protocol.name().startsWith("CPacket")) {
                CLIENTBOUND.put(protocol.getId(), protocol);
            } else if (protocol.name().startsWith("SPacket")) {
                SERVERBOUND.put(protocol.getId(), protocol);
            } else {
                throw new RuntimeException("Invalid protocol name: " + protocol.name());
            }
        }
    }
}
