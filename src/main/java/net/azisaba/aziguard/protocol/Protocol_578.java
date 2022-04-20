
package net.azisaba.aziguard.protocol;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Protocol_578 implements Protocol {
    SPacketTeleportConfirm(0x00),
    SPacketQueryBlockNBT(0x01),
    SPacketSetDifficulty(0x02),
    SPacketChatMessage(0x03),
    SPacketClientStatus(0x04),
    SPacketClientSettings(0x05),
    SPacketTabComplete(0x06),
    SPacketWindowConfirm(0x07),
    SPacketClickWindowButton(0x08),
    SPacketClickWindow(0x09),
    SPacketCloseWindow(0x0A),
    SPacketPluginMessage(0x0B),
    SPacketEditBook(0x0C),
    SPacketQueryEntityNBT(0x0D),
    SPacketInteractEntity(0x0E),
    SPacketKeepAlive(0x0F),
    SPacketLockDifficulty(0x10),
    SPacketPlayerPosition(0x11),
    SPacketPlayerPositionRot(0x12),
    SPacketPlayerRot(0x13),
    SPacketPlayerMovement(0x14),
    SPacketVehicleMove(0x15),
    SPacketSteerBoat(0x16),
    SPacketPickItem(0x17),
    SPacketCraftRecipeRequest(0x18),
    SPacketPlayerAbilities(0x19),
    SPacketPlayerDigging(0x1A),
    SPacketEntityAction(0x1B),
    SPacketSteerVehicle(0x1C),
    SPacketSetRecipeBookState(0x1D),
    SPacketNameItem(0x1E),
    SPacketResourcePackStatus(0x1F),
    SPacketAdvancementsTab(0x20),
    SPacketSelectTrade(0x21),
    SPacketSetBeaconEffect(0x22),
    SPacketHeldItemChange(0x23),
    SPacketUpdateCommandBlock(0x24),
    SPacketUpdateCommandBlockMinecart(0x25),
    SPacketCreativeInventoryAction(0x26),
    SPacketUpdateJigsawBlock(0x27),
    SPacketUpdateStructureBlock(0x28),
    SPacketUpdateSign(0x29),
    SPacketAnimation(0x2A),
    SPacketSpectate(0x2B),
    SPacketPlayerBlockPlacement(0x2C),
    SPacketUseItem(0x2D),
    ;

    private static final Map<Integer, Protocol_578> CLIENTBOUND = new ConcurrentHashMap<>();
    private static final Map<Integer, Protocol_578> SERVERBOUND = new ConcurrentHashMap<>();
    private final int id;

    Protocol_578(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public static Protocol_578 getCPacket(int id) {
        return CLIENTBOUND.get(id);
    }

    @Nullable
    public static Protocol_578 getSPacket(int id) {
        return SERVERBOUND.get(id);
    }

    static {
        for (Protocol_578 protocol : values()) {
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
