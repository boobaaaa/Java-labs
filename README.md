# Петрик Илья ИТ-3 Лабораторная №5

## **Задание 1: Дробь с кэшированием**

### **Текст задачи**
В класс Дробь добавить интерфейс на два метода: получение вещественного значения, установка числителя и установка знаменателя. Сгенерировать версию дроби, которая будет кэшировать вычисление вещественного значения.

### **Алгоритм решения**
1. **Создан интерфейс `FractionOperations`** с методами:
   ```java
   interface FractionOperations {
       double getRealValue();
       void setNumerator(int numerator);
       void setDenominator(int denominator);
   }
   ```

2. **Реализован класс `Fraction`** с:
   - Полями `numerator`, `denominator`, `cachedValue`
   - Конструктором с проверкой знаменателя на ноль
   - Нормализацией отрицательных значений (знаменатель всегда положительный)
   - Кэшированием вещественного значения
   - Переопределенными методами `equals()` и `hashCode()` для сравнения по числителю и знаменателю
   - Методом `toString()` в формате "числитель/знаменатель"

   ```java
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
       
       @Override
       public double getRealValue() {
           if (cachedValue == null) {
               cachedValue = (double) numerator / denominator;
           }
           return cachedValue;
       }
   }
   ```

3. **Демонстрация работы** в методе `task1()`:
   - Создание двух дробей с вводом от пользователя
   - Изменение числителя и знаменателя с проверкой кэширования
   - Сравнение дробей через `equals()` и `hashCode()`

## **Задание 2: Количество мяуканий**

### **Текст задачи**
Создать сущность Кот с возможностью мяукать. Реализовать метод, принимающий набор мяукающих объектов и вызывающий мяуканье у каждого. После работы метода подсчитать количество мяуканий каждого кота.

### **Алгоритм решения**
1. **Создан интерфейс `Meowable`**:
   ```java
   interface Meowable {
       void meow();
   }
   ```

2. **Реализован класс `Cat`**:
   - Поля: `name`, `meowCount`
   - Метод `meow()` выводит "Имя: мяу!" и увеличивает счетчик
   - Метод `toString()` возвращает "кот: Имя"

   ```java
   class Cat implements Meowable {
       private String name;
       private int meowCount = 0;
       
       @Override
       public void meow() {
           System.out.println(name + ": мяу!");
           meowCount++;
       }
   }
   ```

3. **Метод `makeThemMeow()`**:
   - Принимает список `Meowable` и количество мяуканий
   - Вызывает `meow()` у каждого объекта заданное количество раз

4. **Демонстрация работы** в `task2()`:
   - Создание нескольких котов
   - Запоминание начального количества мяуканий
   - Вызов метода `makeThemMeow()`
   - Подсчет разницы (мяуканий во время работы метода)
   - Вывод статистики

## **Задание 3: Первые вхождения элементов**

### **Текст задачи**
Оставить в списке только первые вхождения одинаковых элементов.

### **Алгоритм решения**
1. **Создан класс `UniqueElementsProcessor`**:
   - Дженерик-метод `getFirstOccurrences()`
   - Использует `HashSet` для отслеживания встреченных элементов

   ```java
   class UniqueElementsProcessor {
       public static <T> List<T> getFirstOccurrences(List<T> list) {
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
   ```

2. **Демонстрация работы** в `task3()`:
   - Поддержка трех типов данных: числа, строки, дроби
   - Ввод данных от пользователя
   - Вывод исходного списка и результата обработки

## **Задание 4: Соревнования по многоборью**

### **Текст задачи**
Обработка результатов соревнований из файла: чтение данных, подсчет суммы баллов, сортировка по убыванию, определение мест.

### **Алгоритм решения**
1. **Класс `SportsmanResult`**:
   - Поля: `lastName`, `firstName`, `totalScore`, `place`
   - Проверка длины имени и фамилии
   - Метод `toString()` для форматированного вывода

2. **Класс `SportsCompetitionProcessor`**:
   - Метод `processResults()` читает файл
   - Парсит данные с проверкой формата
   - Вычисляет сумму баллов для каждого спортсмена
   - Сортирует по убыванию суммы
   - Определяет места (одинаковые суммы - одинаковые места)

   ```java
   class SportsCompetitionProcessor {
       public static List<SportsmanResult> processResults(String filename) throws IOException {
           // Чтение файла, парсинг данных
           // Сортировка и определение мест
       }
   }
   ```

3. **Демонстрация работы** в `task4()`:
   - Чтение из файла (создание тестового файла при отсутствии)
   - Вывод результатов в табличном формате

## **Задание 5: Символы в четных словах**

### **Текст задачи**
Найти все уникальные символы, встречающиеся в словах с четными номерами (нумерация с 1).

