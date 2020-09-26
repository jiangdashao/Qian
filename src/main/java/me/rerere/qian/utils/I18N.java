package me.rerere.qian.utils;

public class I18N {
    public static String get(String path) {
        return "TODO";
    }

    public static String get(String path, Object... args) {
        return String.format(get(path), args);
    }
}
