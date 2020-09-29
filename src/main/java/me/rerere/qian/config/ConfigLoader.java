package me.rerere.qian.config;

import lombok.Getter;
import me.rerere.qian.config.files.MainConfig;
import me.rerere.qian.utils.yaml.YamlMapper;

@Getter
public class ConfigLoader {
    private MainConfig mainConfig;

    public void load() {
        mainConfig = YamlMapper.loadYamlByClass(MainConfig.class);
    }
}
