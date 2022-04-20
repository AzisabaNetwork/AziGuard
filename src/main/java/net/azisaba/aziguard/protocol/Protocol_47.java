
package net.azisaba.aziguard.protocol;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Protocol_47 implements Protocol {
    SPacketKeepAlive(0x00), // Keep Alive
    SPacketChatMessage(0x01), // Chat Message
    SPacketInteractEntity(0x02), // Use Entity
    SPacketPlayerMovement(0x03), // Player
    SPacketPlayerPosition(0x04), // Player Position
    SPacketPlayerRot(0x05), // Player Look
    SPacketPlayerPositionRot(0x06), // Player Position And Look
    SPacketPlayerDigging(0x07), // Player Digging
    SPacketPlayerBlockPlacement(0x08), // Player Block Placement
    SPacketHeldItemChange(0x09), // Held Item Change
    SPacketAnimation(0x0A), // Animation
    SPacketEntityAction(0x0B), // Entity Action
    SPacketSteerVehicle(0x0C), // Steer Vehicle
    SPacketCloseWindow(0x0D), // Close Window
    SPacketClickWindow(0x0E), // Click Window
    SPacketWindowConfirm(0x0F), // Confirm Transaction
    SPacketCreativeInventoryAction(0x10), // Creative Inventory Action
    SPacketEnchantItem(0x11), // Enchant Item
    SPacketUpdateSign(0x12), // Update Sign
    SPacketPlayerAbilities(0x13), // Player Abilities
    SPacketTabComplete(0x14), // Tab-Complete
    SPacketClientSettings(0x15), // Client Settings
    SPacketClientStatus(0x16), // Client Status
    SPacketPluginMessage(0x17), // Plugin Message
    SPacketSpectate(0x18), // Spectate
    SPacketResourcePackStatus(0x19), // Resource Pack Status
    ;

    private static final Map<Integer, Protocol_47> CLIENTBOUND = new ConcurrentHashMap<>();
    private static final Map<Integer, Protocol_47> SERVERBOUND = new ConcurrentHashMap<>();
    private final int id;

    Protocol_47(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public static Protocol_47 getCPacket(int id) {
        return CLIENTBOUND.get(id);
    }

    @Nullable
    public static Protocol_47 getSPacket(int id) {
        return SERVERBOUND.get(id);
    }

    static {
        for (Protocol_47 protocol : values()) {
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
