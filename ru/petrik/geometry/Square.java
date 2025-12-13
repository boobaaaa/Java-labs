package ru.petrik.geometry;

public class Square implements Shape {
    private Point topLeft;
    private double side;

    public Square(Point topLeft, double side) {
        setTopLeft(topLeft);
        setSide(side);
    }

    public Square(double x, double y, double side) {
        this(new Point(x, y), side);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = (topLeft != null) ? topLeft : new Point();
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Сторона квадрата должна быть положительным числом. Получено: " + side);
        }
        this.side = side;
    }

    public PolygonalChain getPolygonalChain() {
        PolygonalChain chain = new PolygonalChain();
        
        Point topLeft = this.topLeft;
        Point topRight = new Point(topLeft.getX() + side, topLeft.getY());
        Point bottomRight = new Point(topLeft.getX() + side, topLeft.getY() + side);
        Point bottomLeft = new Point(topLeft.getX(), topLeft.getY() + side);
        
        chain.addPoint(topLeft);
        chain.addPoint(topRight);
        chain.addPoint(bottomRight);
        chain.addPoint(bottomLeft);
        chain.addPoint(topLeft.clone());
        
        return chain;
    }

    public double getArea() {
        return side * side;
    }

    @Override
    public String toString() {
        return String.format("Квадрат в точке %s со стороной %.1f", topLeft, side);
    }
}