package ru.vsu.lab6.demo;

import ru.vsu.lab6.annotations.Cache;

import java.util.Map;

@Cache({"users", "orders"})
public class CacheDemoService {

    public void warmUp(Map<String, Object> cache) {
        if (cache == null) throw new IllegalArgumentException("Кеш не задан");
        cache.put("users", "demo-users");
        cache.put("orders", "demo-orders");
    }
}
