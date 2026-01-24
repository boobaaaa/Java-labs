package ru.vsu.lab6.processors;

import ru.vsu.lab6.annotations.Cache;

import java.util.Arrays;
import java.util.List;

public final class CacheProcessor {

    private CacheProcessor() {}

    public static List<String> readRegions(Class<?> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Класс не задан");
        Cache cache = clazz.getAnnotation(Cache.class);
        if (cache == null) return List.of();
        return Arrays.asList(cache.value());
    }
}
