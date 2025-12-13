package ru.petrik.geometry;

public class Line {
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    public double length() {
        return start.distanceTo(end);
    }

    @Override
    public String toString() {
        return String.format("Линия от %s до %s", start, end);
    }

    public PolygonalChain getPolygonalChain() {
        PolygonalChain chain = new PolygonalChain();
        chain.addPoint(start.clone());
        chain.addPoint(end.clone());
        return chain;
    }
}