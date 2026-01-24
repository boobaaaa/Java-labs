package ru.vsu.lab6.processors;

import ru.vsu.lab6.annotations.Two;

public final class TwoProcessor {

    private TwoProcessor() {}

    public static String readTwo(Class<?> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Класс не задан");
        Two two = clazz.getAnnotation(Two.class);
        if (two == null) return null;

        return "first=\"" + two.first() + "\", second=" + two.second();
    }
}