### **Алгоритм решения**
1. **Класс `EvenWordsAnalyzer`**:
   - Конструктор принимает имя файла
   - Метод `getCharactersFromEvenWords()` читает файл, разбивает на слова
   - Собирает символы из слов с четными индексами (i = 1, 3, 5...)

   ```java
   class EvenWordsAnalyzer {
       public Set<Character> getCharactersFromEvenWords() throws IOException {
           String content = Files.readString(Paths.get(filename));
           String[] words = content.split("\\s+");
           Set<Character> characters = new HashSet<>();
           
           for (int i = 1; i < words.length; i += 2) {
               for (char c : words[i].toCharArray()) {
                   characters.add(c);
               }
           }
           return characters;
       }
   }
   ```

2. **Демонстрация работы** в `task5()`:
   - Чтение из файла
   - Вывод уникальных символов
   - Сортировка символов и подсчет количества

## **Задание 6: Обратный порядок очереди**

### **Текст задачи**
Вывести элементы непустой очереди в обратном порядке.

### **Алгоритм решения**
1. **Класс `QueueReversal`**:
   - Дженерик-метод `reverseQueue()`
   - Использует `Stack` для реверса элементов
   - Проверка на null и пустую очередь

   ```java
   class QueueReversal {
       public static <T> Queue<T> reverseQueue(Queue<T> queue) {
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
   ```

2. **Демонстрация работы** в `task6()`:
   - Поддержка очередей строк и чисел
   - Ввод элементов от пользователя
   - Вывод исходной и реверсированной очередей

## **Задание 7.1: Обработка точек через стрим**

### **Текст задачи**
Обработка набора точек через Stream API: удаление дубликатов, сортировка по X, преобразование отрицательных Y в положительные, создание ломаной.

### **Алгоритм решения**
1. **Классы `Point`, `Line`, `Polyline`**:
   ```java
   class Point {
       private double x, y;
       // equals(), hashCode(), toString() в формате "{X;Y}"
   }
   
   class Line {
       private Point start, end;
       // toString() в формате "Линия от {X1;Y1} до {X2;Y2}"
   }
   
   class Polyline {
       private List<Point> points;
       // toString() в формате "Линия [T1,T2,...,TN]"
   }
   ```

2. **Класс `PointStreamProcessor`**:
   - Метод `processPoints()` реализует обработку через Stream API

   ```java
   class PointStreamProcessor {
       public static Polyline processPoints(List<Point> points) {
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
   ```

3. **Демонстрация работы** в `task7()`:
   - Ввод координат точек от пользователя
   - Создание линии из первых двух точек
   - Обработка через стрим и создание ломаной

## **Задание 7.2: Группировка людей по номерам**

### **Текст задачи**
Чтение данных из файла в формате "Имя:Номер", обработка через Stream API: приведение имен к нужному формату, фильтрация людей без номеров, группировка по номерам.

### **Алгоритм решения**
1. **Класс `PeopleGrouper`**:
   - Конструктор принимает имя файла
   - Метод `groupPeopleByNumber()` обрабатывает данные через Stream API

   ```java
   class PeopleGrouper {
       public Map<Integer, List<String>> groupPeopleByNumber() throws IOException {
           return Files.lines(Paths.get(filename))
               .filter(line -> line.contains(":"))
               .map(line -> line.split(":"))
               .filter(parts -> parts.length == 2)
               .filter(parts -> !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty())
               .map(parts -> {
                   String name = parts[0].trim();
                   name = name.substring(0, 1).toUpperCase() + 
                          name.substring(1).toLowerCase();
                   int number = Integer.parseInt(parts[1].trim());
                   return new AbstractMap.SimpleEntry<>(number, name);
               })
               .collect(Collectors.groupingBy(
                   Map.Entry::getKey,
                   Collectors.mapping(Map.Entry::getValue, Collectors.toList())
               ));
       }
   }
   ```

2. **Демонстрация работы** в `task8()`:
   - Чтение из файла (создание тестового файла при отсутствии)
   - Вывод сгруппированных данных
   - Статистика по количеству уникальных номеров и людей

## **Общая архитектура проекта**

### **Структура Main класса**:
1. **Интерактивное меню** для выбора задания
2. **Вспомогательные методы** для безопасного ввода:
   - `readInteger()` - ввод целых чисел с проверкой
   - `readDouble()` - ввод дробных чисел с проверкой
   - `createTestFile()` - создание тестовых файлов

### **Особенности реализации**:
- **Проверка входных данных** во всех методах
- **Обработка исключений** с информативными сообщениями
- **Дружественный интерфейс** с подсказками для пользователя
- **Автоматическое создание тестовых файлов** при их отсутствии
- **Подробный вывод результатов** с промежуточными данными

### **Принципы ООП**:
- **Инкапсуляция** - приватные поля с геттерами
- **Наследование/Полиморфизм** - интерфейсы `FractionOperations` и `Meowable`
- **Дженерики** - в `UniqueElementsProcessor` и `QueueReversal`
- **Stream API** - в заданиях 7 и 8 для функциональной обработки данных

Проект соответствует всем требованиям задания: каждое задание реализовано в отдельном классе, все классы имеют свойства, конструкторы и метод `toString()`, организована проверка входных данных, реализован дружественный интерфейс ввода-вывода.
