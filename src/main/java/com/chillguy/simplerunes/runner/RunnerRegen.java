package com.chillguy.simplerunes.runner;

import com.chillguy.simplerunes.abstracts.Runner;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class RunnerRegen extends Runner {
    public RunnerRegen(NamespacedKey runnerKey, String description) {
        super("Regen", runnerKey, new ItemStack(Material.PIGLIN_BANNER_PATTERN), description);
    }
}
