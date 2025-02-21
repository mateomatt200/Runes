package com.venned.simplerunes.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public abstract class CustomGUIListener implements Listener {

    protected abstract void OnInventoryClick(InventoryClickEvent e);
}
