import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Лабораторная работа №5");
        System.out.println("=======================\n");
        
        while (true) {
            System.out.println("Выберите задание:");
            for (int i = 1; i <= 8; i++) {
                System.out.println(i + ". Задание " + i);
            }
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");
            
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Ошибка: введите число");
                continue;
            }
            
            int choice;
            try {
                choice = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число от 0 до 8");
                continue;
            }
            
            switch (choice) {
                case 1: task1(); break;
                case 2: task2(); break;
                case 3: task3(); break;
                case 4: task4(); break;
                case 5: task5(); break;
                case 6: task6(); break;
                case 7: task7(); break;
                case 8: task8(); break;
                case 0:
                    System.out.println("Программа завершена.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Ошибка: выберите число от 0 до 8");
            }
            
            System.out.println("\n" + "=".repeat(50) + "\n");
        }
    }
    
    private static void task1() {
        System.out.println("\n=== Задание 1: Дробь с кэшированием ===");
        
        Fraction frac1 = createFraction("первой");
        if (frac1 == null) return;
        
        System.out.println("Дробь 1: " + frac1);
        System.out.println("Вещественное значение: " + frac1.getRealValue());
        
        Fraction frac2 = createFraction("второй");
        if (frac2 == null) return;
        
        System.out.println("Дробь 2: " + frac2);
        System.out.println("Вещественное значение: " + frac2.getRealValue());
        
        modifyFraction(frac2);
        
        System.out.println("\nСравнение дробей:");
        System.out.println("Дробь 1 равна Дроби 2? " + frac1.equals(frac2));
        System.out.println("Хэш-код дроби 1: " + frac1.hashCode());
        System.out.println("Хэш-код дроби 2: " + frac2.hashCode());
    }
    
    private static Fraction createFraction(String order) {
        System.out.println("\nСоздание " + order + " дроби:");
        Integer numerator = readInteger("числитель");
        if (numerator == null) return null;
        
        Integer denominator = readInteger("знаменатель");
        if (denominator == null) return null;
        
        try {
            return new Fraction(numerator, denominator);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            return null;
        }
    }
    
    private static void modifyFraction(Fraction fraction) {
        System.out.print("\nИзменить числитель дроби? (да/нет): ");
        if (scanner.nextLine().equalsIgnoreCase("да")) {
            Integer newNum = readInteger("новый числитель");
            if (newNum != null) {
                fraction.setNumerator(newNum);
                System.out.println("Дробь после изменения: " + fraction);
                System.out.println("Новое вещественное значение: " + fraction.getRealValue());
            }
        }
        
        System.out.print("Изменить знаменатель дроби? (да/нет): ");
        if (scanner.nextLine().equalsIgnoreCase("да")) {
            Integer newDen = readInteger("новый знаменатель");
            if (newDen != null) {
                try {
                    fraction.setDenominator(newDen);
                    System.out.println("Дробь после изменения: " + fraction);
                    System.out.println("Новое вещественное значение: " + fraction.getRealValue());
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        }
    }
    
    private static void task2() {
        System.out.println("\n=== Задание 2: Количество мяуканий ===");
        
        Integer catCount = readInteger("количество котов");
        if (catCount == null || catCount <= 0) {
            System.out.println("Ошибка: количество котов должно быть больше 0");
            return;
        }
        
        List<Cat> cats = new ArrayList<>();
        for (int i = 1; i <= catCount; i++) {
            System.out.print("Введите имя кота " + i + ": ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                name = "Кот" + i;
            }
            cats.add(new Cat(name));
        }
        
        System.out.println("\nСозданы коты:");
        for (Cat cat : cats) {
            System.out.println(cat);
        }
        
        Integer meowTimes = readInteger("сколько раз каждый кот должен мяукнуть в методе");
        if (meowTimes == null || meowTimes < 0) {
            System.out.println("Ошибка: количество мяуканий не может быть отрицательным");
            return;
        }
        
        Map<Cat, Integer> beforeCounts = new HashMap<>();
        for (Cat cat : cats) {
            beforeCounts.put(cat, cat.getMeowCount());
        }
        
        System.out.println("\nПередаем котов в метод makeThemMeow()...");
        List<Meowable> meowables = new ArrayList<>(cats);
        
        makeThemMeow(meowables, meowTimes);
        
        System.out.println("\nРезультаты после работы метода makeThemMeow():");
        for (Cat cat : cats) {
            int meowedDuringMethod = cat.getMeowCount() - beforeCounts.get(cat);
            System.out.println(cat.getName() + " мяукал во время работы метода: " + meowedDuringMethod + " раз");
        }
        
        System.out.println("\nОбщая статистика:");
        int totalMeows = 0;
        for (Cat cat : cats) {
            System.out.println(cat.getName() + " всего мяукал: " + cat.getMeowCount() + " раз");
            totalMeows += cat.getMeowCount();
        }
        System.out.println("Всего мяуканий: " + totalMeows);
    }
    
    private static void makeThemMeow(List<Meowable> meowables, int times) {
        System.out.println("\n=== Начало работы метода makeThemMeow() ===");
        System.out.println("Метод получает " + meowables.size() + " мяукающих объектов");
        System.out.println("Каждый объект будет мяукать " + times + " раз");
        
        for (int i = 1; i <= times; i++) {
            System.out.println("\n--- Мяукание " + i + " из " + times + " ---");
            for (Meowable meowable : meowables) {
                meowable.meow();
            }
            
            if (i < times) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
            }
        }
        
        System.out.println("\n=== Конец работы метода makeThemMeow() ===\n");
    }
    
    private static void task3() {
        System.out.println("\n=== Задание 3: Первые вхождения элементов ===");
        
        System.out.println("Выберите тип элементов:");
        System.out.println("1. Числа (целые)");
        System.out.println("2. Строки");
        System.out.println("3. Дроби");
        System.out.print("Ваш выбор: ");
        
        Integer choice = readInteger("");
        if (choice == null || choice < 1 || choice > 3) {
            System.out.println("Ошибка: выберите число от 1 до 3");
            return;
        }
        
        switch (choice) {
            case 1: processNumbers(); break;
            case 2: processStrings(); break;
            case 3: processFractions(); break;
        }
    }
    
    private static void processNumbers() {
        System.out.print("Введите целые числа через пробел: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("Ошибка: введите хотя бы одно число");
            return;
        }
        
        List<Integer> numbers = new ArrayList<>();
        String[] parts = input.split("\\s+");
        for (String part : parts) {
            try {
                numbers.add(Integer.parseInt(part));
            } catch (NumberFormatException e) {
                System.out.println("Пропущен нечисловой элемент: '" + part + "'");
            }
        }
        
        if (numbers.isEmpty()) {
            System.out.println("Нет корректных чисел для обработки");
            return;
        }
        
        System.out.println("Исходный список: " + numbers);
        List<Integer> result = UniqueElementsProcessor.getFirstOccurrences(numbers);
        System.out.println("Только первые вхождения: " + result);
    }
    
    private static void processStrings() {
        System.out.print("Введите строки через запятую: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("Ошибка: введите хотя бы одну строку");
            return;
        }
        
        List<String> strings = Arrays.asList(input.split(","));
        strings = strings.stream()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
        
        if (strings.isEmpty()) {
            System.out.println("Нет строк для обработки");
            return;
        }
        
        System.out.println("Исходный список: " + strings);
        List<String> result = UniqueElementsProcessor.getFirstOccurrences(strings);
        System.out.println("Только первые вхождения: " + result);
    }
    
    private static void processFractions() {
        Integer fracCount = readInteger("количество дробей");
        if (fracCount == null || fracCount <= 0) {
            System.out.println("Ошибка: количество дробей должно быть больше 0");
            return;
        }
        
        List<Fraction> fractions = new ArrayList<>();
        for (int i = 1; i <= fracCount; i++) {
            System.out.println("Дробь " + i + ":");
            Fraction frac = createFraction("");
            if (frac != null) {
                fractions.add(frac);
            }
        }
        
        if (fractions.isEmpty()) {
            System.out.println("Нет дробей для обработки");
            return;
        }
        
        System.out.println("Исходный список дробей: " + fractions);
        List<Fraction> result = UniqueElementsProcessor.getFirstOccurrences(fractions);
        System.out.println("Только первые вхождения: " + result);
    }
    
    private static void task4() {
        System.out.println("\n=== Задание 4: Соревнования по многоборью ===");
        
        System.out.print("Введите имя файла (по умолчанию sportsmen.txt): ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            filename = "sportsmen.txt";
        }
        
        try {
            createTestFile(filename, 
                "3\n4\nИванов Сергей 100 30 78 13\nПетров Антон 90 16 98 14\nСидоров Юрий 100 70 30 21");
            
            List<SportsmanResult> results = SportsCompetitionProcessor.processResults(filename);
            
            System.out.println("\nРезультаты соревнований:");
            System.out.println("Фамилия      Имя         Сумма  Место");
            System.out.println("-----------------------------------");
            for (SportsmanResult s : results) {
                System.out.printf("%-12s %-12s %-6d %-5d%n", 
                    s.getLastName(), s.getFirstName(), s.getTotalScore(), s.getPlace());
            }
            
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private static void task5() {
        System.out.println("\n=== Задание 5: Символы в четных словах ===");
        
        System.out.print("Введите имя файла (по умолчанию text.txt): ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            filename = "text.txt";
        }
        
        try {
            createTestFile(filename, "Это пример текста на русском языке для проверки работы программы");
            
            EvenWordsAnalyzer analyzer = new EvenWordsAnalyzer(filename);
            Set<Character> characters = analyzer.getCharactersFromEvenWords();
            
            System.out.println("\nСимволы, встречающиеся в четных словах:");
            if (characters.isEmpty()) {
                System.out.println("Нет символов в четных словах");
            } else {
                System.out.println("Уникальные символы: " + characters);
                
                List<Character> sortedChars = new ArrayList<>(characters);
                Collections.sort(sortedChars);
                System.out.println("Отсортированные символы: " + sortedChars);
                System.out.println("Всего уникальных символов: " + characters.size());
            }
            
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private static void task6() {
        System.out.println("\n=== Задание 6: Обратный порядок очереди ===");
        
        System.out.println("Выберите тип элементов очереди:");
        System.out.println("1. Строки");
        System.out.println("2. Числа");
        System.out.print("Ваш выбор: ");
        
        Integer choice = readInteger("");
        if (choice == null || (choice != 1 && choice != 2)) {
            System.out.println("Ошибка: выберите 1 или 2");
            return;
        }
        
        if (choice == 1) {
            System.out.print("Введите строки через запятую: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Ошибка: введите хотя бы одну строку");
                return;
            }
            
            Queue<String> queue = new LinkedList<>();
            for (String element : input.split(",")) {
                String trimmed = element.trim();
                if (!trimmed.isEmpty()) {
                    queue.add(trimmed);
                }
            }
            
            processQueue(queue);
        } else {
            System.out.print("Введите числа через пробел: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Ошибка: введите хотя бы одно число");
                return;
            }
            
            Queue<Integer> queue = new LinkedList<>();
            for (String element : input.split("\\s+")) {
                try {
                    queue.add(Integer.parseInt(element.trim()));
                } catch (NumberFormatException e) {
                    System.out.println("Пропущен нечисловой элемент: '" + element + "'");
                }
            }
            
            processQueue(queue);
        }
    }
    
    private static <T> void processQueue(Queue<T> queue) {
        if (queue.isEmpty()) {
            System.out.println("Ошибка: очередь не может быть пустой");
            return;
        }
        
        System.out.println("Исходная очередь: " + queue);
        try {
            Queue<T> reversedQueue = QueueReversal.reverseQueue(queue);
            System.out.println("Очередь в обратном порядке: " + reversedQueue);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private static void task7() {
        System.out.println("\n=== Задание 7: Обработка точек через стрим ===");
        
        Integer pointCount = readInteger("количество точек");
        if (pointCount == null || pointCount <= 0) {
            System.out.println("Ошибка: количество точек должно быть больше 0");
            return;
        }
        
        List<Point> points = new ArrayList<>();
        for (int i = 1; i <= pointCount; i++) {
            System.out.println("Точка " + i + ":");
            
            Double x = readDouble("координата X");
            if (x == null) return;
            
            Double y = readDouble("координата Y");
            if (y == null) return;
            
            points.add(new Point(x, y));
        }
        
        System.out.println("\nИсходные точки: " + points);
        
        if (points.size() >= 2) {
            Line line = new Line(points.get(0), points.get(1));
            System.out.println("Линия из первых двух точек: " + line);
        }
        
        try {
            Polyline polyline = PointStreamProcessor.processPoints(points);
            System.out.println("\nРезультат обработки через стрим:");
            System.out.println("Ломаная линия: " + polyline);
        } catch (Exception e) {
            System.out.println("Ошибка при обработке точек: " + e.getMessage());
        }
    }
    
    private static void task8() {
        System.out.println("\n=== Задание 8: Группировка людей по номерам ===");
        
        System.out.print("Введите имя файла (по умолчанию people.txt): ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            filename = "people.txt";
        }
        
        try {
            createTestFile(filename, 
                "Вася:5\nПетя:3\nАня:5\nМаша:\nКоля:3\nОля:\nСергей:7\nанна:2\nИВАН:4");
            
            PeopleGrouper grouper = new PeopleGrouper(filename);
            Map<Integer, List<String>> groupedPeople = grouper.groupPeopleByNumber();
            
            System.out.println("\nРезультат группировки людей по номерам:");
            if (groupedPeople.isEmpty()) {
                System.out.println("Нет людей с корректными номерами");
            } else {
                groupedPeople.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    });
            }
            
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private static Integer readInteger(String prompt) {
        System.out.print(prompt.isEmpty() ? "Введите число: " : "Введите " + prompt + ": ");
        String input = scanner.nextLine().trim();
        
        if (input.isEmpty()) {
            System.out.println("Ошибка: поле не может быть пустым");
            return null;
        }
        
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите целое число");
            return null;
        }
    }
    
    private static Double readDouble(String prompt) {
        System.out.print("Введите " + prompt + ": ");
        String input = scanner.nextLine().trim();
        
        if (input.isEmpty()) {
            System.out.println("Ошибка: поле не может быть пустым");
            return null;
        }
        
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите число");
            return null;
        }
    }
    
    private static void createTestFile(String filename, String content) throws IOException {
        Path filePath = Paths.get(filename);
        if (!Files.exists(filePath)) {
            Files.write(filePath, content.getBytes());
            System.out.println("Создан тестовый файл: " + filePath.toAbsolutePath());
        }
    }
}

interface FractionOperations {
    double getRealValue();
    void setNumerator(int numerator);
    void setDenominator(int denominator);
}

class Fraction implements FractionOperations {
    private int numerator;
    private int denominator;
    private Double cachedValue = null;
    
    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Знаменатель не может быть равен нулю");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        normalize();
    }
    
    private void normalize() {
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        cachedValue = null;
    }
    
    @Override
    public double getRealValue() {
        if (cachedValue == null) {
            cachedValue = (double) numerator / denominator;
        }
        return cachedValue;
    }
    
    @Override
    public void setNumerator(int numerator) {
        this.numerator = numerator;
        cachedValue = null;
    }
    
    @Override
    public void setDenominator(int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Знаменатель не может быть равен нулю");
        }
        this.denominator = denominator;
        normalize();
    }
    
    public int getNumerator() { return numerator; }
    public int getDenominator() { return denominator; }
    
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fraction fraction = (Fraction) obj;
        return numerator == fraction.numerator && denominator == fraction.denominator;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
}

