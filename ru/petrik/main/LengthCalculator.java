package ru.petrik.main;

import ru.petrik.geometry.Measurable;
import java.util.List;

public class LengthCalculator {
    public static double calculateTotalLength(List<Measurable> objects) {
        double total = 0;
        for (Measurable obj : objects) {
            total += obj.getLength();
        }
        return total;
    }
}