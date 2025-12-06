import java.util.Scanner;

public class lab1ex2 {

    // 1. Модуль числа
    public static int abs(int x) {
        return (x < 0) ? -x : x;
    }

    // 2. Строка сравнения
    public static String makeDecision(int x, int y) {
        if (x > y) return x + " > " + y;
        else if (x < y) return x + " < " + y;
        else return x + "==" + y;
    }

    // 3. Тройной максимум
    public static int max3(int x, int y, int z) {
        int max = (x > y) ? x : y;
        if (z > max) max = z;
        return max;
    }

    // 4. Двойная сумма
    public static int sum2(int x, int y) {
        int sum = x + y;
        return (sum >= 10 && sum <= 19) ? 20 : sum;
    }

    // 5. День недели
    public static String day(int x) {
        switch (x) {
            case 1: return "понедельник";
            case 2: return "вторник";
            case 3: return "среда";
            case 4: return "четверг";
            case 5: return "пятница";
            case 6: return "суббота";
            case 7: return "воскресенье";
            default: return "это не день недели";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Задача 1
        System.out.print("Задача 1. Введите число для модуля: ");
        int num1 = sc.nextInt();
        System.out.println("Модуль числа: " + abs(num1));
        System.out.println();

        // Задача 2
        System.out.print("Задача 2. Введите два числа для сравнения: ");
        int x2 = sc.nextInt();
        int y2 = sc.nextInt();
        System.out.println("Результат сравнения: " + makeDecision(x2, y2));
        System.out.println();

        // Задача 3
        System.out.print("Задача 3. Введите три числа для нахождения максимума: ");
        int x3 = sc.nextInt();
        int y3 = sc.nextInt();
        int z3 = sc.nextInt();
        System.out.println("Максимальное число: " + max3(x3, y3, z3));
        System.out.println();

        // Задача 4
        System.out.print("Задача 4. Введите два числа для двойной суммы: ");
        int x4 = sc.nextInt();
        int y4 = sc.nextInt();
        System.out.println("Результат двойной суммы: " + sum2(x4, y4));
        System.out.println();

        // Задача 5
        System.out.print("Задача 5. Введите число для дня недели: ");
        int x5 = sc.nextInt();
        System.out.println("День недели: " + day(x5));

        sc.close();
    }
}
