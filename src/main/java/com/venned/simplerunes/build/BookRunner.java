package com.venned.simplerunes.build;

import com.venned.simplerunes.abstracts.Runner;
import com.venned.simplerunes.utils.PDHUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class BookRunner {

    protected List<Runner> runners;
    protected NamespacedKey namespacedKey;
    protected ItemStack item;

    public BookRunner(NamespacedKey namespacedKey, ItemStack item) {
        this.runners = new ArrayList<>();
        this.namespacedKey = namespacedKey;
        this.item = item;
    }

    public List<Runner> getRunners() {
        return runners;
    }

    public void addList(Runner runner){
        runners.add(runner);
    }

    public boolean containsRunner(Runner runner){
        return runners.contains(runner);
    }

    public void addRunner(Runner runner) {
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (!container.has(runner.getRunnerKey(), PersistentDataType.BOOLEAN)) {
            container.set(runner.getRunnerKey(), PersistentDataType.BOOLEAN, true);
            item.setItemMeta(itemMeta);
        }
    }

    public void removeRunner(Runner runner) {
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (container.has(runner.getRunnerKey(), PersistentDataType.BOOLEAN)) {
            container.remove(runner.getRunnerKey());
            item.setItemMeta(itemMeta);
        }
    }

    public ItemStack getItem() {
        return item;
    }

    public NamespacedKey getNamespacedKey() {
        return namespacedKey;
    }
}
