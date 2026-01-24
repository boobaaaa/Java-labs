package ru.vsu.lab6.demo;

import ru.vsu.lab6.annotations.Default;

@Default(String.class)
public class DefaultDemo {

    @Default(Integer.class)
    private Object field;

    public Object getField() {
        return field;
    }
}
