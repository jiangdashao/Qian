package me.rerere.qian.config.files;

import lombok.Getter;
import lombok.ToString;
import me.rerere.qian.utils.yaml.annotations.YamlEntity;
import me.rerere.qian.utils.yaml.annotations.YamlPath;

@Getter
@ToString
@YamlEntity
public class MainConfig {
    @YamlPath("language")
    private String language;

    @YamlPath("database.type")
    private String databaseType;

    @YamlPath("database.mysql.address")
    private String mysqlAddress;

    @YamlPath("database.mysql.user")
    private String mysqlUser;

    @YamlPath("database.mysql.password")
    private String mysqlPassword;

    @YamlPath("database.mysql.database")
    private String mysqlDatabase;

    @YamlPath("database.mysql.table_prefix")
    private String mysqlTablePrefix;

    @YamlPath("economic.initial_balance")
    private double initialBalance;
}
