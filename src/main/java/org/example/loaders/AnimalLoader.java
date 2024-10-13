package org.example.loaders;

import org.example.entity.Animal;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AnimalLoader implements Loader {
    private final List<Animal> animals = new ArrayList<>();

    /**
     * Чтение файла с животными и их свойствами
     * <p>
     * params:
     * <p>
     * String path - путь к файлу с животными
     * return список свойств животных
     **/
    @Override
    public List<Animal> load(String path) {

        try (BufferedReader reader = Files.newBufferedReader(Path.of(path))) {
            String lines;
            while ((lines = reader.readLine()) != null) {
                if (!lines.isEmpty()) {
                    String[] section = lines.split(",");
                    animals.add(new Animal(section[0], section[1], section[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("File load exception " + e);
        }
        return animals;
    }
}
