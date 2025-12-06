import java.util.ArrayList;
import java.util.List;

// Класс для хранения имени
class Name {
    private String lastName;
    private String firstName;
    private String patronymic;

    public Name(String lastName, String firstName, String patronymic) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }

    public Name(String firstName) { // только имя
        this(null, firstName, null);
    }

    public Name(String lastName, String firstName) { // фамилия + имя
        this(lastName, firstName, null);
    }

    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getPatronymic() { return patronymic; }

    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (lastName != null) sb.append(lastName);
        if (firstName != null) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(firstName);
        }
        if (patronymic != null) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(patronymic);
        }
        return sb.toString();
    }
}

// Класс Человек
class Person {
    private Name name;
    private int height;
    private Person father;

    public Person(Name name, int height) {
        this.name = name;
        this.height = height;
    }

    public Person(Name name, int height, Person father) {
        this.name = name;
        this.height = height;
        this.father = father;
    }

    public void setFather(Person father) {
        this.father = father;
        // Если у человека нет фамилии, берём от отца
        if ((name.getLastName() == null || name.getLastName().isEmpty()) && father != null && father.name.getLastName() != null) {
            name.setLastName(father.name.getLastName());
        }
        // Если нет отчества, создаём от имени отца
        if ((name.getPatronymic() == null || name.getPatronymic().isEmpty()) && father != null && father.name.getFirstName() != null) {
            name.setPatronymic(father.name.getFirstName() + "ович");
        }
    }

    @Override
    public String toString() {
        return name.toString() + " (рост: " + height + ")";
    }
}

// Класс для Точки
class Point {
    private double x;
    private double y;

    public Point(double x, double y) { this.x = x; this.y = y; }

    public double distanceTo(Point other) {
        return Math.hypot(this.x - other.x, this.y - other.y);
    }

    public void shift(double dx, double dy) { x += dx; y += dy; }

    @Override
    public String toString() { return "(" + x + ";" + y + ")"; }
}

// Класс Ломаная линия
class Polyline {
    private List<Point> points;

    public Polyline() { points = new ArrayList<>(); }
    public Polyline(Point... pts) {
        points = new ArrayList<>();
        for (Point p : pts) points.add(p);
    }

    public void addPoints(Point... pts) { for (Point p : pts) points.add(p); }

    public void shiftStart(double dx, double dy) {
        if (!points.isEmpty()) {
            points.get(0).shift(dx, dy);
        }
    }

    public double length() {
        double len = 0;
        for (int i = 1; i < points.size(); i++) {
            len += points.get(i - 1).distanceTo(points.get(i));
        }
        return len;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Линия [");
        for (int i = 0; i < points.size(); i++) {
            sb.append(points.get(i));
            if (i < points.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

// Главный класс
public class Main {
    public static void main(String[] args) {

        System.out.println("=== Имена ===");
        Name cleopatra = new Name("Клеопатра");
        Name pushkin = new Name("Пушкин", "Александр", "Сергеевич");
        Name mayakovsky = new Name("Маяковский", "Владимир");

        System.out.println(cleopatra);
        System.out.println(pushkin);
        System.out.println(mayakovsky);

        System.out.println("\n=== Люди с именами ===");
        Person person1 = new Person(cleopatra, 152);
        Person person2 = new Person(pushkin, 167);
        Person person3 = new Person(mayakovsky, 189);

        System.out.println(person1);
        System.out.println(person2);
        System.out.println(person3);

        System.out.println("\n=== Люди с родителями ===");
        Name ivanName = new Name("Чудова", "Иван");
        Name petrName = new Name("Чудова", "Петр");
        Name borisName = new Name(null, "Борис");

        Person ivan = new Person(ivanName, 180);
        Person petr = new Person(petrName, 175, ivan);
        petr.setFather(ivan);
        Person boris = new Person(borisName, 165, petr);
        boris.setFather(petr);

        System.out.println(ivan);
        System.out.println(petr);
        System.out.println(boris);

        System.out.println("\n=== Ломаные линии ===");
        Polyline poly1 = new Polyline(new Point(1,5), new Point(2,8), new Point(5,3));
        Polyline poly2 = new Polyline(new Point(1,5), new Point(2,-5), new Point(4,-8), new Point(5,3));

        System.out.println(poly1);
        System.out.println(poly2);

        System.out.println("\n=== Сдвиг начала первой ломаной ===");
        poly1.shiftStart(1, 1); // смещаем начало на (1,1)
        poly2.shiftStart(1, 1); // смещаем одновременно начало второй
        System.out.println(poly1);
        System.out.println(poly2);

        System.out.println("\n=== Создание Ломаной и длина ===");
        Polyline polyLength = new Polyline(new Point(1,5), new Point(2,8), new Point(5,3));
        System.out.println(polyLength);
        System.out.println("Длина: " + polyLength.length());

        polyLength.addPoints(new Point(5,15), new Point(8,10));
        System.out.println("После добавления точек: " + polyLength);
        System.out.println("Длина: " + polyLength.length());
    }
}
