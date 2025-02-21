package com.venned.simplerunes.abstracts;

import com.venned.simplerunes.listener.CustomGUIListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public abstract class GUIRunner extends CustomGUIListener {

    protected String title;
    protected int size;

    public GUIRunner(String title, int size, Plugin plugin) {
        this.title = title;
        this.size = size;
    }

    public abstract Inventory createInventory(Player player, boolean status);

}