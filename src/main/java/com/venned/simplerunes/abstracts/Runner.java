package com.venned.simplerunes.abstracts;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public abstract class Runner {

    protected String name;
    protected String description;
    protected NamespacedKey runnerKey;
    protected ItemStack item;

    public Runner(String name, NamespacedKey runnerKey, ItemStack item, String description) {
        this.name = name;

        this.runnerKey = runnerKey;
        this.item = item;
        this.description = description;
    }

    public Boolean StatusRunner(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(runnerKey, PersistentDataType.BOOLEAN)) {
            return container.get(runnerKey, PersistentDataType.BOOLEAN);
        }
        return null;
    }

    public ItemStack createItem() {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§d Rune " +name);
        meta.getPersistentDataContainer().set(runnerKey,
                PersistentDataType.BOOLEAN, false);
        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§7" + description);
        lore.add(" ");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createItemGUI(){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§d Rune " +name);
        meta.getPersistentDataContainer().set(runnerKey,
                PersistentDataType.BOOLEAN, true);
        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§7" + description);
        lore.add(" ");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getItem() {
        return item;
    }
    public String getName() {
        return name;
    }
    public NamespacedKey getRunnerKey() {
        return runnerKey;
    }
}
