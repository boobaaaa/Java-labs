package ru.petrik.geometry;

public class LineWithLength extends Line implements Measurable {
    public LineWithLength(Point start, Point end) {
        super(start, end);
    }
    
    public LineWithLength(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }
    
    @Override
    public double getLength() {
        return length();
    }
}