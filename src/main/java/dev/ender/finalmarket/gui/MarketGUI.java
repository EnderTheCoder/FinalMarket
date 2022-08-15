package dev.ender.finalmarket.gui;

import dev.ender.finalmarket.MarketItem;
import dev.ender.finalmarket.database.Cache;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarketGUI extends AbstractGUI {

    public Integer marketPage = 0;

    public MarketGUI(Player player) {
        super(player, "FinalMarket", 54);
        this.setButton(0, new ItemStack(Material.FIREWORK_ROCKET))
                .setButton(8, new ItemStack(Material.FIREWORK_ROCKET))
                .setBlank(new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
    }

    public MarketGUI() {

    }

    @Override
    public MarketGUI create() {

        int i = 9;
        for (MarketItem marketItem : getPageData(this.marketPage)) {
            super.setButton(i, marketItem.item);
            i++;
        }
        return (MarketGUI) super.create();
    }

    private List<MarketItem> getPageData(Integer page) {
        HashMap<Integer, MarketItem> allCachedData = Cache.getMarketItems();
        List<MarketItem> pagedItems = new ArrayList<>();

        int startId = (page - 1) * 45;
        int endId;
        if (startId + 44 > allCachedData.size() - 1)    {
            endId = allCachedData.size() - 1;
        } else {
            endId = startId + 44;
        }
        for (Integer id : allCachedData.keySet()) {
            if (id >= startId && id <= endId) {
                pagedItems.add(allCachedData.get(id));
            }
        }
        return pagedItems;
    }

    //buy
    @Override
    public void leftClick(Integer pos) {

        //before page
        if (pos == 0) {

        }
        //next page
        if (pos == 8) {

        }

        if (pos >= 9 && pos <= 44) {
            refresh();
        }


    }

    //sell
    @Override
    public void rightClick(Integer pos) {
        if (pos >= 9 && pos <= 44) {
            refresh();
        }
    }
}
