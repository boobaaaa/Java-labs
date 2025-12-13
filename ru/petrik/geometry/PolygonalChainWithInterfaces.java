package ru.petrik.geometry;

public class PolygonalChainWithInterfaces extends PolygonalChain implements Measurable, Shape {
    @Override
    public double getLength() {
        return length();
    }
    
    @Override
    public double getArea() {
        return 0;
    }
    
    @Override
    public PolygonalChain getPolygonalChain() {
        PolygonalChain chain = new PolygonalChain();
        for (Point p : points) {
            chain.addPoint(p.clone());
        }
        return chain;
    }
}