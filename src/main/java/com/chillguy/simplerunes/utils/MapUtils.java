package com.chillguy.simplerunes.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MapUtils {

    private Map<UUID, Inventory> playerInventoryRunner = new HashMap<UUID, Inventory>();
    private Map<UUID, Inventory> playerInventoryBook = new HashMap<>();

    public Map<UUID, Inventory> getPlayerInventoryBook() {
        return playerInventoryBook;
    }

    public void setPlayerInventoryBook(Player player, Inventory inventory) {
        playerInventoryBook.put(player.getUniqueId(), inventory);
    }

    public Map<UUID, Inventory> getPlayerInventoryRunner() {
        return playerInventoryRunner;
    }

    public void setPlayerInventories(Player player, Inventory inventory) {
        playerInventoryRunner.put(player.getUniqueId(), inventory);
    }
}
