package com.chillguy.simplerunes.gui;

import com.chillguy.simplerunes.abstracts.Runner;
import com.chillguy.simplerunes.build.BookRunner;
import com.chillguy.simplerunes.utils.MapUtils;
import com.chillguy.simplerunes.utils.PDHUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class BookGUIRunner implements Listener {

    private final PDHUtils pdhUtils;
    private final MapUtils mapUtils;

    private final Map<UUID, BookRunner> playerBookRunners = new HashMap<>();


    public BookGUIRunner(PDHUtils pdhUtils, MapUtils mapUtils) {
        this.pdhUtils = pdhUtils;
        this.mapUtils = mapUtils;
    }

    public void Initialize(BookRunner bookRunner, Player player){
        playerBookRunners.remove(player.getUniqueId());

        playerBookRunners.put(player.getUniqueId(), bookRunner);
        player.openInventory(createInventory(player, bookRunner));
    }

    public Inventory createInventory(Player player, BookRunner bookRunner) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Book Runner");

        mapUtils.getPlayerInventoryBook().remove(player.getUniqueId());
        mapUtils.setPlayerInventoryBook(player, inventory);
        initializeItems(inventory, bookRunner.getRunners());
        return inventory;
    }

    private void initializeItems(Inventory inventory, List<Runner> runners) {
        ItemStack redGlass = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta redGlassMeta = redGlass.getItemMeta();
        redGlassMeta.setDisplayName(" ");
        redGlass.setItemMeta(redGlassMeta);


        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, redGlass);
        }

        int[] centerSlots = {12, 13, 14};
        for (int i = 0; i < centerSlots.length; i++) {
            if (i < runners.size()) {
                inventory.setItem(centerSlots[i], runners.get(i).createItemGUI());
            } else {
                inventory.setItem(centerSlots[i], new ItemStack(Material.AIR));
            }
        }
    }


    @EventHandler
    protected void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        BookRunner bookRunner = playerBookRunners.get(player.getUniqueId());

        if (bookRunner != null && Objects.equals(event.getInventory(), mapUtils.getPlayerInventoryBook().get(player.getUniqueId()))) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }

            if (event.getClickedInventory() == player.getInventory()) {
                // Handle adding a rune from player's inventory to the BookRunner GUI
                if (clickedItem.getItemMeta() != null) {
                    PersistentDataContainer container = clickedItem.getItemMeta().getPersistentDataContainer();
                    for (Runner runner : pdhUtils.getRunners()) {
                        if (container.has(runner.getRunnerKey(), PersistentDataType.BOOLEAN)) {
                            if (!bookRunner.containsRunner(runner)) {
                                if (Boolean.TRUE.equals(container.get(runner.getRunnerKey(), PersistentDataType.BOOLEAN))) {
                                    bookRunner.addRunner(runner);
                                    player.sendMessage("§c§l(!) §eThe rune was equipped successfully!");
                                    player.closeInventory();
                                    removeItemFromPlayerInventory(player, clickedItem);
                                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.5f, 1.5f);
                                    initializeItems(event.getInventory(), bookRunner.getRunners());
                                } else {
                                    player.sendMessage("§c§l(!) §cThis rune is not active");
                                }
                            } else {
                                player.sendMessage("§c§l(!) §That Rune is already active");
                            }
                        }
                    }
                }
            } else if (event.getClickedInventory() == event.getInventory()) {
                // Handle removing a rune from the BookRunner GUI back to the player's inventory
                if (clickedItem.getItemMeta() != null) {
                    PersistentDataContainer container = clickedItem.getItemMeta().getPersistentDataContainer();
                    for (Runner runner : pdhUtils.getRunners()) {
                        if (container.has(runner.getRunnerKey(), PersistentDataType.BOOLEAN)) {
                            if (bookRunner.containsRunner(runner)) {
                                bookRunner.removeRunner(runner);
                                player.sendMessage("§c§l(!) §eThe rune was unequipped successfully!");
                                player.closeInventory();
                                addItemToPlayerInventory(player, clickedItem);
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.5f, 1.5f);
                            } else {
                                player.sendMessage("§c§l(!) §That Rune is not active");
                            }
                        }
                    }
                }
            }
        }
    }

    private void addItemToPlayerInventory(Player player, ItemStack item) {
        Inventory playerInventory = player.getInventory();
        playerInventory.addItem(item);
    }

    private void removeItemFromPlayerInventory(Player player, ItemStack item) {
        Inventory playerInventory = player.getInventory();
        for (int i = 0; i < playerInventory.getSize(); i++) {
            ItemStack invItem = playerInventory.getItem(i);
            if (invItem != null && invItem.isSimilar(item)) {
                playerInventory.setItem(i, new ItemStack(Material.AIR));
                break;
            }
        }
    }
}
