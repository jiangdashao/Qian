package me.rerere.qian.database;

import lombok.Getter;
import me.rerere.qian.Qian;
import me.rerere.qian.config.files.MainConfig;
import me.rerere.qian.database.implementions.H2Database;
import me.rerere.qian.database.implementions.MysqlDatabase;

@Getter
public class DatabaseManager {
    private IDatabase database;

    public void init() {
        MainConfig mainConfig = Qian.getInstance().getConfigLoader().getMainConfig();
        switch (mainConfig.getDatabaseType().toLowerCase()) {
            case "h2":
                this.database = new H2Database();
                break;
            case "mysql":
                this.database = new MysqlDatabase();
                break;
            default:
                throw new IllegalStateException(String.format("Unsupported database type: %s", mainConfig.getDatabaseType()));
        }

        if (!this.database.init()) {
            throw new IllegalStateException(String.format("Can not init the %s database, please check your configuration file", this.database.getName()));
        }
    }

    public void shutdown() {
        if (database != null)
            this.database.shutdown();
        else
            Qian.getInstance().getLogger().warning("No database connection, how to stop it?");
    }
}