interface Meowable {
    void meow();
}

class Cat implements Meowable {
    private String name;
    private int meowCount = 0;
    
    public Cat(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя кота не может быть пустым");
        }
        this.name = name.trim();
    }
    
    @Override
    public void meow() {
        System.out.println(name + ": мяу!");
        meowCount++;
    }
    
    public int getMeowCount() {
        return meowCount;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "кот: " + name;
    }
}

class UniqueElementsProcessor {
    public static <T> List<T> getFirstOccurrences(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }
        
        List<T> result = new ArrayList<>();
        Set<T> seen = new HashSet<>();
        
        for (T element : list) {
            if (!seen.contains(element)) {
                result.add(element);
                seen.add(element);
            }
        }
        
        return result;
    }
}

class SportsmanResult {
    private String lastName;
    private String firstName;
    private int totalScore;
    private int place;
    
    public SportsmanResult(String lastName, String firstName, int totalScore) {
        if (lastName == null || firstName == null) {
            throw new IllegalArgumentException("Фамилия и имя не могут быть null");
        }
        if (lastName.length() > 20) {
            throw new IllegalArgumentException("Фамилия не может быть длиннее 20 символов");
        }
        if (firstName.length() > 12) {
            throw new IllegalArgumentException("Имя не может быть длиннее 12 символов");
        }
        
        this.lastName = lastName;
        this.firstName = firstName;
        this.totalScore = totalScore;
    }
    
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public int getTotalScore() { return totalScore; }
    public int getPlace() { return place; }
    public void setPlace(int place) { this.place = place; }
    
