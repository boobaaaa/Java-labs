package ru.vsu.lab6.processors;

import ru.vsu.lab6.annotations.ToString;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class ToStringProcessor {

    private ToStringProcessor() {}

    public static String build(Object obj) {
        if (obj == null) return "null";

        Class<?> c = obj.getClass();
        List<String> parts = new ArrayList<>();

        for (Field f : c.getDeclaredFields()) {
            f.setAccessible(true);

            ToString ts = f.getAnnotation(ToString.class);
            if (ts != null && ts.value() == ToString.Mode.NO) {
                continue;
            }

            try {
                Object v = f.get(obj);
                parts.add(f.getName() + "=" + v);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Нет доступа к полю: " + f.getName(), e);
            }
        }

        return c.getSimpleName() + "{" + String.join(", ", parts) + "}";
    }
}
