package ru.vsu.lab6.demo;

import ru.vsu.lab6.annotations.ToString;

@ToString
public class Person {

    private String name;

    @ToString(ToString.Mode.NO)
    private String password;

    private int age;

    public Person(String name, String password, int age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }
}
