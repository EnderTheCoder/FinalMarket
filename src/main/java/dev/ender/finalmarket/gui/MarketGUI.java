package dev.ender.finalmarket.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class MarketGUI extends AbstractGUI {
    public MarketGUI(Player player) {
        super(player, "FinalMarket", 54);
        this.setButton(0, new ItemStack(Material.FIREWORK_ROCKET))
                .setButton(8, new ItemStack(Material.FIREWORK_ROCKET))
                .setBlank(new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
    }

    public MarketGUI() {

    }

    //buy
    @Override
    public MarketGUI leftClick(Integer pos) {

        //before page
        if (pos == 0) {

        }
        //next page
        if (pos == 8) {

        }
        //???判断上页下页？
        return this;
    }

    //sell
    @Override
    public MarketGUI rightClick(Integer pos) {
        return this;
    }
}
