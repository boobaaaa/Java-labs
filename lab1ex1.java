import java.util.Scanner;

public class lab1ex1 {

    // 1. Преобразование символа '0'-'9' в число
    public static int charToNum(char x) {
        return x - '0';
    }

    // 2. Проверка, является ли число положительным
    public static boolean isPositive(int x) {
        return x > 0;
    }

    // 3. Проверка, является ли символ большой буквой A-Z
    public static boolean isUpperCase(char x) {
        return x >= 'A' && x <= 'Z';
    }

    // 4. Проверка, делит ли одно число другое нацело
    public static boolean isDivisor(int a, int b) {
        if (a == 0 || b == 0) return false; // деление на ноль невозможно
        return a % b == 0 || b % a == 0;
    }

    // 5. Сумма последних цифр двух чисел
    public static int lastNumSum(int a, int b) {
        return (a % 10) + (b % 10);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Задача 1
        System.out.print("Задача 1. Введите символ от '0' до '9': ");
        char c1 = sc.next().charAt(0);
        System.out.println("Преобразование символа в число: " + charToNum(c1));
        System.out.println();

        // Задача 2
        System.out.print("Задача 2. Введите число для проверки на позитив: ");
        int n2 = sc.nextInt();
        System.out.println("Число положительное? " + isPositive(n2));
        System.out.println();

        // Задача 3
        System.out.print("Задача 3. Введите символ для проверки на заглавную букву: ");
        char c3 = sc.next().charAt(0);
        System.out.println("Это заглавная буква? " + isUpperCase(c3));
        System.out.println();

        // Задача 4
        System.out.print("Задача 4. Введите два числа для проверки делимости: ");
        int a4 = sc.nextInt();
        int b4 = sc.nextInt();
        System.out.println("Любое число делит другое нацело? " + isDivisor(a4, b4));
        System.out.println();

        // Задача 5
        System.out.println("Задача 5. Последовательное суммирование пяти чисел по последней цифре.");
        int[] numbers = new int[5];
        for (int i = 0; i < 5; i++) {
            System.out.print("Введите число #" + (i + 1) + ": ");
            numbers[i] = sc.nextInt();
        }

        // Последовательное суммирование последних цифр
        int result = lastNumSum(numbers[0], numbers[1]);
        System.out.println(numbers[0] + " + " + numbers[1] + " = " + result);
        for (int i = 2; i < 5; i++) {
            int prev = result;
            result = lastNumSum(result, numbers[i]);
            System.out.println(prev + " + " + numbers[i] + " = " + result);
        }
        System.out.println("Итого " + result);

        sc.close();
    }
}
