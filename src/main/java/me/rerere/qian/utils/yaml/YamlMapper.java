package me.rerere.qian.utils.yaml;

import me.rerere.qian.Qian;
import me.rerere.qian.utils.yaml.annotations.YamlEntity;
import me.rerere.qian.utils.yaml.annotations.YamlPath;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlMapper {
    public static <T> T loadYamlByClass(Class<T> type) {
        // Check whether this class is a configuration entity class
        if (!type.isAnnotationPresent(YamlEntity.class)) {
            throw new IllegalArgumentException("the class is not a yaml entity class");
        }
        // Check if the plugin folder is created
        if (!Qian.getInstance().getDataFolder().exists()) {
            Qian.getInstance().getDataFolder().mkdirs();
        }
        // Generate configuration file
        String filePath = type.getAnnotation(YamlEntity.class).filePath();
        File file = new File(Qian.getInstance().getDataFolder(), filePath);
        if (!file.exists()) {
            Qian.getInstance().saveResource(filePath, false);
        }
        // Load configuration file
        YamlConfiguration yamlConfiguration = load(file);
        // Create a config entity object through reflection
        T object = null;
        try {
            object = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // Scan all fields and load the value based on their Annotation
        for (Field field : type.getDeclaredFields()) {
            // No YamlPath annotation
            if (!field.isAnnotationPresent(YamlPath.class)) {
                continue;
            }
            // Get the path from the annotation
            String path = field.getAnnotation(YamlPath.class).value();
            if (yamlConfiguration.contains(path)) {
                parseValueToField(object, field, yamlConfiguration, path);
            } else {
                // TODO?
                // Generate default value?
            }
        }
        return object;
    }

    private static <T> void parseValueToField(T object, Field field, YamlConfiguration configuration, String path) {
        Class<?> type = field.getType();
        field.setAccessible(true);
        try {
            if (type == int.class || type == Integer.TYPE) {
                field.set(object, configuration.getInt(path));

            } else if (type == double.class || type == Double.TYPE || type == float.class || type == Float.TYPE) {
                field.set(object, configuration.getDouble(path));

            } else if (type == String.class) {
                field.set(object, configuration.getString(path));

            } else if (type == boolean.class || type == Boolean.class) {
                field.set(object, configuration.getBoolean(path));

            } else if (type == List.class) {
                field.set(object, configuration.getList(path));

            } else if (type == Map.class) {
                Map<String, Object> map = new HashMap<>();
                configuration.getConfigurationSection(path)
                        .getKeys(false)
                        .forEach(key -> map.put(key, configuration.get(path + "." + key)));
                field.set(object, map);

            } else {
                throw new IllegalArgumentException(String.format("Not support yaml value type: %s", type.getName()));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfiguration load(File file) {
        YamlConfiguration yamlConfiguration = null;
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            yamlConfiguration = YamlConfiguration.loadConfiguration((reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return yamlConfiguration;
    }
}
