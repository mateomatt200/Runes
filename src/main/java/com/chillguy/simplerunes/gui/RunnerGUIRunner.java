package com.chillguy.simplerunes.gui;

import com.chillguy.simplerunes.abstracts.GUIRunner;
import com.chillguy.simplerunes.abstracts.Runner;
import com.chillguy.simplerunes.utils.MapUtils;
import com.chillguy.simplerunes.utils.PDHUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RunnerGUIRunner extends GUIRunner implements Listener {

    private final PDHUtils pdhUtils;
    private final MapUtils mapUtils;

    public RunnerGUIRunner(PDHUtils pdhUtils, MapUtils mapUtils){
        super("", 27, null);
        this.pdhUtils = pdhUtils;
        this.mapUtils = mapUtils;
    }

    public RunnerGUIRunner(PDHUtils pdhUtils, Plugin plugin, MapUtils mapUtils) {
        super("Runner Status", 27, plugin);
        this.pdhUtils = pdhUtils;
        this.mapUtils = mapUtils;
    }

    @Override
    public Inventory createInventory(Player player, boolean status) {
        Inventory inventory = Bukkit.createInventory(null, size, title);
        mapUtils.setPlayerInventories(player, inventory);

        initializeItems(inventory, status);
        return inventory;
    }

    private void initializeItems(Inventory inventory, boolean status) {
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackGlassMeta = blackGlass.getItemMeta();
        blackGlassMeta.setDisplayName(" ");
        blackGlass.setItemMeta(blackGlassMeta);

        for (int i = 0; i < size; i++) {
            inventory.setItem(i, blackGlass);
        }

        ItemStack centerItem;
        if (status) {
            centerItem = new ItemStack(Material.EMERALD);
            ItemMeta centerMeta = centerItem.getItemMeta();
            centerMeta.setDisplayName("§aRunner is Active");
            centerItem.setItemMeta(centerMeta);
        } else {
            centerItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta centerMeta = centerItem.getItemMeta();
            centerMeta.setDisplayName("§cRunner is Inactive");
            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§5Place a Gem so it can be activated!");
            centerMeta.setLore(lore);
            centerItem.setItemMeta(centerMeta);
        }

        int centerSlot = (size / 2);
        inventory.setItem(centerSlot, centerItem);
    }


    @EventHandler
    protected void OnInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(Objects.equals(event.getInventory(), mapUtils.getPlayerInventoryRunner().get(player.getUniqueId()))) {
            ItemStack clickedItem = event.getCurrentItem();
            event.setCancelled(true);
            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }
            if (clickedItem.getType() == Material.EMERALD) {
                if(clickedItem.getItemMeta() != null) {
                    if(clickedItem.getItemMeta().getPersistentDataContainer().has(pdhUtils.getGemKey())) {
                        ItemStack itemInHand = player.getInventory().getItemInMainHand();

                        if (itemInHand != null && itemInHand.hasItemMeta()) {
                            ItemMeta meta = itemInHand.getItemMeta();
                            PersistentDataContainer container = meta.getPersistentDataContainer();
                            for (Runner runner : pdhUtils.getRunners()) {
                                if (container.has(runner.getRunnerKey())) {
                                    if (!runner.StatusRunner(itemInHand)) {
                                        container.set(runner.getRunnerKey(), PersistentDataType.BOOLEAN, true);
                                        itemInHand.setItemMeta(meta);
                                        Inventory inventory = createInventory(player, true);
                                        player.openInventory(inventory);
                                        removeItemFromPlayerInventory(player, clickedItem);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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
