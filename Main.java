import java.util.*;
import java.util.function.*;

class Box<T> {
    private T value;
    
    public Box() {
        this.value = null;
    }
    
    public Box(T value) {
        this.value = value;
    }
    
    public T get() {
        return value;
    }
    
    public void put(T newValue) {
        if (value != null) {
            throw new IllegalStateException("Коробка уже содержит значение!");
        }
        this.value = newValue;
    }
    
    public T take() {
        T temp = value;
        value = null;
        return temp;
    }
    
    public boolean isEmpty() {
        return value == null;
    }
    
    public boolean isFull() {
        return value != null;
    }
    
    @Override
    public String toString() {
        return "Box[" + (value != null ? value.toString() : "пусто") + "]";
    }
}

class Storage<T> {
    private final T value;
    private final T alternative;
    
    public Storage(T value, T alternative) {
        this.value = value;
        this.alternative = alternative;
    }
    
    public T get() {
        return value != null ? value : alternative;
    }
    
    @Override
    public String toString() {
        return "Storage[value=" + value + ", alternative=" + alternative + "]";
    }
}

class BoxUtils {
    public static double findMax(List<? extends Box<? extends Number>> boxes) {
        if (boxes == null || boxes.isEmpty()) {
            throw new IllegalArgumentException("Список коробок пуст или null");
        }
        
        double max = Double.NEGATIVE_INFINITY;
        for (Box<? extends Number> box : boxes) {
            if (box.isFull()) {
                Number value = box.get();
                if (value != null) {
                    double doubleValue = value.doubleValue();
                    if (doubleValue > max) {
                        max = doubleValue;
                    }
                }
            }
        }
        
        return max;
    }
}

class FunctionalUtils {
    public static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        List<R> result = new ArrayList<>();
        for (T item : list) {
            result.add(function.apply(item));
        }
        return result;
    }
    
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }
    
    public static <T> T reduce(List<T> list, BinaryOperator<T> operator) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        
        T result = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            result = operator.apply(result, list.get(i));
        }
        return result;
    }
    
    public static <T> T reduce(List<T> list, T identity, BinaryOperator<T> operator) {
        T result = identity;
        for (T item : list) {
            result = operator.apply(result, item);
        }
        return result;
    }
    
    public static <T, P> P collect(List<T> list, 
                                   Supplier<P> supplier, 
                                   BiConsumer<P, T> accumulator) {
        P result = supplier.get();
        for (T item : list) {
            accumulator.accept(result, item);
        }
        return result;
    }
}

public class Lab4 {
    public static void processNumberStorage(Storage<Integer> storage) {
        System.out.println("Извлечено из хранилища: " + storage.get());
    }
    
    public static void processStringStorage(Storage<String> storage) {
        System.out.println("Извлечено из хранилища: " + storage.get());
    }
    
    public static void processBoxes(List<Box<? extends Number>> boxes) {
        double max = BoxUtils.findMax(boxes);
        System.out.println("Максимальное значение в коробках: " + max);
    }
    
    static class StringLengthFunction implements Function<String, Integer> {
        @Override
        public Integer apply(String s) {
            return s.length();
        }
    }
    
    static class AbsoluteValueFunction implements Function<Integer, Integer> {
        @Override
        public Integer apply(Integer x) {
            return Math.abs(x);
        }
    }
    
    static class MaxArrayFunction implements Function<int[], Integer> {
        @Override
        public Integer apply(int[] arr) {
            if (arr == null || arr.length == 0) return 0;
            int max = arr[0];
            for (int num : arr) {
                if (num > max) max = num;
            }
            return max;
        }
    }
    
    static class StringLengthPredicate implements Predicate<String> {
        @Override
        public boolean test(String s) {
            return s.length() >= 3;
        }
    }
    
    static class PositiveNumberPredicate implements Predicate<Integer> {
        @Override
        public boolean test(Integer x) {
            return x > 0;
        }
    }
    
    static class NoPositiveArrayPredicate implements Predicate<int[]> {
        @Override
        public boolean test(int[] arr) {
            for (int num : arr) {
                if (num > 0) return false;
            }
            return true;
        }
    }
    
    static class StringConcatenator implements BinaryOperator<String> {
        @Override
        public String apply(String s1, String s2) {
            return s1 + s2;
        }
    }
    
