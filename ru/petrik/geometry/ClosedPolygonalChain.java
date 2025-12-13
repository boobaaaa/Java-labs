package ru.petrik.geometry;

public class ClosedPolygonalChain extends PolygonalChain {
    @Override
    public double length() {
        if (points.size() < 2) return 0;
        
        double totalLength = super.length();
        
        if (points.size() > 1) {
            totalLength += points.get(points.size() - 1).distanceTo(points.get(0));
        }
        
        return totalLength;
    }

    @Override
    public String toString() {
        return "Замкнутая " + super.toString();
    }

    @Override
    public PolygonalChain getPolygonalChain() {
        PolygonalChain chain = new PolygonalChain();
        for (Point p : points) {
            chain.addPoint(p.clone());
        }
        if (!points.isEmpty()) {
            chain.addPoint(points.get(0).clone());
        }
        return chain;
    }
}