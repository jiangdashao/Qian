package me.rerere.qian.database;

public final class SQLs {
    public static final String BALANCE_TABLE_NAME = "balance";

    public static final String BALANCE_TABLE_CREATION = "CREATE TABLE IF NOT EXISTS %s(" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "uuid VARCHAR(255) NOT NULL," +
            "name VARCHAR(255) NOT NULL," +
            "balance DOUBLE NOT NULL" +
            ")";

    public static final String BALANCE_DATA_QUERY = "SELECT * FROM %s WHERE uuid=?";

    public static final String BALANCE_DATA_INSERT = "INSERT INTO %s(uuid, name, balance) VALUES(?,?,?)";

    public static final String BALANCE_DATA_UPDATE = "UPDATE %s SET name=?,balance=? WHERE uuid=?";
}
