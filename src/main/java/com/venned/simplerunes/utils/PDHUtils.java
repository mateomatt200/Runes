package com.venned.simplerunes.utils;

import com.venned.simplerunes.build.BookRunner;
import com.venned.simplerunes.abstracts.Runner;
import com.venned.simplerunes.runner.RunnerHaste;
import com.venned.simplerunes.runner.RunnerRegen;
import com.venned.simplerunes.runner.RunnerSpeed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class PDHUtils {

    private BookRunner bookRunner;
    private NamespacedKey bookKey;
    private NamespacedKey rune_speed;
    private NamespacedKey rune_haste;
    private NamespacedKey rune_regen;
    private NamespacedKey gemKey;
    private final List<Runner> runners = new ArrayList<>();

    RunnerSpeed runnerSpeed;
    RunnerHaste runnerHaste;
    RunnerRegen runnerRegen;

    public PDHUtils(Plugin plugin) {
        bookKey = new NamespacedKey(plugin, "book_runner");
        gemKey = new NamespacedKey(plugin, "gem_runner");
        createRunners(plugin);
        createBook();
    }

    public ItemStack createGem(){
        ItemStack itemStack = new ItemStack(Material.EMERALD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§e Gem Power");
        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§7 Use it to power your rune");
        itemMeta.setLore(lore);
        itemMeta.getPersistentDataContainer().set(
                gemKey,
                PersistentDataType.BOOLEAN,
                true
        );
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    public void createBook(){
        ItemStack itemStack = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§6 Book of Runes");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§7 This item you can store your runes and activate them.");
        lore.add(" ");
        lore.add("§a§l RIGHT CLICK TO USE");
        itemMeta.setLore(lore);
        itemMeta.getPersistentDataContainer().set(bookKey,
                PersistentDataType.BOOLEAN, true);
        itemStack.setItemMeta(itemMeta);
        bookRunner = new BookRunner(bookKey, itemStack);
    }

    public void createRunners(Plugin plugin){
        rune_speed = new NamespacedKey(plugin, "rune_speed");
        rune_haste = new NamespacedKey(plugin, "rune_haste");
        rune_regen = new NamespacedKey(plugin, "rune_regen");

        String description_speed = "This rune grants you speed";
        String description_haste = "This rune grants you Haste";
        String description_regen = "This rune grants you regen";

        runnerSpeed = new RunnerSpeed(rune_speed, description_speed);
        runnerHaste = new RunnerHaste(rune_haste, description_haste);
        runnerRegen = new RunnerRegen(rune_regen, description_regen);

        runners.add(runnerSpeed);
        runners.add(runnerHaste);
        runners.add(runnerRegen);
    }

    public NamespacedKey getGemKey() {
        return gemKey;
    }

    public List<Runner> getRunners() {
        return runners;
    }

    public RunnerSpeed getRunnerSpeed() {
        return runnerSpeed;
    }

    public RunnerRegen getRunnerRegen(){
        return runnerRegen;
    }

    public RunnerHaste getRunnerHaste() {
        return runnerHaste;
    }

    public NamespacedKey getRune_speed() {
        return rune_speed;
    }

    public BookRunner getBookRunner() {
        return bookRunner;
    }
}
