package dev.ender.finalmarket;

import dev.ender.finalmarket.command.MarketCommand;
import dev.ender.finalmarket.database.SQLite;
import dev.ender.finalmarket.gui.MarketGUI;
import dev.ender.finalmarket.task.SaveCache;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class FinalMarket extends JavaPlugin {


    public static Logger LOGGER = Bukkit.getLogger();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        SQLite.initTable();

        LOGGER.info(ChatColor.GOLD + "This plugin is made by RurdCrod and EnderTheCoder together.");

        new SaveCache().runTaskTimerAsynchronously(this, 0, 2400);

        if (Bukkit.getPluginCommand("finalmarket") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("finalmarket")).setExecutor(new MarketCommand());
        }

        Bukkit.getPluginManager().registerEvents(new MarketGUI(), this);
    }

    @Override
    public void onDisable() {

    }
}
