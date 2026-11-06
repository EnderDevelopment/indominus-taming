package com.enderdevelopment.plugin;

import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Ravager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

final class RexListener implements Listener {
    private final Main plugin;

    RexListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Entity target = event.getRightClicked();
        if (!(target instanceof Ravager rex) || !rex.getPersistentDataContainer().has(plugin.rexKey, PersistentDataType.BYTE)) {
            return;
        }
        Player player = event.getPlayer();
        var data = rex.getPersistentDataContainer();
        String owner = data.get(plugin.ownerKey, PersistentDataType.STRING);
        ItemStack held = player.getInventory().getItem(event.getHand());
        boolean hasSaddle = data.has(plugin.saddleKey, PersistentDataType.BYTE);

        if (owner == null) {
            if (held.getType() == Material.BEEF || held.getType() == Material.COOKED_BEEF) {
                event.setCancelled(true);
                if (Math.random() < 0.35) {
                    data.set(plugin.ownerKey, PersistentDataType.STRING, player.getUniqueId().toString());
                    rex.setTarget(null);
                    rex.setPersistent(false);
                    player.sendMessage(Component.text("You tamed the Indominus Rex! Equip it with a Dino Saddle to ride.", NamedTextColor.GREEN));
                } else {
                    if (player.getGameMode() != org.bukkit.GameMode.CREATIVE) held.setAmount(held.getAmount() - 1);
                    player.sendMessage(Component.text("The Indominus Rex is still wild. Keep feeding it raw beef.", NamedTextColor.YELLOW));
                }
                return;
            }
            return;
        }
        if (!owner.equals(player.getUniqueId().toString())) {
            event.setCancelled(true);
            player.sendMessage(Component.text("This Indominus Rex belongs to another player.", NamedTextColor.RED));
            return;
        }
        if (!hasSaddle && isDinoSaddle(held)) {
            event.setCancelled(true);
            held.setAmount(held.getAmount() - 1);
            data.set(plugin.saddleKey, PersistentDataType.BYTE, (byte) 1);
            player.sendMessage(Component.text("Dino Saddle equipped. Right-click again to ride.", NamedTextColor.GREEN));
            return;
        }
        if (hasSaddle) {
            event.setCancelled(true);
            if (rex.getPassengers().isEmpty() || rex.getPassengers().contains(player)) {
                rex.addPassenger(player);
            } else {
                player.sendMessage(Component.text("The Indominus Rex already has a rider.", NamedTextColor.YELLOW));
            }
        }
    }

    private boolean isDinoSaddle(ItemStack item) {
        if (item == null || item.getType() != Material.SADDLE || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(plugin.saddleKey, PersistentDataType.BYTE);
    }
}
