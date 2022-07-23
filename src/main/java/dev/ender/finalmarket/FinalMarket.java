package dev.ender.finalmarket;

import dev.ender.finalmarket.command.DemoCommand;
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
        LOGGER.info(ChatColor.GOLD + "This plugin is made by RurdCrod and EnderTheCoder together.");
        //Register the event listener.
//        Bukkit.getPluginManager().registerEvents(new DontBreak(),this);
        if (Bukkit.getPluginCommand("suicide") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("suicide")).setExecutor(new DemoCommand());
        }
    }

    @Override
    public void onDisable() {

    }
}
