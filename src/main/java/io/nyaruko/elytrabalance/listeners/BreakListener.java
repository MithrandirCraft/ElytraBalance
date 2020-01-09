package io.nyaruko.elytrabalance.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class BreakListener implements Listener {
    /**
     * Remove on break listener (removeElytraOnBreak)
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(PlayerItemDamageEvent event) {
        Player p = event.getPlayer();
        ItemStack is = p.getInventory().getChestplate();
        ItemMeta im;

        if(is == null || (im = is.getItemMeta()) == null) return;

        int durability = ((Damageable) im).getDamage();
        if(is.getType() == Material.ELYTRA && !p.hasPermission("elytrabalance.overrides.breakremoval") && durability >= 430) {
            ItemStack replacement = new ItemStack(Material.AIR);
            p.getInventory().setChestplate(replacement);
            event.setCancelled(true);
        }
    }
}
