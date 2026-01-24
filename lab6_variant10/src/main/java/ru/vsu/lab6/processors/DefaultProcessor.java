package ru.vsu.lab6.processors;

import ru.vsu.lab6.annotations.Default;

import java.lang.reflect.Field;

public final class DefaultProcessor {

    private DefaultProcessor() {}

    public static String readDefaultFromClass(Class<?> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Класс не задан");
        Default d = clazz.getAnnotation(Default.class);
        return d == null ? null : d.value().getName();
    }

    public static String readDefaultFromField(Field field) {
        if (field == null) throw new IllegalArgumentException("Поле не задано");
        Default d = field.getAnnotation(Default.class);
        return d == null ? null : d.value().getName();
    }
}
