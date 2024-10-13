package org.example.loaders;

import org.example.entity.Animal;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalLoaderTest {

    @Test
    void load() throws IOException {
        Path testDir = Paths.get("D:\\dev\\PanDev\\AnimalAccountingSystem\\src\\test\\resources");
        Files.createDirectories(testDir);
        Path testFile = testDir.resolve("animal.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(testFile)) {
            writer.write("ЛЕГКОЕ,МАЛЕНЬКОЕ,ВСЕЯДНОЕ\n");
            writer.write("ТЯЖЕЛОЕ,МАЛЕНЬКОЕ,ТРАВОЯДНОЕ\n");
            writer.write("ТЯЖЕЛОЕ,НЕВЫСОКОЕ,ТРАВОЯДНОЕ\n");
        }

        AnimalLoader loader = new AnimalLoader();
        List<Animal> actuallyAnimals = loader.load(testFile.toString());

        assertEquals(3, actuallyAnimals.size());

        assertEquals("ВСЕЯДНОЕ", actuallyAnimals.get(0).getType());
        assertEquals("МАЛЕНЬКОЕ", actuallyAnimals.get(0).getHeight());
        assertEquals("ЛЕГКОЕ", actuallyAnimals.get(0).getWeight());

        assertEquals("ТРАВОЯДНОЕ", actuallyAnimals.get(1).getType());
        assertEquals("МАЛЕНЬКОЕ", actuallyAnimals.get(1).getHeight());
        assertEquals("ТЯЖЕЛОЕ", actuallyAnimals.get(1).getWeight());

        assertEquals("ТРАВОЯДНОЕ", actuallyAnimals.get(2).getType());
        assertEquals("НЕВЫСОКОЕ", actuallyAnimals.get(2).getHeight());
        assertEquals("ТЯЖЕЛОЕ", actuallyAnimals.get(2).getWeight());
    }
}