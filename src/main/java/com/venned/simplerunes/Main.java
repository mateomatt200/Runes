package com.venned.simplerunes;

import com.venned.simplerunes.commands.MainCommand;
import com.venned.simplerunes.gui.BookGUIRunner;
import com.venned.simplerunes.gui.RunnerGUIRunner;
import com.venned.simplerunes.listener.InteractBookListener;
import com.venned.simplerunes.listener.InteractRunnerListener;
import com.venned.simplerunes.utils.BookRunnable;
import com.venned.simplerunes.utils.MapUtils;
import com.venned.simplerunes.utils.PDHUtils;
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
