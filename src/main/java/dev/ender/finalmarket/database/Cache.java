package dev.ender.finalmarket.database;

import dev.ender.finalmarket.DealRecord;
import dev.ender.finalmarket.MarketItem;

import java.util.HashMap;

public class Cache {
    public static HashMap<String, MarketItem> MARKET_ITEM = new HashMap<>();
    public static HashMap<Integer, DealRecord> DEAL_RECORD = new HashMap<>();


    public static void saveMarketItem(MarketItem item) {

    }

    public static void saveDealRecord(DealRecord dealRecord) {

    }

    public static void saveAll() {

    }
}
