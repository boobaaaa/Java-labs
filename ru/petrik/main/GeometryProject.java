package ru.petrik.main;

import ru.petrik.geometry.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeometryProject {
    
    // Метод для возведения в степень (задача 7.3)
    public static double power(String xStr, String yStr) {
        int x = Integer.parseInt(xStr);
        int y = Integer.parseInt(yStr);
        return Math.pow(x, y);
    }
    
    // Метод для безопасного ввода числа
    public static double readDouble(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Ошибка! Введите число.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextDouble();
    }
    
    // Метод для безопасного ввода целого числа
    public static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка! Введите целое число.");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== ДЕМОНСТРАЦИЯ РЕШЕНИЯ ЛАБОРАТОРНОЙ РАБОТЫ ===");
        System.out.println();
        
        // ========== ЗАДАЧА 1: КВАДРАТ И ЛОМАНАЯ ==========
        System.out.println("=== ЗАДАЧА 1: КВАДРАТ И ЛОМАНАЯ ===");
        
        try {
            // Создание квадрата
            Square square = new Square(5, 3, 23);
            System.out.println("1. Создан квадрат: " + square);
            
            // Получение ломаной из квадрата
            PolygonalChain chain = square.getPolygonalChain();
            System.out.println("2. Получена ломаная из квадрата");
            System.out.println("   Ломаная: " + chain);
            System.out.println("3. Длина ломаной: " + chain.length());
            
            // Сдвиг последней точки
            if (chain.getPointCount() > 0) {
                Point lastPoint = chain.getPoint(chain.getPointCount() - 1);
                lastPoint.move(15 - lastPoint.getX(), 25 - lastPoint.getY());
                System.out.println("4. Сдвинута последняя точка ломаной в {15;25}");
                System.out.println("5. Новая длина ломаной: " + chain.length());
            }
            
            // Демонстрация проверки стороны квадрата
            System.out.println("\n6. Демонстрация проверки стороны квадрата:");
            try {
                Square invalidSquare = new Square(0, 0, -5);
            } catch (IllegalArgumentException e) {
                System.out.println("   Ошибка при создании: " + e.getMessage());
            }
            
            try {
                square.setSide(0);
            } catch (IllegalArgumentException e) {
                System.out.println("   Ошибка при изменении: " + e.getMessage());
            }
            
            // Корректное изменение стороны
            square.setSide(10);
            System.out.println("7. Сторона квадрата изменена на 10: " + square);
            
        } catch (Exception e) {
            System.out.println("Ошибка в задаче 1: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // ========== ЗАДАЧА 2: БИНАРНОЕ ДЕРЕВО ==========
        System.out.println("=== ЗАДАЧА 2: БИНАРНОЕ ДЕРЕВО ===");
        
        Node root = new Node();
        int[] values = {3, 5, 4, 7, 1, 2};
        
        System.out.println("Добавляем значения: 3, 5, 4, 7, 1, 2");
        for (int value : values) {
            root.addValue(value);
        }
        
        System.out.println("Обход дерева (левосторонний): " + root);
        
        System.out.println("\nПроверка существования значений:");
        System.out.println("Содержит 4? " + root.contains(4));
        System.out.println("Содержит 6? " + root.contains(6));
        
        System.out.println("\nУдаление значения 4:");
        root.removeValue(4);
        System.out.println("Обход дерева после удаления: " + root);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // ========== ЗАДАЧА 3: ЗАМКНУТАЯ ЛОМАНАЯ ==========
        System.out.println("=== ЗАДАЧА 3: ЗАМКНУТАЯ ЛОМАНАЯ ===");
        
        ClosedPolygonalChain closedChain = new ClosedPolygonalChain();
        closedChain.addPoint(0, 0);
        closedChain.addPoint(10, 0);
        closedChain.addPoint(10, 10);
        closedChain.addPoint(0, 10);
        
        System.out.println("Создана замкнутая ломаная (квадрат 10x10):");
        System.out.println("Замкнутая ломаная: " + closedChain);
        System.out.println("Длина замкнутой ломаной: " + closedChain.length());
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // ========== ЗАДАЧА 4: ФИГУРЫ ==========
        System.out.println("=== ЗАДАЧА 4: ФИГУРЫ ===");
        
        // Создание различных фигур
        Circle circle = new Circle(0, 0, 5);
        Rectangle rectangle = new Rectangle(0, 0, 6, 4);
        Triangle triangle = new Triangle(0, 0, 5, 0, 2.5, 5);
        
        System.out.println("Созданы фигуры:");
        System.out.println("1. " + circle);
        System.out.println("   Площадь круга: " + circle.getArea());
        
        System.out.println("\n2. " + rectangle);
        System.out.println("   Площадь прямоугольника: " + rectangle.getArea());
        
        System.out.println("\n3. " + triangle);
        System.out.println("   Площадь треугольника: " + triangle.getArea());
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // ========== ЗАДАЧА 5: ИЗМЕРЕНИЕ ДЛИНЫ ==========
        System.out.println("=== ЗАДАЧА 5: ИЗМЕРЕНИЕ ДЛИНЫ ===");
        
        List<Measurable> measurableObjects = new ArrayList<>();
        
        // Создаем объекты с длиной
        LineWithLength line1 = new LineWithLength(0, 0, 10, 0);
        LineWithLength line2 = new LineWithLength(0, 0, 0, 5);
        
        PolygonalChainWithInterfaces chain1 = new PolygonalChainWithInterfaces();
        chain1.addPoint(0, 0);
        chain1.addPoint(5, 0);
        chain1.addPoint(5, 5);
        
        measurableObjects.add(line1);
        measurableObjects.add(line2);
        measurableObjects.add(chain1);
        
        double totalLength = LengthCalculator.calculateTotalLength(measurableObjects);
        System.out.println("Объекты с длиной:");
        System.out.println("1. Линия 1: " + line1 + ", длина: " + line1.getLength());
        System.out.println("2. Линия 2: " + line2 + ", длина: " + line2.getLength());
        System.out.println("3. Ломаная: " + chain1 + ", длина: " + chain1.getLength());
        System.out.println("\nСуммарная длина всех объектов: " + totalLength);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // ========== ЗАДАЧА 5.2: ОБЪЕДИНЕНИЕ ЛОМАНЫХ ==========
        System.out.println("=== ЗАДАЧА 5.2: ОБЪЕДИНЕНИЕ ЛОМАНЫХ ===");
        
        List<Shape> shapesWithChains = new ArrayList<>();
        shapesWithChains.add(square);
        shapesWithChains.add(rectangle);
        shapesWithChains.add(triangle);
        shapesWithChains.add(chain1);
        
        PolygonalChain mergedChain = PolygonalChainMerger.mergePolygonalChains(shapesWithChains);
        System.out.println("Объединенная ломаная из квадрата, прямоугольника, треугольника и обычной ломаной:");
        System.out.println("Количество точек: " + mergedChain.getPointCount());
        System.out.println("Длина объединенной ломаной: " + mergedChain.length());
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // ========== ЗАДАЧА 6: СРАВНЕНИЕ ЛОМАНЫХ ==========
        System.out.println("=== ЗАДАЧА 6: СРАВНЕНИЕ ЛОМАНЫХ ===");
        
        PolygonalChain chainA = new PolygonalChain();
        chainA.addPoint(0, 0);
        chainA.addPoint(5, 0);
        chainA.addPoint(5, 5);
        
        PolygonalChain chainB = new PolygonalChain();
        chainB.addPoint(0, 0);
        chainB.addPoint(5, 0);
        chainB.addPoint(5, 5);
        
        PolygonalChain chainC = new PolygonalChain();
        chainC.addPoint(0, 0);
        chainC.addPoint(5, 0);
        chainC.addPoint(5, 6);
        
        System.out.println("Цепь A: " + chainA);
        System.out.println("Цепь B: " + chainB);
        System.out.println("Цепь C: " + chainC);
        System.out.println("\nA.equals(B)? " + chainA.equals(chainB));
        System.out.println("A.equals(C)? " + chainA.equals(chainC));
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // ========== ЗАДАЧА 7.3: ВОЗВЕДЕНИЕ В СТЕПЕНЬ ==========
        System.out.println("=== ЗАДАЧА 7.3: ВОЗВЕДЕНИЕ В СТЕПЕНЬ ===");
        
        if (args.length >= 2) {
            try {
                double result = power(args[0], args[1]);
                System.out.println(args[0] + " ^ " + args[1] + " = " + result);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: неверный формат чисел в аргументах командной строки");
            }
        } else {
            System.out.println("Аргументы командной строки не предоставлены.");
            System.out.println("Введите числа для возведения в степень:");
            
            int x = readInt(scanner, "Введите X: ");
            int y = readInt(scanner, "Введите Y: ");
            
            double result = Math.pow(x, y);
            System.out.println(x + " ^ " + y + " = " + result);
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // ========== ЗАДАЧА 8: КЛОНИРОВАНИЕ ТОЧКИ ==========
        System.out.println("=== ЗАДАЧА 8: КЛОНИРОВАНИЕ ТОЧКИ ===");
        
        Point original = new Point(10, 20);
        Point clone = original.clone();
        
        System.out.println("Оригинальная точка: " + original);
        System.out.println("Клонированная точка: " + clone);
        
        original.move(5, 5);
        System.out.println("\nПосле перемещения оригинала на (5,5):");
        System.out.println("Оригинальная точка: " + original);
        System.out.println("Клонированная точка: " + clone);
        System.out.println("Клон остался неизменным? " + 
                          (clone.getX() == 10 && clone.getY() == 20));
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // ========== ДОПОЛНИТЕЛЬНАЯ ДЕМОНСТРАЦИЯ: ВВОД С КЛАВИАТУРЫ ==========
        System.out.println("=== ДОПОЛНИТЕЛЬНАЯ ДЕМОНСТРАЦИЯ ===");
        System.out.println("Создание фигур с вводом параметров с клавиатуры:");
        
        try {
            double circleX = readDouble(scanner, "\nВведите X координату центра круга: ");
            double circleY = readDouble(scanner, "Введите Y координату центра круга: ");
            double radius = readDouble(scanner, "Введите радиус круга: ");
            
            Circle userCircle = new Circle(circleX, circleY, radius);
            System.out.println("Создан круг: " + userCircle);
            System.out.println("Площадь круга: " + userCircle.getArea());
            
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        
        scanner.close();
        System.out.println("\n=== ПРОГРАММА ЗАВЕРШЕНА ===");
    }
}