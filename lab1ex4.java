import java.util.Scanner;
import java.util.Arrays;

public class lab1ex4 {

    // 1. Поиск первого вхождения
    public static int findFirst(int[] arr, int x) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) return i;
        }
        return -1;
    }

    // 2. Поиск последнего вхождения
    public static int findLast(int[] arr, int x) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == x) return i;
        }
        return -1;
    }

    // 3. Добавление в массив
    public static int[] add(int[] arr, int x, int pos) {
        if (pos < 0) pos = 0;
        if (pos > arr.length) pos = arr.length;

        int[] newArr = new int[arr.length + 1];
        for (int i = 0; i < pos; i++) {
            newArr[i] = arr[i];
        }
        newArr[pos] = x;
        for (int i = pos; i < arr.length; i++) {
            newArr[i + 1] = arr[i];
        }
        return newArr;
    }

    // 4. Реверс массива
    public static int[] reverseBack(int[] arr) {
        int[] newArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[arr.length - 1 - i];
        }
        return newArr;
    }

    // 5. Удаление негативных чисел
    public static int[] deleteNegative(int[] arr) {
        int count = 0;
        for (int val : arr) {
            if (val >= 0) count++;
        }
        int[] newArr = new int[count];
        int index = 0;
        for (int val : arr) {
            if (val >= 0) {
                newArr[index++] = val;
            }
        }
        return newArr;
    }

    // Метод для ввода массива
    public static int[] inputArray(Scanner sc) {
        System.out.print("Введите размер массива: ");
        int n = sc.nextInt();
        int[] arr = new int[n];
        System.out.print("Введите элементы массива: ");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        return arr;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ввод массива
        int[] arr = inputArray(sc);
        System.out.println("Исходный массив: " + Arrays.toString(arr));
        System.out.println();

        // Задача 1: Поиск первого значения
        System.out.print("Задача 1. Введите число для поиска первого вхождения: ");
        int x1 = sc.nextInt();
        System.out.println("Индекс первого вхождения: " + findFirst(arr, x1));
        System.out.println();

        // Задача 2: Поиск последнего значения
        System.out.print("Задача 2. Введите число для поиска последнего вхождения: ");
        int x2 = sc.nextInt();
        System.out.println("Индекс последнего вхождения: " + findLast(arr, x2));
        System.out.println();

        // Задача 3: Добавление числа
        System.out.print("Задача 3. Введите число для добавления: ");
        int x3 = sc.nextInt();
        System.out.print("Введите позицию для вставки: ");
        int pos = sc.nextInt();
        int[] arrAdded = add(arr, x3, pos);
        System.out.println("Массив после добавления: " + Arrays.toString(arrAdded));
        System.out.println();

        // Задача 4: Реверс массива
        int[] arrReversed = reverseBack(arr);
        System.out.println("Реверс массива: " + Arrays.toString(arrReversed));
        System.out.println();

        // Задача 5: Удаление негативных чисел
        int[] arrNoNeg = deleteNegative(arr);
        System.out.println("Массив без отрицательных чисел: " + Arrays.toString(arrNoNeg));

        sc.close();
    }
}
