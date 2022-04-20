
package net.azisaba.aziguard.protocol;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Protocol_404 implements Protocol {
    SPacketTeleportConfirm(0x00),
    SPacketQueryBlockNBT(0x01),
    SPacketChatMessage(0x02),
    SPacketClientStatus(0x03),
    SPacketClientSettings(0x04),
    SPacketTabComplete(0x05),
    SPacketWindowConfirm(0x06),
    SPacketEnchantItem(0x07),
    SPacketClickWindow(0x08),
    SPacketCloseWindow(0x09),
    SPacketPluginMessage(0x0A),
    SPacketEditBook(0x0B),
    SPacketQueryEntityNBT(0x0C),
    SPacketInteractEntity(0x0d),
    SPacketKeepAlive(0x0E),
    SPacketPlayerMovement(0x0F),
    SPacketPlayerPosition(0x10),
    SPacketPlayerPositionRot(0x11),
    SPacketPlayerRot(0x12),
    SPacketVehicleMove(0x13),
    SPacketSteerBoat(0x14),
    SPacketPickItem(0x15),
    SPacketCraftRecipeRequest(0x16),
    SPacketPlayerAbilities(0x17),
    SPacketPlayerDigging(0x18),
    SPacketEntityAction(0x19),
    SPacketSteerVehicle(0x1A),
    SPacketSetRecipeBookState(0x1B),
    SPacketNameItem(0x1C),
    SPacketResourcePackStatus(0x1D),
    SPacketAdvancementsTab(0x1E),
    SPacketSelectTrade(0x1F),
    SPacketSetBeaconEffect(0x20),
    SPacketHeldItemChange(0x21),
    SPacketUpdateCommandBlock(0x22),
    SPacketUpdateCommandBlockMinecart(0x23),
    SPacketCreativeInventoryAction(0x24),
    SPacketUpdateStructureBlock(0x25),
    SPacketUpdateSign(0x26),
    SPacketAnimation(0x27),
    SPacketSpectate(0x28),
    SPacketPlayerBlockPlacement(0x29),
    SPacketUseItem(0x2A),
    ;

    private static final Map<Integer, Protocol_404> CLIENTBOUND = new ConcurrentHashMap<>();
    private static final Map<Integer, Protocol_404> SERVERBOUND = new ConcurrentHashMap<>();
    private final int id;

    Protocol_404(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public static Protocol_404 getCPacket(int id) {
        return CLIENTBOUND.get(id);
    }

    @Nullable
    public static Protocol_404 getSPacket(int id) {
        return SERVERBOUND.get(id);
    }

    static {
        for (Protocol_404 protocol : values()) {
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
