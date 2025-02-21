package com.chillguy.simplerunes.listener;

import com.chillguy.simplerunes.abstracts.Runner;
import com.chillguy.simplerunes.gui.RunnerGUIRunner;
import com.chillguy.simplerunes.utils.MapUtils;
import com.chillguy.simplerunes.utils.PDHUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

public class InteractRunnerListener implements Listener {

    private final PDHUtils pdhUtils;
    private final Plugin plugin;
    private final MapUtils mapUtils;

    public InteractRunnerListener(PDHUtils pdhUtils, Plugin plugin, MapUtils mapUtils) {
        this.pdhUtils = pdhUtils;
        this.plugin = plugin;
        this.mapUtils = mapUtils;
    }



    @EventHandler
    public void onRunnerItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType() != Material.AIR && item.getType() != Material.BOOK){
            ItemMeta meta = item.getItemMeta();
            if(meta != null){
                PersistentDataContainer container = meta.getPersistentDataContainer();
                for(Runner runner : pdhUtils.getRunners()){
                    if(container.has(runner.getRunnerKey())){
                        RunnerGUIRunner runnerGUI = new RunnerGUIRunner(pdhUtils, plugin, mapUtils);
                        player.openInventory(runnerGUI.createInventory(player, runner.StatusRunner(item)));
                    }
                }
            }
        }
    }

}
