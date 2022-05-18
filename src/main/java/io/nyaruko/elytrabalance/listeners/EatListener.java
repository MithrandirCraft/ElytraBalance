package io.nyaruko.elytrabalance.listeners;

import io.nyaruko.elytrabalance.ElytraBalance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class EatListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEat(PlayerItemConsumeEvent event) {
        Player p = event.getPlayer();
        if (p.isGliding() && !p.isSwimming() && !p.hasPermission("elytrabalance.overrides.eat")) {
            event.setCancelled(true);
            String consumableBlocked = (ElytraBalance.getConfigModel()).consumableBlockedMessage;
            if (!consumableBlocked.isEmpty())
                ElytraBalance.sendConfigMessage(p, consumableBlocked);
        }
    }
}
