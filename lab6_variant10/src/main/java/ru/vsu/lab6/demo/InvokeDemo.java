package ru.vsu.lab6.demo;

import ru.vsu.lab6.annotations.Invoke;

public class InvokeDemo {

    private int counter = 0;

    @Invoke
    public void inc() {
        counter++;
        System.out.println("Метод inc выполнен, counter=" + counter);
    }

    public int getCounter() {
        return counter;
    }
}
