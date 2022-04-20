package net.azisaba.aziguard;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class AziGuardConfig {
    public static boolean whitelist = false;
    public static boolean logPackets = false;

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
                                "logPackets: false"
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
            logPackets = node.getNode("logPackets").getBoolean(false);
        } catch (IOException ignore) {
        }
    }
}
