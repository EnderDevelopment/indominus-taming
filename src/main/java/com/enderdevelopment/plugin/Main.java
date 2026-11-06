package com.enderdevelopment.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Ravager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class Main extends JavaPlugin {
    NamespacedKey rexKey;
    NamespacedKey ownerKey;
    NamespacedKey saddleKey;

    @Override
    public void onEnable() {
        rexKey = new NamespacedKey(this, "indominus_rex");
        ownerKey = new NamespacedKey(this, "rex_owner");
        saddleKey = new NamespacedKey(this, "dino_saddle");
        Bukkit.getPluginManager().registerEvents(new RexListener(this), this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("indominus")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("This command can only be used by a player.");
                return true;
            }
            if (!player.hasPermission("indominus.spawn")) {
                player.sendMessage(Component.text("You do not have permission.", NamedTextColor.RED));
                return true;
            }
            Ravager rex = (Ravager) player.getWorld().spawnEntity(player.getLocation(), EntityType.RAVAGER);
            rex.customName(Component.text("Indominus Rex", NamedTextColor.DARK_RED));
            rex.setCustomNameVisible(true);
            rex.getPersistentDataContainer().set(rexKey, PersistentDataType.BYTE, (byte) 1);
            if (rex.getAttribute(Attribute.MAX_HEALTH) != null) {
                rex.getAttribute(Attribute.MAX_HEALTH).setBaseValue(100.0);
                rex.setHealth(100.0);
            }
            player.sendMessage(Component.text("An Indominus Rex has been spawned. Tame it with raw beef.", NamedTextColor.GREEN));
            return true;
        }
        if (command.getName().equalsIgnoreCase("dinosaddle")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("This command can only be used by a player.");
                return true;
            }
            if (!player.hasPermission("indominus.saddle")) {
                player.sendMessage(Component.text("You do not have permission.", NamedTextColor.RED));
                return true;
            }
            ItemStack saddle = new ItemStack(Material.SADDLE);
            ItemMeta meta = saddle.getItemMeta();
            meta.displayName(Component.text("Dino Saddle", NamedTextColor.GOLD));
            meta.getPersistentDataContainer().set(saddleKey, PersistentDataType.BYTE, (byte) 1);
            saddle.setItemMeta(meta);
            player.getInventory().addItem(saddle).values().forEach(item -> player.getWorld().dropItemNaturally(player.getLocation(), item));
            player.sendMessage(Component.text("Dino saddle received. Right-click your tamed Indominus Rex with it.", NamedTextColor.GREEN));
            return true;
        }
        return false;
    }
}
