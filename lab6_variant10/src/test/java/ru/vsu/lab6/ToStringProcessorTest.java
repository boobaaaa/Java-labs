package ru.vsu.lab6;

import org.junit.jupiter.api.Test;
import ru.vsu.lab6.demo.Person;
import ru.vsu.lab6.processors.ToStringProcessor;

import static org.junit.jupiter.api.Assertions.*;

public class ToStringProcessorTest {

    @Test
    void build_shouldExcludeNoFields() {
        Person p = new Person("Иван", "секрет", 20);
        String s = ToStringProcessor.build(p);

        assertTrue(s.contains("name=Иван"));
        assertTrue(s.contains("age=20"));
        assertFalse(s.contains("password="));
        assertFalse(s.contains("секрет"));
    }
}
