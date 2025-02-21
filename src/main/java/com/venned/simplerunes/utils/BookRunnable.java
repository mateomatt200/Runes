package com.venned.simplerunes.utils;

import com.venned.simplerunes.abstracts.Runner;
import com.venned.simplerunes.build.BookRunner;
import com.venned.simplerunes.runner.RunnerHaste;
import com.venned.simplerunes.runner.RunnerRegen;
import com.venned.simplerunes.runner.RunnerSpeed;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BookRunnable extends BukkitRunnable {

    private final Plugin plugin;
    private final Map<UUID, BookRunner> playerBookRunners = new HashMap<>();
    private final PDHUtils pdhUtils;

    public BookRunnable(Plugin plugin, PDHUtils pdhUtils) {
        this.plugin = plugin;
        this.pdhUtils = pdhUtils;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Inventory inventory = player.getInventory();
            for(ItemStack item : inventory.getContents()) {
                if(item != null) {
                    if (item.getItemMeta() != null) {
                        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
                        if (container.has(pdhUtils.getBookRunner().getNamespacedKey())) {
                            BookRunner bookRunner = getBookRunner(item, container);
                            for (Runner runner : bookRunner.getRunners()) {
                                if (runner instanceof RunnerSpeed) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
                                            200, 3, true, true));
                                }
                                if (runner instanceof RunnerHaste){
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 3, true, true));
                                }
                                if (runner instanceof RunnerRegen){
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 3, true, true));
                                }
                            }
                        }
                    }
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