    static class IntegerSum implements BinaryOperator<Integer> {
        @Override
        public Integer apply(Integer a, Integer b) {
            return a + b;
        }
    }
    
    static class NumberClassifier implements BiConsumer<Map<String, List<Integer>>, Integer> {
        @Override
        public void accept(Map<String, List<Integer>> map, Integer number) {
            String key = number > 0 ? "positive" : "negative";
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(number);
        }
    }
    
    static class StringLengthGrouper implements BiConsumer<Map<Integer, List<String>>, String> {
        @Override
        public void accept(Map<Integer, List<String>> map, String str) {
            int length = str.length();
            map.computeIfAbsent(length, k -> new ArrayList<>()).add(str);
        }
    }
    
    public static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка! Введите целое число.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }
    
    public static String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        scanner.nextLine();
        return scanner.nextLine();
    }
    
    public static void demoBoxInteractive(Scanner scanner) {
        System.out.println("\n=== Демонстрация работы с Коробкой ===");
        
        Box<Integer> intBox = new Box<>();
        
        System.out.println("1. Создана пустая коробка для целых чисел");
        System.out.println("   Коробка пуста? " + intBox.isEmpty());
        System.out.println("   Коробка заполнена? " + intBox.isFull());
        
        int value = readInt(scanner, "\n2. Введите число для размещения в коробке: ");
        intBox.put(value);
        System.out.println("   Число " + value + " размещено в коробке");
        System.out.println("   Коробка пуста? " + intBox.isEmpty());
        System.out.println("   Коробка заполнена? " + intBox.isFull());
        System.out.println("   Содержимое коробки: " + intBox.get());
        
        System.out.println("\n3. Пытаемся разместить еще одно число...");
        try {
            intBox.put(999);
        } catch (IllegalStateException e) {
            System.out.println("   Ошибка: " + e.getMessage());
        }
        
        System.out.println("\n4. Извлекаем значение из коробки...");
        Integer extracted = intBox.take();
        System.out.println("   Извлечено: " + extracted);
        System.out.println("   Коробка пуста? " + intBox.isEmpty());
        System.out.println("   Коробка заполнена? " + intBox.isFull());
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== ЛАБОРАТОРНАЯ РАБОТА №4 ===");
        
        System.out.println("=== ЗАДАНИЕ 1 ===");
        
        demoBoxInteractive(scanner);
        
        System.out.println("\n--- Часть 1.1 (автоматическая демонстрация) ---");
        Box<Integer> intBox = new Box<>();
        intBox.put(3);
        System.out.println("Коробка с числом 3: " + intBox);
        
        System.out.println("Передача коробки в метод и извлечение значения...");
        Integer extractedValue = intBox.take();
        System.out.println("Извлеченное значение: " + extractedValue);
        
        System.out.println("\n--- Часть 1.2: Хранилище ---");
        
        Storage<Integer> nullNumberStorage = new Storage<>(null, 0);
        System.out.println("1. Хранилище чисел (null, альтернатива 0):");
        processNumberStorage(nullNumberStorage);
        
        Storage<Integer> numberStorage = new Storage<>(99, -1);
        System.out.println("\n2. Хранилище чисел (99, альтернатива -1):");
        processNumberStorage(numberStorage);
        
        Storage<String> nullStringStorage = new Storage<>(null, "default");
        System.out.println("\n3. Хранилище строк (null, альтернатива 'default'):");
        processStringStorage(nullStringStorage);
        
        Storage<String> stringStorage = new Storage<>("hello", "default");
        System.out.println("\n4. Хранилище строк ('hello', альтернатива 'default'):");
        processStringStorage(stringStorage);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        System.out.println("=== ЗАДАНИЕ 2: ПОИСК МАКСИМУМА ===");
        
        List<Box<? extends Number>> boxes = new ArrayList<>();
        
        Box<Integer> box1 = new Box<>(10);
        Box<Double> box2 = new Box<>(25.5);
        Box<Long> box3 = new Box<>(100L);
        Box<Float> box4 = new Box<>(15.75f);
        Box<Integer> box5 = new Box<>();
        
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        boxes.add(box4);
        boxes.add(box5);
        
        System.out.println("Список коробок:");
        for (int i = 0; i < boxes.size(); i++) {
            System.out.println("  Коробка " + (i+1) + ": " + boxes.get(i));
        }
        
        System.out.println("\nПоиск максимального значения...");
        try {
            double max = BoxUtils.findMax(boxes);
            System.out.println("Максимальное значение: " + max);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        System.out.println("=== ЗАДАНИЕ 3: ФУНКЦИОНАЛЬНОЕ ПРОГРАММИРОВАНИЕ ===");
        
        System.out.println("\n--- 3.1 ФУНКЦИЯ (map) ---");
        
        List<String> strings1 = Arrays.asList("qwerty", "asdfg", "zx");
        System.out.println("Пример 1 - Исходный список строк: " + strings1);
        
        List<Integer> lengths = FunctionalUtils.map(strings1, new StringLengthFunction());
        System.out.println("Длины строк: " + lengths);
        
        List<Integer> numbers1 = Arrays.asList(1, -3, 7);
        System.out.println("\nПример 2 - Исходный список чисел: " + numbers1);
        
        List<Integer> absolutes = FunctionalUtils.map(numbers1, new AbsoluteValueFunction());
        System.out.println("Абсолютные значения: " + absolutes);
        
        List<int[]> arrays = Arrays.asList(
            new int[]{1, 2, 3},
            new int[]{-5, 0, 5},
            new int[]{10, 20, 30, 40}
        );
        System.out.println("\nПример 3 - Исходный список массивов: ");
        for (int[] arr : arrays) {
            System.out.println("  " + Arrays.toString(arr));
        }
        
        List<Integer> maxValues = FunctionalUtils.map(arrays, new MaxArrayFunction());
        System.out.println("Максимальные значения массивов: " + maxValues);
        
        System.out.println("\n--- 3.2 ФИЛЬТР ---");
        
        List<String> strings2 = Arrays.asList("qwerty", "asdfg", "zx");
        System.out.println("Пример 1 - Исходный список строк: " + strings2);
        
        List<String> filteredStrings = FunctionalUtils.filter(strings2, new StringLengthPredicate());
        System.out.println("Строки длиной >= 3: " + filteredStrings);
        
        List<Integer> numbers2 = Arrays.asList(1, -3, 7);
        System.out.println("\nПример 2 - Исходный список чисел: " + numbers2);
        
        List<Integer> positiveNumbers = FunctionalUtils.filter(numbers2, new PositiveNumberPredicate());
        System.out.println("Положительные числа: " + positiveNumbers);
        
        List<int[]> arrays2 = Arrays.asList(
            new int[]{-1, -2, -3},
            new int[]{-5, 0, 5},
            new int[]{-10, -20},
            new int[]{1, 2, 3}
        );
        System.out.println("\nПример 3 - Исходный список массивов: ");
        for (int[] arr : arrays2) {
            System.out.println("  " + Arrays.toString(arr));
        }
        
        List<int[]> noPositiveArrays = FunctionalUtils.filter(arrays2, new NoPositiveArrayPredicate());
        System.out.println("Массивы без положительных элементов: ");
        for (int[] arr : noPositiveArrays) {
            System.out.println("  " + Arrays.toString(arr));
        }
        
        System.out.println("\n--- 3.3 СОКРАЩЕНИЕ (reduce) ---");
        
        List<String> strings3 = Arrays.asList("qwerty", "asdfg", "zx");
        System.out.println("Пример 1 - Исходный список строк: " + strings3);
        
        String concatenated = FunctionalUtils.reduce(strings3, new StringConcatenator());
        System.out.println("Объединенная строка: " + concatenated);
        
        List<Integer> numbers3 = Arrays.asList(1, -3, 7);
        System.out.println("\nПример 2 - Исходный список чисел: " + numbers3);
        
        Integer sum = FunctionalUtils.reduce(numbers3, new IntegerSum());
        System.out.println("Сумма чисел: " + sum);
        
        List<List<Integer>> listOfLists = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5),
            Arrays.asList(6, 7, 8, 9)
        );
        System.out.println("\nПример 3 - Исходный список списков: " + listOfLists);
        
        List<Integer> lengthsOfLists = FunctionalUtils.map(listOfLists, List::size);
        System.out.println("Длины списков: " + lengthsOfLists);
        
        Integer totalElements = FunctionalUtils.reduce(lengthsOfLists, new IntegerSum());
        System.out.println("Общее количество элементов: " + totalElements);
        
        System.out.println("\n--- Безопасная версия reduce (с identity) ---");
        
        List<String> emptyList = new ArrayList<>();
        System.out.println("Пустой список: " + emptyList);
        
        String safeResult = FunctionalUtils.reduce(emptyList, "", new StringConcatenator());
        System.out.println("Результат конкатенации (с identity): " + safeResult);
        
        System.out.println("\n--- 3.4 КОЛЛЕКЦИОНИРОВАНИЕ ---");
        
        List<Integer> numbers4 = Arrays.asList(1, -3, 7, -2, 0, -5, 10);
        System.out.println("Пример 1 - Исходный список чисел: " + numbers4);
        
        Map<String, List<Integer>> numberGroups = FunctionalUtils.collect(
            numbers4,
            HashMap::new,
            new NumberClassifier()
        );
        
        System.out.println("Разделение чисел:");
        System.out.println("  Положительные: " + numberGroups.getOrDefault("positive", new ArrayList<>()));
        System.out.println("  Отрицательные: " + numberGroups.getOrDefault("negative", new ArrayList<>()));
        
        List<String> strings4 = Arrays.asList("qwerty", "asdfg", "zx", "qw");
        System.out.println("\nПример 2 - Исходный список строк: " + strings4);
        
        Map<Integer, List<String>> stringGroups = FunctionalUtils.collect(
            strings4,
            HashMap::new,
            new StringLengthGrouper()
        );
        
        System.out.println("Группировка строк по длине:");
        for (Map.Entry<Integer, List<String>> entry : stringGroups.entrySet()) {
            System.out.println("  Длина " + entry.getKey() + ": " + entry.getValue());
        }
        
        List<String> strings5 = Arrays.asList("qwerty", "asdfg", "qwerty", "qw");
        System.out.println("\nПример 3 - Исходный список строк (с дубликатами): " + strings5);
        
        Set<String> uniqueStrings = FunctionalUtils.collect(
            strings5,
            HashSet::new,
            Set::add
        );
        
        System.out.println("Уникальные строки: " + uniqueStrings);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("=== ИНТЕРАКТИВНАЯ ДЕМОНСТРАЦИЯ ===");
        
        System.out.println("\nДемонстрация функциональных методов с вводом данных:");
        
        System.out.println("\n1. Введите список чисел через пробел:");
        scanner.nextLine();
        String numbersInput = scanner.nextLine();
        
        List<Integer> userNumbers = new ArrayList<>();
        try {
            String[] numberStrings = numbersInput.split("\\s+");
            for (String numStr : numberStrings) {
                if (!numStr.trim().isEmpty()) {
                    userNumbers.add(Integer.parseInt(numStr.trim()));
                }
            }
            
            System.out.println("Ваш список чисел: " + userNumbers);
            
            List<Integer> userAbsolutes = FunctionalUtils.map(userNumbers, new AbsoluteValueFunction());
            System.out.println("Абсолютные значения: " + userAbsolutes);
            
            List<Integer> userPositives = FunctionalUtils.filter(userNumbers, new PositiveNumberPredicate());
            System.out.println("Положительные числа: " + userPositives);
            
            Integer userSum = FunctionalUtils.reduce(userNumbers, 0, new IntegerSum());
            System.out.println("Сумма всех чисел: " + userSum);
            
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при разборе чисел!");
        }
        
        System.out.println("\n2. Введите список строк (каждую с новой строки, пустая строка для завершения):");
        List<String> userStrings = new ArrayList<>();
        
        while (true) {
            System.out.print("Строка: ");
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            userStrings.add(line);
        }
        
        if (!userStrings.isEmpty()) {
            System.out.println("Ваш список строк: " + userStrings);
            
            List<Integer> stringLengths = FunctionalUtils.map(userStrings, new StringLengthFunction());
            System.out.println("Длины строк: " + stringLengths);
            
            List<String> longStrings = FunctionalUtils.filter(userStrings, s -> s.length() >= 3);
            System.out.println("Строки длиной >= 3: " + longStrings);
            
            String concatenatedUser = FunctionalUtils.reduce(userStrings, "", new StringConcatenator());
            System.out.println("Объединенная строка: " + concatenatedUser);
        }
        
        scanner.close();
        System.out.println("\n=== ПРОГРАММА ЗАВЕРШЕНА ===");
    }
}
