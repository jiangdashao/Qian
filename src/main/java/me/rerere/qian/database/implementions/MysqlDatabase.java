package me.rerere.qian.database.implementions;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.rerere.qian.Qian;
import me.rerere.qian.config.files.MainConfig;
import me.rerere.qian.data.PlayerEcoData;
import me.rerere.qian.database.IDatabase;
import me.rerere.qian.database.SQLs;

import java.sql.*;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class MysqlDatabase implements IDatabase {
    private HikariDataSource hikariDataSource;

    @Override
    public String getName() {
        return "MySQL";
    }

    @Override
    public boolean init() {
        MainConfig mainConfig = Qian.getInstance().getConfigLoader().getMainConfig();

        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(String.format("jdbc:mysql://%s/%s", mainConfig.getMysqlAddress(), mainConfig.getMysqlDatabase()));
            config.setUsername(mainConfig.getMysqlUser());
            config.setPassword(mainConfig.getMysqlPassword());
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            this.hikariDataSource = new HikariDataSource(config);

            // create the table
            Connection connection = this.hikariDataSource.getConnection();
            if (connection == null)
                return false;
            Statement statement = connection.createStatement();
            statement.execute(String.format(SQLs.BALANCE_TABLE_CREATION, mainConfig.getMysqlTablePrefix() + SQLs.BALANCE_TABLE_NAME));
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void shutdown() {
        this.hikariDataSource.close();
        this.hikariDataSource = null;
    }

    @Override
    public boolean containsData(UUID uuid) {
        boolean contains = false;
        try {
            Connection connection = this.hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format(SQLs.BALANCE_DATA_QUERY, Qian.getInstance().getConfigLoader().getMainConfig().getMysqlTablePrefix() + SQLs.BALANCE_TABLE_NAME));
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                contains = true;
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return contains;
    }

    @Override
    public Optional<PlayerEcoData> query(UUID uuid) {
        PlayerEcoData data = null;
        try {
            Connection connection = this.hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format(SQLs.BALANCE_DATA_QUERY, Qian.getInstance().getConfigLoader().getMainConfig().getMysqlTablePrefix() + SQLs.BALANCE_TABLE_NAME));
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                data = new PlayerEcoData(resultSet.getString("uuid"), resultSet.getString("name"), resultSet.getDouble("balance"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(data);
    }

    @Override
    public void insert(PlayerEcoData data) {
        try {
            Connection connection = this.hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format(SQLs.BALANCE_DATA_INSERT, Qian.getInstance().getConfigLoader().getMainConfig().getMysqlTablePrefix() + SQLs.BALANCE_TABLE_NAME));

            preparedStatement.setString(1, data.getUuid());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setDouble(3, data.getBalance());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void update(PlayerEcoData data) {
        try {
            Connection connection = this.hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format(SQLs.BALANCE_DATA_UPDATE, Qian.getInstance().getConfigLoader().getMainConfig().getMysqlTablePrefix() + SQLs.BALANCE_TABLE_NAME));

            preparedStatement.setString(1, data.getName());
            preparedStatement.setDouble(2, data.getBalance());
            preparedStatement.setString(3, data.getUuid());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void batchUpdate(Set<PlayerEcoData> dataSet) {
        try {
            Connection connection = this.hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format(SQLs.BALANCE_DATA_UPDATE, Qian.getInstance().getConfigLoader().getMainConfig().getMysqlTablePrefix() + SQLs.BALANCE_TABLE_NAME));

            for (PlayerEcoData data : dataSet) {
                preparedStatement.setString(1, data.getName());
                preparedStatement.setDouble(2, data.getBalance());
                preparedStatement.setString(3, data.getUuid());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