    @Override
    public String toString() {
        return String.format("%s %s %d %d", lastName, firstName, totalScore, place);
    }
}

class SportsCompetitionProcessor {
    public static List<SportsmanResult> processResults(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        
        if (lines.size() < 2) {
            throw new IllegalArgumentException("Файл должен содержать минимум 2 строки");
        }
        
        int n, m;
        try {
            n = Integer.parseInt(lines.get(0).trim());
            m = Integer.parseInt(lines.get(1).trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат чисел в первых двух строках");
        }
        
        if (n <= 0 || n >= 1000) {
            throw new IllegalArgumentException("Количество спортсменов должно быть: 0 < N < 1000");
        }
        if (m <= 0) {
            throw new IllegalArgumentException("Количество видов спорта должно быть положительным");
        }
        
        List<SportsmanResult> sportsmen = new ArrayList<>();
        
        for (int i = 2; i < 2 + n && i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            
            String[] parts = line.split("\\s+");
            
            if (parts.length < m + 2) {
                throw new IllegalArgumentException("Неверный формат данных в строке " + (i + 1));
            }
            
            String lastName = parts[0];
            String firstName = parts[1];
            int totalScore = 0;
            
            for (int j = 0; j < m; j++) {
                try {
                    totalScore += Integer.parseInt(parts[2 + j]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Некорректное число баллов в строке " + (i + 1));
                }
            }
            
            sportsmen.add(new SportsmanResult(lastName, firstName, totalScore));
        }
        
        sportsmen.sort((s1, s2) -> Integer.compare(s2.getTotalScore(), s1.getTotalScore()));
        
        int currentPlace = 1;
        for (int i = 0; i < sportsmen.size(); i++) {
            if (i > 0 && sportsmen.get(i).getTotalScore() != sportsmen.get(i-1).getTotalScore()) {
                currentPlace = i + 1;
            }
            sportsmen.get(i).setPlace(currentPlace);
        }
        
        return sportsmen;
    }
}

class EvenWordsAnalyzer {
    private String filename;
    
    public EvenWordsAnalyzer(String filename) {
        this.filename = filename;
    }
    
    public Set<Character> getCharactersFromEvenWords() throws IOException {
        String content = Files.readString(Paths.get(filename));
        String[] words = content.split("\\s+");
        
        Set<Character> characters = new HashSet<>();
        
        for (int i = 1; i < words.length; i += 2) {
            String word = words[i];
            for (char c : word.toCharArray()) {
                characters.add(c);
            }
        }
        
        return characters;
    }
    
    @Override
    public String toString() {
        return "EvenWordsAnalyzer{filename='" + filename + "'}";
    }
}

class QueueReversal {
    public static <T> Queue<T> reverseQueue(Queue<T> queue) {
        if (queue == null) {
            throw new IllegalArgumentException("Очередь не может быть null");
        }
        if (queue.isEmpty()) {
            throw new IllegalArgumentException("Очередь не может быть пустой");
        }
        
        Stack<T> stack = new Stack<>();
        Queue<T> result = new LinkedList<>();
        
        Queue<T> tempQueue = new LinkedList<>(queue);
        
        while (!tempQueue.isEmpty()) {
            stack.push(tempQueue.poll());
        }
        
        while (!stack.isEmpty()) {
            result.offer(stack.pop());
        }
        
        return result;
    }
}

class Point {
    private double x;
    private double y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    @Override
    public String toString() {
        return String.format("{%.1f;%.1f}", x, y);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class Line {
    private Point start;
    private Point end;
    
    public Line(Point start, Point end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Точки не могут быть null");
        }
        this.start = start;
        this.end = end;
    }
    
    public Point getStart() { return start; }
    public Point getEnd() { return end; }
    
    @Override
    public String toString() {
        return "Линия от " + start + " до " + end;
    }
}

class Polyline {
    private List<Point> points;
    
    public Polyline(List<Point> points) {
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException("Ломаная должна содержать хотя бы одну точку");
        }
        this.points = new ArrayList<>(points);
    }
    
    public List<Point> getPoints() { return points; }
    
    @Override
    public String toString() {
        return "Линия [" + points.stream()
            .map(Point::toString)
            .collect(Collectors.joining(",")) + "]";
    }
}

class PointStreamProcessor {
    public static Polyline processPoints(List<Point> points) {
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException("Список точек не может быть пустым");
        }
        
        return points.stream()
            .distinct()
            .sorted(Comparator.comparingDouble(Point::getX))
            .map(p -> new Point(p.getX(), Math.abs(p.getY())))
            .collect(Collectors.collectingAndThen(
                Collectors.toList(),
                Polyline::new
            ));
    }
}

class PeopleGrouper {
    private String filename;
    
    public PeopleGrouper(String filename) {
        this.filename = filename;
    }
    
    public Map<Integer, List<String>> groupPeopleByNumber() throws IOException {
        return Files.lines(Paths.get(filename))
            .filter(line -> line.contains(":"))
            .map(line -> line.split(":"))
            .filter(parts -> parts.length == 2)
            .filter(parts -> !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty())
            .map(parts -> {
                String name = parts[0].trim();
                String numberStr = parts[1].trim();
                
                if (!name.isEmpty()) {
                    name = name.substring(0, 1).toUpperCase() + 
                           name.substring(1).toLowerCase();
                }
                
                try {
                    int number = Integer.parseInt(numberStr);
                    return new AbstractMap.SimpleEntry<>(number, name);
                } catch (NumberFormatException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(
                Map.Entry::getKey,
                Collectors.mapping(Map.Entry::getValue, Collectors.toList())
            ));
    }
    
    @Override
    public String toString() {
        return "PeopleGrouper{filename='" + filename + "'}";
    }
}