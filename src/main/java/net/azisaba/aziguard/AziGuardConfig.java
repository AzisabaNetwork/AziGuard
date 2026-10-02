package net.azisaba.aziguard;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AziGuardConfig {
    public static volatile boolean whitelist = false;
    public static volatile boolean beta = false;
    public static String whitelistNode = "aziguard.bypass_whitelist";
    public static boolean logPackets = false;
    public static List<Integer> blockedProtocols = Collections.emptyList();
    public static String blockedProtocolMessage = "This version is not supported. Please use (insert version here).";

    public static synchronized void reload() {
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
                                "beta: false",
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
            ConfigurationNode node = YamlConfigurationLoader.builder().path(configPath).build().load();
            blockedProtocols = node.node("blocked-protocols").getList(Integer.class, Collections.emptyList());
            whitelist = node.node("whitelist").getBoolean(false);
            whitelistNode = node.node("whitelist-node").getString("aziguard.bypass_whitelist");
            beta = node.node("beta").getBoolean(false);
            logPackets = node.node("logPackets").getBoolean(false);
            blockedProtocolMessage = node.node("blocked-protocol-message").getString("This version is not supported. Please use (insert version here).");
        } catch (IOException ex) {
            AziGuard.instance.getLogger().warn("Failed to load config.yml", ex);
        }
    }

    public static synchronized void setEnabled(String key, boolean enabled) throws IOException {
        if (!key.equals("whitelist") && !key.equals("beta")) {
            throw new IllegalArgumentException("Unknown setting: " + key);
        }
        var loader = YamlConfigurationLoader.builder()
                .path(AziGuard.instance.getDataDirectory().resolve("config.yml")).build();
        ConfigurationNode node = loader.load();
        node.node(key).set(enabled);
        loader.save(node);
        if (key.equals("whitelist")) {
            whitelist = enabled;
        } else {
            beta = enabled;
        }
    }
}
