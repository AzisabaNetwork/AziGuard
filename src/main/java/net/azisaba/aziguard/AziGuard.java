package net.azisaba.aziguard;

import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.plugin.Plugin;
import net.kyori.adventure.text.Component;

@Plugin(id = "aziguard", name = "AziGuard", version = "1.0", authors = "Azisaba Network")
public class AziGuard {
    @Subscribe
    public void onLogin(LoginEvent e) {
        if (e.getPlayer().hasPermission("aziguard.login")) {
            return;
        }
        e.setResult(ResultedEvent.ComponentResult.denied(Component.text("You are not whitelisted in this server!")));
    }
}
