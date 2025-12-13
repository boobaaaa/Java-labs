package ru.petrik.main;

import ru.petrik.geometry.Shape;
import ru.petrik.geometry.Point;
import ru.petrik.geometry.PolygonalChain;
import java.util.List;

public class PolygonalChainMerger {
    public static PolygonalChain mergePolygonalChains(List<Shape> shapes) {
        PolygonalChain result = new PolygonalChain();
        for (Shape shape : shapes) {
            PolygonalChain chain = shape.getPolygonalChain();
            if (chain != null && chain.getPointCount() > 0) {
                for (int i = 0; i < chain.getPointCount(); i++) {
                    result.addPoint(chain.getPoint(i).clone());
                }
            }
        }
        return result;
    }
}