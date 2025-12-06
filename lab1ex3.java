import java.util.Scanner;

public class lab1ex3 {

    // 1. Числа наоборот
    public static String reverseListNums(int x) {
        StringBuilder sb = new StringBuilder();
        for (int i = x; i >= 0; i--) {
            sb.append(i).append(" ");
        }
        return sb.toString().trim();
    }

    // 2. Четные числа
    public static String chet(int x) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= x; i += 2) {
            sb.append(i).append(" ");
        }
        return sb.toString().trim();
    }

    // 3. Одинаковость цифр
    public static boolean equalNum(int x) {
        x = Math.abs(x); // работаем с положительным числом
        int lastDigit = x % 10;
        while (x > 0) {
            if (x % 10 != lastDigit) return false;
            x /= 10;
        }
        return true;
    }

    // 4. Левый треугольник
    public static void leftTriangle(int x) {
        for (int i = 1; i <= x; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    // 5. Правый треугольник
    public static void rightTriangle(int x) {
        for (int i = 1; i <= x; i++) {
            for (int j = 0; j < x - i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Задача 1
        System.out.print("Задача 1. Введите число для обратного списка: ");
        int n1 = sc.nextInt();
        System.out.println("Числа наоборот: " + reverseListNums(n1));
        System.out.println();

        // Задача 2
        System.out.print("Задача 2. Введите число для вывода четных чисел: ");
        int n2 = sc.nextInt();
        System.out.println("Четные числа: " + chet(n2));
        System.out.println();

        // Задача 3
        System.out.print("Задача 3. Введите число для проверки одинаковых цифр: ");
        int n3 = sc.nextInt();
        System.out.println("Все цифры одинаковы? " + equalNum(n3));
        System.out.println();

        // Задача 4
        System.out.print("Задача 4. Введите число для левого треугольника: ");
        int n4 = sc.nextInt();
        System.out.println("Левый треугольник:");
        leftTriangle(n4);
        System.out.println();

        // Задача 5
        System.out.print("Задача 5. Введите число для правого треугольника: ");
        int n5 = sc.nextInt();
        System.out.println("Правый треугольник:");
        rightTriangle(n5);

        sc.close();
    }
}
