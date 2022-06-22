package net.azisaba.aziguard;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AziGuardConfig {
    public static boolean whitelist = false;
    public static String whitelistNode = "aziguard.bypass_whitelist";
    public static boolean logPackets = false;
    public static List<Integer> blockedProtocols = Collections.emptyList();
    public static String blockedProtocolMessage = "This version is not supported. Please use (insert version here).";

    @SuppressWarnings("UnstableApiUsage")
    public static void reload() {
        whitelist = false;
        logPackets = false;
        Path configPath = AziGuard.instance.getDataDirectory().resolve("config.yml");
        if (!Files.exists(configPath)) {
            try {
                if (!Files.exists(AziGuard.instance.getDataDirectory())) {
                    Files.createDirectory(AziGuard.instance.getDataDirectory());
                }
                Files.write(
                        configPath,
                        Arrays.asList(
                                "whitelist: false",
                                "whitelist-node: aziguard.bypass_whitelist",
                                "logPackets: false",
                                "blocked-protocols: []",
                                "blocked-protocol-message: \"This version is not supported. Please use (insert version here).\""
                        ),
                        StandardOpenOption.CREATE
                );
            } catch (IOException ex) {
                AziGuard.instance.getLogger().warn("Failed to write config.yml", ex);
            }
        }
        try {
            ConfigurationNode node = YAMLConfigurationLoader.builder().setPath(configPath).build().load();
            whitelist = node.getNode("whitelist").getBoolean(false);
            whitelistNode = node.getNode("whitelist-node").getString("aziguard.bypass_whitelist");
            logPackets = node.getNode("logPackets").getBoolean(false);
            try {
                blockedProtocols = node.getNode("blocked-protocols").getList(TypeToken.of(Integer.class));
            } catch (ObjectMappingException e) {
                throw new RuntimeException("Failed to load block-protocols", e);
            }
            blockedProtocolMessage = node.getNode("blocked-protocol-message").getString("This version is not supported. Please use (insert version here).");
        } catch (IOException ignore) {
        }
    }
}
