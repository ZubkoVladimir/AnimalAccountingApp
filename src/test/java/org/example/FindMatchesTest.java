package org.example;

import org.example.entity.Animal;
import org.example.entity.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AnimalFilterTest {
    private List<Animal> animals;
    private List<Rule> rules;
    private AnimalFilter filter;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        animals = new ArrayList<>();
        animals.add(new Animal("ЛЕГКОЕ", "МАЛЕНЬКОЕ", "ВСЕЯДНОЕ"));
        animals.add(new Animal("ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ТРАВОЯДНОЕ"));
        animals.add(new Animal("ТЯЖЕЛОЕ", "НЕВЫСОКОЕ", "ТРАВОЯДНОЕ"));
        animals.add(new Animal("ТЯЖЕЛОЕ", "МАЛЕНЬКОЕ", "ПЛОТОЯДНОЕ"));
        animals.add(new Animal("ТЯЖЕЛОЕ", "ВЫСОКОЕ", "ВСЕЯДНОЕ"));

        rules = new ArrayList<>();
        rules.add(new Rule("травоядных")); // 2
        rules.add(new Rule("травоядных или плотоядных и они при этом маленькие")); // 2
        rules.add(new Rule("всеядных, но не являются высокими")); // 1
        rules.add(new Rule("всеядных или плотоядных")); // 3

        filter = new AnimalFilter();

        originalOut = System.out;
        // Перенаправляем вывод
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        // Возвращаем оригинальный вывод
        System.setOut(originalOut);
    }

    @Test
    void applyRules() {
        // Проверяем вывод
        List<Animal> filteredAnimals = new ArrayList<>(animals);
        filter.applyRules(filteredAnimals, rules);
        String normalizedOutput = outputStreamCaptor.toString().replace("\r\n", "\n");
        assertEquals("""
                травоядных: 2
                травоядных или плотоядных и они при этом маленькие: 2
                всеядных, но не являются высокими: 1
                всеядных или плотоядных: 3
                """, normalizedOutput);
    }
}