
package net.azisaba.aziguard.protocol;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Protocol_335 implements Protocol {
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
    SPacketPlayerMovement(0x0C),
    SPacketPlayerPosition(0x0D),
    SPacketPlayerPositionRot(0x0E),
    SPacketPlayerRot(0x0F),
    SPacketVehicleMove(0x10),
    SPacketSteerBoat(0x11),
    SPacketCraftRecipeRequest(0x12),
    SPacketPlayerAbilities(0x13),
    SPacketPlayerDigging(0x14),
    SPacketEntityAction(0x15),
    SPacketSteerVehicle(0x16),
    SPacketSetRecipeBookState(0x17),
    SPacketResourcePackStatus(0x18),
    SPacketAdvancementsTab(0x19),
    SPacketHeldItemChange(0x1A),
    SPacketCreativeInventoryAction(0x1B),
    SPacketUpdateSign(0x1C),
    SPacketAnimation(0x1D),
    SPacketSpectate(0x1E),
    SPacketPlayerBlockPlacement(0x1F),
    SPacketUseItem(0x20),
    ;

    private static final Map<Integer, Protocol_335> CLIENTBOUND = new ConcurrentHashMap<>();
    private static final Map<Integer, Protocol_335> SERVERBOUND = new ConcurrentHashMap<>();
    private final int id;

    Protocol_335(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public static Protocol_335 getCPacket(int id) {
        return CLIENTBOUND.get(id);
    }

    @Nullable
    public static Protocol_335 getSPacket(int id) {
        return SERVERBOUND.get(id);
    }

    static {
        for (Protocol_335 protocol : values()) {
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
