package com.chillguy.simplerunes;

import com.chillguy.simplerunes.commands.MainCommand;
import com.chillguy.simplerunes.gui.BookGUIRunner;
import com.chillguy.simplerunes.gui.RunnerGUIRunner;
import com.chillguy.simplerunes.listener.InteractBookListener;
import com.chillguy.simplerunes.listener.InteractRunnerListener;
import com.chillguy.simplerunes.utils.BookRunnable;
import com.chillguy.simplerunes.utils.MapUtils;
import com.chillguy.simplerunes.utils.PDHUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        PDHUtils pdhUtils = new PDHUtils(this);
        MapUtils mapUtils = new MapUtils();
        BookGUIRunner bookGUIRunner = new BookGUIRunner(pdhUtils, mapUtils);

        Objects.requireNonNull(getCommand("runner")).setExecutor(new MainCommand(pdhUtils));
        getCommand("runner").setTabCompleter(new MainCommand(pdhUtils));

        BookRunnable bookRunnable = new BookRunnable(this, pdhUtils);
        bookRunnable.runTaskTimer(this, 0L, 1L);

        getServer().getPluginManager().registerEvents(bookGUIRunner, this);
        getServer().getPluginManager().registerEvents(new RunnerGUIRunner(pdhUtils, mapUtils), this);
        getServer().getPluginManager().registerEvents(new InteractBookListener(pdhUtils, this, mapUtils, bookGUIRunner), this);
        getServer().getPluginManager().registerEvents(new InteractRunnerListener(pdhUtils, this, mapUtils), this);

    }

    @Override
    public void onDisable() {

    }

}
