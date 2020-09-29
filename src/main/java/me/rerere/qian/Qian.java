package me.rerere.qian;

import lombok.Getter;
import me.rerere.qian.config.ConfigLoader;
import me.rerere.qian.database.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public final class Qian extends JavaPlugin {
    @Getter
    private static Qian instance;

    private ConfigLoader configLoader;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        instance = this;
        this.showBanner();

        // Load all configuration files and language files
        this.loadConfigFiles();

        // load database
        this.databaseManager = new DatabaseManager();
        this.databaseManager.init();
    }

    @Override
    public void onDisable() {
        this.databaseManager.shutdown();
    }

    private void showBanner() {
        Logger logger = this.getLogger();
        logger.info("   ____      ____   ___       _   __");
        logger.info("  / __ \\    /  _/  /   |     / | / /");
        logger.info(" / / / /    / /   / /| |    /  |/ / ");
        logger.info("/ /_/ /   _/ /   / ___ |   / /|  /  ");
        logger.info("\\___\\_\\  /___/  /_/  |_|  /_/ |_/");
        logger.info("");
    }

    private void loadConfigFiles() {
        this.configLoader = new ConfigLoader();
        this.configLoader.load();
    }
}
