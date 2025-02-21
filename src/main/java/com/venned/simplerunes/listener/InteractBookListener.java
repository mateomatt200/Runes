package com.venned.simplerunes.listener;

import com.venned.simplerunes.abstracts.Runner;
import com.venned.simplerunes.build.BookRunner;
import com.venned.simplerunes.gui.BookGUIRunner;
import com.venned.simplerunes.utils.MapUtils;
import com.venned.simplerunes.utils.PDHUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

public class InteractBookListener implements Listener {


    private final PDHUtils pdhUtils;
    private final Plugin plugin;
    private final MapUtils mapUtils;
    private final BookGUIRunner bookGUIRunner;

    public InteractBookListener(PDHUtils pdhUtils, Plugin plugin, MapUtils mapUtils, BookGUIRunner bookGUIRunner) {
        this.pdhUtils = pdhUtils;
        this.plugin = plugin;
        this.mapUtils = mapUtils;
        this.bookGUIRunner = bookGUIRunner;
    }

    @EventHandler
    public void onBookRunnerItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType() != Material.AIR){
            ItemMeta meta = item.getItemMeta();
            if(meta != null){
                PersistentDataContainer container = meta.getPersistentDataContainer();
                    if(container.has(pdhUtils.getBookRunner().getNamespacedKey())){
                        BookRunner bookRunner = getBookRunner(item, container);
                        bookGUIRunner.Initialize(bookRunner, player);
                    }
            }
        }
    }

    public BookRunner getBookRunner(ItemStack item, PersistentDataContainer persistentDataContainer) {
        BookRunner bookRunner = new BookRunner(pdhUtils.getBookRunner().getNamespacedKey(), item);
        for(Runner runner : pdhUtils.getRunners()){
            if(persistentDataContainer.has(runner.getRunnerKey())){
                bookRunner.addList(runner);
            }
        }
        return bookRunner;

    }
}
