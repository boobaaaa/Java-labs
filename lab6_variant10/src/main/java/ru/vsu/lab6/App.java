package ru.vsu.lab6;

import ru.vsu.lab6.demo.*;
import ru.vsu.lab6.processors.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== ЛР6 (вариант 10) ===");
            System.out.println("1) @Invoke");
            System.out.println("2) @Default");
            System.out.println("3) @ToString");
            System.out.println("4) @Validate");
            System.out.println("5) @Two");
            System.out.println("6) @Cache");
            System.out.println("0) Выход");
            System.out.print("Выбор: ");

            int choice = readInt(sc);
            switch (choice) {
                case 0 -> {
                    System.out.println("Выход.");
                    return;
                }
                case 1 -> demoInvoke();
                case 2 -> demoDefault();
                case 3 -> demoToString(sc);
                case 4 -> demoValidate();
                case 5 -> demoTwo();
                case 6 -> demoCache();
                default -> System.out.println("Некорректный пункт меню.");
            }
        }
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.print("Введите целое число: ");
            }
        }
    }

    private static void demoInvoke() {
        InvokeDemo d = new InvokeDemo();
        System.out.println("До: counter=" + d.getCounter());
        InvokeProcessor.invokeAll(d);
        System.out.println("После: counter=" + d.getCounter());
    }

    private static void demoDefault() {
        String classDefault = DefaultProcessor.readDefaultFromClass(DefaultDemo.class);
        System.out.println("Default на классе: " + classDefault);

        try {
            Field f = DefaultDemo.class.getDeclaredField("field");
            String fieldDefault = DefaultProcessor.readDefaultFromField(f);
            System.out.println("Default на поле: " + fieldDefault);
        } catch (NoSuchFieldException e) {
            System.out.println("Поле не найдено: " + e.getMessage());
        }
    }

    private static void demoToString(Scanner sc) {
        System.out.print("Имя: ");
        String name = sc.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Имя не может быть пустым. Имя: ");
            name = sc.nextLine().trim();
        }

        System.out.print("Пароль: ");
        String pass = sc.nextLine();

        System.out.print("Возраст (целое >=0): ");
        int age;
        while (true) {
            age = readInt(sc);
            if (age >= 0) break;
            System.out.print("Возраст должен быть >=0. Повторите: ");
        }

        Person p = new Person(name, pass, age);
        System.out.println(ToStringProcessor.build(p));
    }

    private static void demoValidate() {
        List<String> list = ValidateProcessor.readValidatedClasses(ValidateDemo.class);
        System.out.println("Классы в @Validate: " + list);
    }

    private static void demoTwo() {
        System.out.println(TwoProcessor.readTwo(TwoDemo.class));
    }

    private static void demoCache() {
        List<String> regions = CacheProcessor.readRegions(CacheDemoService.class);
        if (regions.isEmpty()) {
            System.out.println("Список кешируемых областей пуст.");
            return;
        }
        System.out.println("Кешируемые области: " + regions);

        CacheDemoService svc = new CacheDemoService();
        Map<String, Object> cache = new HashMap<>();
        svc.warmUp(cache);
        System.out.println("Кеш после warmUp(): " + cache);
    }
}
