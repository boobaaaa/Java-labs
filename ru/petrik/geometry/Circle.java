package ru.petrik.geometry;

public class Circle implements Shape {
    private Point center;
    private double radius;

    public Circle(Point center, double radius) {
        this.center = (center != null) ? center : new Point();
        setRadius(radius);
    }

    public Circle(double x, double y, double radius) {
        this(new Point(x, y), radius);
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = (center != null) ? center : new Point();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть положительным числом. Получено: " + radius);
        }
        this.radius = radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public PolygonalChain getPolygonalChain() {
        System.out.println("Круг нельзя представить в виде ломаной линии");
        return new PolygonalChain();
    }

    @Override
    public String toString() {
        return String.format("Круг с центром в %s и радиусом %.1f", center, radius);
    }
}