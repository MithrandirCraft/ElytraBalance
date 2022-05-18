package io.nyaruko.elytrabalance.listeners;

import io.nyaruko.elytrabalance.ElytraBalance;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BoostListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBoost(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = event.getItem();
        if (!player.isGliding() || heldItem == null || event.getAction() != Action.RIGHT_CLICK_AIR || heldItem.getType() != Material.FIREWORK_ROCKET)
            return;
        if (!player.hasPermission("elytrabalance.overrides.boostplayerdamage"))
            if (((FireworkMeta)heldItem.getItemMeta()).hasEffects()) {
                player.damage((ElytraBalance.getConfigModel()).additionalDamagePerStarRocketUse);
            } else if ((ElytraBalance.getConfigModel()).playerDamageOnNoStarRocketUse) {
                player.damage((ElytraBalance.getConfigModel()).damagePerNoStarRocketUse);
            }
        if (!player.hasPermission("elytrabalance.overrides.itemdamage")) {
            ItemStack elytra = player.getInventory().getChestplate();
            if (elytra != null) {
                Damageable elytraMeta = (Damageable)elytra.getItemMeta();
                if (elytraMeta != null) {
                    int durability = elytraMeta.getDamage() + (ElytraBalance.getConfigModel()).elytraDamageOnRocketUse;
                    durability = Math.min(durability, elytra.getType().getMaxDurability());
                    elytraMeta.setDamage(durability);
                    elytra.setItemMeta((ItemMeta)elytraMeta);
                    Bukkit.getServer().getPluginManager().callEvent((Event)new PlayerItemDamageEvent(player, elytra, (ElytraBalance.getConfigModel()).elytraDamageOnRocketUse));
                }
            }
        }
    }

    @EventHandler
    public void onRipTide(PlayerRiptideEvent event) {
        Player player = event.getPlayer();
        ItemStack elytra = player.getInventory().getChestplate();
        if (elytra != null && elytra.getType() == Material.ELYTRA && !player.hasPermission("elytrabalance.overrides.riptide")) {
            if ((ElytraBalance.getConfigModel()).elytraDamageOnRiptideUseWithElytra > 0) {
                Damageable elytraMeta = (Damageable)elytra.getItemMeta();
                if (elytraMeta != null) {
                    int durability = elytraMeta.getDamage() + (ElytraBalance.getConfigModel()).elytraDamageOnRiptideUseWithElytra;
                    durability = Math.min(durability, elytra.getType().getMaxDurability());
                    elytraMeta.setDamage(durability);
                    elytra.setItemMeta((ItemMeta)elytraMeta);
                    Bukkit.getServer().getPluginManager().callEvent((Event)new PlayerItemDamageEvent(player, elytra, (ElytraBalance.getConfigModel()).elytraDamageOnRiptideUseWithElytra));
                    String elytraDamagedMSG = (ElytraBalance.getConfigModel()).riptideDamagedElytraMessage;
                    if (!elytraDamagedMSG.isEmpty())
                        ElytraBalance.sendConfigMessage(player, elytraDamagedMSG);
                }
            }
            if ((ElytraBalance.getConfigModel()).tridentDamageOnRiptideUseWithElytra > 0) {
                ItemStack trident = event.getItem();
                Damageable tridentMeta = (Damageable)trident.getItemMeta();
                if (tridentMeta != null) {
                    int durability = tridentMeta.getDamage() + (ElytraBalance.getConfigModel()).tridentDamageOnRiptideUseWithElytra;
                    durability = Math.min(durability, trident.getType().getMaxDurability());
                    tridentMeta.setDamage(durability);
                    trident.setItemMeta((ItemMeta)tridentMeta);
                    Bukkit.getServer().getPluginManager().callEvent((Event)new PlayerItemDamageEvent(player, trident, (ElytraBalance.getConfigModel()).tridentDamageOnRiptideUseWithElytra));
                    String tridentDamageMSG = (ElytraBalance.getConfigModel()).riptideDamagedTrident;
                    if (!tridentDamageMSG.isEmpty())
                        ElytraBalance.sendConfigMessage(player, tridentDamageMSG);
                }
            }
        }
    }
}
