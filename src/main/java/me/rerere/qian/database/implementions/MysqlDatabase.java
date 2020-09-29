package me.rerere.qian.database.implementions;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.rerere.qian.Qian;
import me.rerere.qian.config.files.MainConfig;
import me.rerere.qian.data.PlayerEcoData;
import me.rerere.qian.database.IDatabase;
import me.rerere.qian.database.SQLs;

import java.sql.Connection;
import java.sql.Statement;
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
            config.setDriverClassName("com.mysql.jdbc.Driver");
            config.setJdbcUrl(String.format("jdbc:mysql://%s/%s", mainConfig.getMysqlAddress(), mainConfig.getMysqlDatabase()));
            config.setUsername(mainConfig.getMysqlUser());
            config.setPassword(mainConfig.getMysqlPassword());
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            config.setConnectionTimeout(2000);

            // debug
            System.out.println(config.getJdbcUrl() + "~" + config.getUsername() + "~" + config.getPassword());

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
        return false;
    }

    @Override
    public Optional<PlayerEcoData> query(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void update(PlayerEcoData data) {

    }

    @Override
    public void batchUpdate(Set<PlayerEcoData> dataSet) {

    }
}
