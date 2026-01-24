package ru.vsu.lab6.processors;

import ru.vsu.lab6.annotations.Invoke;

import java.lang.reflect.Method;

public final class InvokeProcessor {

    private InvokeProcessor() {}

    public static void invokeAll(Object target) {
        if (target == null) throw new IllegalArgumentException("Объект не задан");

        for (Method m : target.getClass().getDeclaredMethods()) {
            if (!m.isAnnotationPresent(Invoke.class)) continue;
            if (m.getParameterCount() != 0) {
                throw new IllegalArgumentException("Метод с @Invoke должен быть без параметров: " + m.getName());
            }
            try {
                m.setAccessible(true);
                m.invoke(target);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка вызова метода: " + m.getName(), e);
            }
        }
    }
}
