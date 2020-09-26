package me.rerere.qian;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Qian extends JavaPlugin {
    private static Qian instance;

    @Override
    public void onEnable() {
        instance = this;
        this.showBanner();
        this.loadConfigFiles();
    }

    @Override
    public void onDisable() {

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

    }
}
