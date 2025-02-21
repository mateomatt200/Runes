package com.venned.simplerunes.runner;

import com.venned.simplerunes.abstracts.Runner;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RunnerSpeed extends Runner {

    public RunnerSpeed(NamespacedKey runeSpeed, String description) {
        super("Speed", runeSpeed, new ItemStack(Material.PIGLIN_BANNER_PATTERN), description);
    }
}
