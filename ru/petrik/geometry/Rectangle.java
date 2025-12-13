package ru.petrik.geometry;

public class Rectangle implements Shape {
    private Point topLeft;
    private double width;
    private double height;

    public Rectangle(Point topLeft, double width, double height) {
        this.topLeft = (topLeft != null) ? topLeft : new Point();
        setWidth(width);
        setHeight(height);
    }

    public Rectangle(double x, double y, double width, double height) {
        this(new Point(x, y), width, height);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Ширина должна быть положительным числом. Получено: " + width);
        }
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Высота должна быть положительным числом. Получено: " + height);
        }
        this.height = height;
    }

    public double getArea() {
        return width * height;
    }

    @Override
    public PolygonalChain getPolygonalChain() {
        PolygonalChain chain = new PolygonalChain();
        
        chain.addPoint(topLeft.clone());
        chain.addPoint(new Point(topLeft.getX() + width, topLeft.getY()));
        chain.addPoint(new Point(topLeft.getX() + width, topLeft.getY() + height));
        chain.addPoint(new Point(topLeft.getX(), topLeft.getY() + height));
        chain.addPoint(topLeft.clone());
        
        return chain;
    }

    @Override
    public String toString() {
        return String.format("Прямоугольник в точке %s размером %.1f×%.1f", topLeft, width, height);
    }
}