package ru.petrik.geometry;

import java.util.ArrayList;
import java.util.List;

public class PolygonalChain {
    protected List<Point> points;

    public PolygonalChain() {
        points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void addPoint(double x, double y) {
        points.add(new Point(x, y));
    }

    public double length() {
        if (points.size() < 2) return 0;
        
        double totalLength = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            totalLength += points.get(i).distanceTo(points.get(i + 1));
        }
        return totalLength;
    }

    public Point getPoint(int index) {
        if (index >= 0 && index < points.size()) {
            return points.get(index);
        }
        return null;
    }

    public void setPoint(int index, Point point) {
        if (index >= 0 && index < points.size()) {
            points.set(index, point);
        }
    }

    public int getPointCount() {
        return points.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Ломаная: ");
        for (Point p : points) {
            sb.append(p).append(" ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PolygonalChain other = (PolygonalChain) obj;
        
        if (this.points.size() != other.points.size()) return false;
        
        for (int i = 0; i < points.size(); i++) {
            if (!this.points.get(i).equals(other.points.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return points.hashCode();
    }
}