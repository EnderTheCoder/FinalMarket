package dev.ender.finalmarket.database;

import dev.ender.finalmarket.DealRecord;
import dev.ender.finalmarket.MarketItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Cache {
    public static HashMap<Integer, MarketItem> MARKET_ITEMS = new HashMap<>();
    public static HashMap<Integer, DealRecord> DEAL_RECORDS = new HashMap<>();


    public static void saveMarketItem(MarketItem item) {
        SQLite s = new SQLite();

        try {
            ResultSet result = s.prepare("SELECT FROM market_item WHERE id = ?")
                    .bindInt(1, item.id)
                    .execute()
                    .result();

            if (result.next()) {
                s.prepare("UPDATE market_item SET type = ?, metadata = ?, amount = ?, price = ? WHERE id = ?")
                        .bindString(1, item.item.getType().getKey().getKey())
                        .bindString(2, item.item.getItemMeta() == null ? "" : item.item.getItemMeta().getAsString())
                        .bindLong(3, item.amount)
                        .bindDouble(4, item.price)
                        .bindInt(5, item.id)
                        .execute();
            } else {
                s.prepare("INSERT INTO market_item(type, metadata, amount, price) VALUES (?, ?, ?, ?)")
                        .bindString(1, item.item.getType().getKey().getKey())
                        .bindString(2, item.item.getItemMeta() == null ? "" : item.item.getItemMeta().getAsString())
                        .bindLong(3, item.amount)
                        .bindDouble(4, item.price)
                        .execute();
            }
        } catch (SQLException e) {
            s.close();
            e.printStackTrace();
        }
        s.close();

    }

    public static void saveDealRecord(DealRecord dealRecord) {

    }

    public static void saveAll() {
        for (Integer key : MARKET_ITEMS.keySet()) {
            saveMarketItem(MARKET_ITEMS.get(key));
        }
        for (Integer key : DEAL_RECORDS.keySet()) {
            saveDealRecord(DEAL_RECORDS.get(key));
        }
    }
}
