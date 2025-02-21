package com.chillguy.simplerunes.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class CustomGUIListener implements Listener {

    protected abstract void OnInventoryClick(InventoryClickEvent e);
}
