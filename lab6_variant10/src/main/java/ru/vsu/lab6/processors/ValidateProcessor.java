package ru.vsu.lab6.processors;

import ru.vsu.lab6.annotations.Validate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ValidateProcessor {

    private ValidateProcessor() {}

    public static List<String> readValidatedClasses(Class<?> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Класс не задан");
        Validate v = clazz.getAnnotation(Validate.class);
        if (v == null) return List.of();

        return Stream.of(v.value())
                .map(Class::getName)
                .collect(Collectors.toList());
    }
}
