
package net.azisaba.aziguard.protocol;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Protocol_753 implements Protocol {
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
    SPacketGenerateStructure(0x0F),
    SPacketKeepAlive(0x10),
    SPacketLockDifficulty(0x11),
    SPacketPlayerPosition(0x12),
    SPacketPlayerPositionRot(0x13),
    SPacketPlayerRot(0x14),
    SPacketPlayerMovement(0x15),
    SPacketVehicleMove(0x16),
    SPacketSteerBoat(0x17),
    SPacketPickItem(0x18),
    SPacketCraftRecipeRequest(0x19),
    SPacketPlayerAbilities(0x1A),
    SPacketPlayerDigging(0x1B),
    SPacketEntityAction(0x1C),
    SPacketSteerVehicle(0x1D),
    SPacketSetRecipeBookState(0x1E),
    SPacketSetDisplayedRecipe(0x1F),
    SPacketNameItem(0x20),
    SPacketResourcePackStatus(0x21),
    SPacketAdvancementsTab(0x22),
    SPacketSelectTrade(0x23),
    SPacketSetBeaconEffect(0x24),
    SPacketHeldItemChange(0x25),
    SPacketUpdateCommandBlock(0x26),
    SPacketUpdateCommandBlockMinecart(0x27),
    SPacketCreativeInventoryAction(0x28),
    SPacketUpdateJigsawBlock(0x29),
    SPacketUpdateStructureBlock(0x2A),
    SPacketUpdateSign(0x2B),
    SPacketAnimation(0x2C),
    SPacketSpectate(0x2D),
    SPacketPlayerBlockPlacement(0x2E),
    SPacketUseItem(0x2F),
    ;

    private static final Map<Integer, Protocol_753> CLIENTBOUND = new ConcurrentHashMap<>();
    private static final Map<Integer, Protocol_753> SERVERBOUND = new ConcurrentHashMap<>();
    private final int id;

    Protocol_753(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public static Protocol_753 getCPacket(int id) {
        return CLIENTBOUND.get(id);
    }

    @Nullable
    public static Protocol_753 getSPacket(int id) {
        return SERVERBOUND.get(id);
    }

    static {
        for (Protocol_753 protocol : values()) {
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
