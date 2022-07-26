package dev.ender.finalmarket.database;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;


public class SQLite {
    private Connection connection = null;
    private PreparedStatement statement = null;

    private static HashMap<String, String> REQUIRED_TABLES;

    public SQLite connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            //如果没有数据库文件的话会自动创建
            this.connection = DriverManager.getConnection("jdbc:sqlite:./plugins/FinalMarket/data.db");
        } catch (Exception e) {
            getLogger().severe(e.getClass().getName() + ": " + e.getMessage());
        }
        return this;
    }

    public Connection getConnection() {
        try {
            if (this.connection == null || !this.connection.isValid(1000)) connect();
        } catch (SQLException e) {
            getLogger().severe("获取数据库连接时发生错误");

            e.printStackTrace();
        }
        return connection;
    }

    public SQLite prepare(String sql) {
        try {
            statement = getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            getLogger().severe("预处理SQL语句时发生错误");
            e.printStackTrace();
        }
        return this;
    }

    public SQLite bindString(int number, String value) {
        try {
            statement.setString(number, value);
        } catch (SQLException e) {
            getLogger().severe("绑定参数时时发生错误");
            e.printStackTrace();
        }
        return this;
    }

    public SQLite bindInt(int number, int value) {
        try {
            statement.setInt(number, value);
        } catch (SQLException e) {
            getLogger().severe("绑定参数时时发生错误");
            e.printStackTrace();
        }
        return this;
    }

    public SQLite bindDouble(int number, double value) {
        try {
            statement.setDouble(number, value);
        } catch (SQLException e) {
            getLogger().severe("绑定参数时时发生错误");
            e.printStackTrace();
        }
        return this;
    }

    public SQLite bindLong(int number, long value) {
        try {
            statement.setLong(number, value);
        } catch (SQLException e) {
            getLogger().severe("绑定参数时时发生错误");
            e.printStackTrace();
        }
        return this;
    }

    public SQLite execute() {
        try {
            statement.execute();
        } catch (SQLException e) {
            getLogger().severe("执行SQL语句时发生错误");
            e.printStackTrace();
        }
        return this;
    }

    public ResultSet result() {
        try {
            return statement.getResultSet();
        } catch (SQLException e) {
            getLogger().severe("获取数据库查询结果时发生错误");
            e.printStackTrace();
            return null;
        }
    }
    public SQLite close() {
        //计算数据库查询所用时间并输出调试信息
        Timestamp endTime = new Timestamp(System.currentTimeMillis());

//        if (ConfigReader.isOnDebug())
//            getLogger().info(String.format("数据库连接关闭，本次查询共用时%s毫秒", endTime.getTime() - startTime.getTime()));

        try {
            this.connection.close();
        } catch (SQLException e) {
            getLogger().severe("关闭数据库连接时发生错误");
            e.printStackTrace();
        }
        return this;
    }

    public static void setRequiredTables() {
        SQLite.REQUIRED_TABLES = new HashMap<>();
        SQLite.REQUIRED_TABLES.put("market_item", "create table market_item\n" +
                "(\n" +
                "    type     TEXT,\n" +
                "    metadata TEXT,\n" +
                "    amount   INTEGER,\n" +
                "    price    REAL,\n" +
                "    id       INTEGER not null\n" +
                "        constraint market_item_pk\n" +
                "            primary key autoincrement\n" +
                ");\n" +
                "\n");
        SQLite.REQUIRED_TABLES.put("deal_record" , "create table deal_record\n" +
                "(\n" +
                "    id          INTEGER not null\n" +
                "        constraint deal_record_pk\n" +
                "            primary key autoincrement,\n" +
                "    player_uuid TEXT,\n" +
                "    amount      INTEGER,\n" +
                "    costs       REAL,\n" +
                "    time        INTEGER\n" +
                ");\n" +
                "\n" +
                "create unique index deal_record_id_uindex\n" +
                "    on deal_record (id);");
    }

    private static boolean isTableExists(String tableName, SQLite s) {
        s.prepare("SELECT * FROM sqlite_master WHERE type='table' AND name = ?");
        s.bindString(1, tableName);
        s.execute();
        ResultSet resultSet = s.result();
        try {
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }
    public static void initTable() {

        setRequiredTables();

        SQLite s = new SQLite();

        for (String key : REQUIRED_TABLES.keySet()) {
            if (!isTableExists(key, s)) {
                s.prepare(REQUIRED_TABLES.get(key));
                s.execute();
            }
        }

        s.close();
    }
}