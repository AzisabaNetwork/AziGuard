package net.azisaba.aziguard.util;

import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

public class ProtocolUtil {
    public static int readVarIntSafely(@NotNull ByteBuf buf) {
        int i = 0;
        int maxRead = Math.min(5, buf.readableBytes());
        for (int j = 0; j < maxRead; j++) {
            int k = buf.readByte();
            i |= (k & 0x7F) << j * 7;
            if ((k & 0x80) != 128) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }
}
