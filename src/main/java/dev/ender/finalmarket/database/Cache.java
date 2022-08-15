package dev.ender.finalmarket.database;

import dev.ender.finalmarket.DealRecord;
import dev.ender.finalmarket.MarketItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Cache {
    private static final HashMap<Integer, MarketItem> MARKET_ITEMS = new HashMap<>();

    private static final HashMap<Integer, DealRecord> DEAL_RECORDS = new HashMap<>();

    private static void deleteMarketItem(Integer id) {
        SQLite s = new SQLite();
        s.prepare("DELETE FROM market_item WHERE id = ?")
                .bindInt(1, id)
                .execute()
                .close();
    }

    private static void deleteDealRecord(Integer id) {
        SQLite s = new SQLite();
        s.prepare("DELETE FROM deal_record WHERE id = ?")
                .bindInt(1, id)
                .execute()
                .close();
    }

    private static void saveMarketItem(MarketItem item) {
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

    private static void saveDealRecord(DealRecord dealRecord) {
        SQLite s = new SQLite();
        try {
            ResultSet rs = s.prepare("SELECT FROM deal_record WHERE id = ?")
                    .bindInt(1, dealRecord.id)
                    .execute()
                    .result();
            if (rs.next()) {
                s.prepare("update deal_record SET player_uuid = ? amount = ? costs = ? time = ? where id =?")
                        .bindString(1, dealRecord.player.getUniqueId().toString())
                        .bindInt(2, dealRecord.amount)
                        .bindDouble(3, dealRecord.costs)
                        .bindLong(4, dealRecord.time)
                        .execute();
            } else {
                s.prepare("insert into  deal_record values (player_uuid , amount , costs , time) VALUES (? , ? , ? , ?)")
                        .bindString(1, dealRecord.player.getUniqueId().toString())
                        .bindInt(2, dealRecord.amount)
                        .bindDouble(3, dealRecord.costs)
                        .bindLong(4, dealRecord.time)
                        .execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            s.close();
        }
        s.close();
    }


    //add
    //remove
    //get

    public static void removeMarketItem(MarketItem marketItem) {
        MARKET_ITEMS.put(marketItem.id, null);
    }
    public static void removeDealRecord(DealRecord dealRecord){
        DEAL_RECORDS.put(dealRecord.id , null);
    }

    public static void addMarketItem (MarketItem marketItem){
        MARKET_ITEMS.put(marketItem.id, marketItem);
    }

    public static void addDealRecord(DealRecord dealRecord){
        DEAL_RECORDS.put(dealRecord.id , dealRecord);
    }

    public static void getMarketItem(Integer id) {
        MARKET_ITEMS.get(id);
    }

    public static void getDealRecord(Integer id){
        DEAL_RECORDS.get(id);
    }

    public static HashMap<Integer, MarketItem> getMarketItems() {
        return MARKET_ITEMS;
    }

    public static HashMap<Integer , DealRecord> getDealRecords(){
        return DEAL_RECORDS;
    }

    public static void saveAll() {
        for (Integer key : MARKET_ITEMS.keySet()) {

            MarketItem item = MARKET_ITEMS.get(key);
            if (item == null) {
                DEAL_RECORDS.remove(key);
                deleteMarketItem(key);
            } else {
                saveMarketItem(MARKET_ITEMS.get(key));
            }
        }
        for (Integer key : DEAL_RECORDS.keySet()) {
            DealRecord record = DEAL_RECORDS.get(key);
            if (record == null) {
                DEAL_RECORDS.remove(key);
                deleteDealRecord(key);
            } else {
                saveDealRecord(DEAL_RECORDS.get(key));
            }
        }
    }
}
