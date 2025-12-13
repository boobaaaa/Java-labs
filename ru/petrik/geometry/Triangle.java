package ru.petrik.geometry;

public class Triangle implements Shape {
    private Point a;
    private Point b;
    private Point c;

    public Triangle(Point a, Point b, Point c) {
        this.a = (a != null) ? a : new Point();
        this.b = (b != null) ? b : new Point();
        this.c = (c != null) ? c : new Point();
    }

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this(new Point(x1, y1), new Point(x2, y2), new Point(x3, y3));
    }

    public double getArea() {
        return Math.abs(
            (a.getX() * (b.getY() - c.getY()) + 
             b.getX() * (c.getY() - a.getY()) + 
             c.getX() * (a.getY() - b.getY())) / 2.0
        );
    }

    @Override
    public PolygonalChain getPolygonalChain() {
        PolygonalChain chain = new PolygonalChain();
        chain.addPoint(a.clone());
        chain.addPoint(b.clone());
        chain.addPoint(c.clone());
        chain.addPoint(a.clone());
        return chain;
    }

    @Override
    public String toString() {
        return String.format("Треугольник с вершинами %s, %s, %s", a, b, c);
    }
}