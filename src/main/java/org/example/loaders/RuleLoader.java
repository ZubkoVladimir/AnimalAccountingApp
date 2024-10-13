package org.example.loaders;

import org.example.entity.Rule;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RuleLoader implements Loader {
    private final List<Rule> rules = new ArrayList<>();
    /**
     * Чтение файла с правилами
     * <p>
     * params:
     * <p>
     * String path - путь к файлу с правилами
     * return список правил
     **/
    @Override
    public List<Rule> load(String path) {

        try (BufferedReader reader = Files.newBufferedReader(Path.of(path))) {
            String lines;
            // Проверяем на null
            while ((lines = reader.readLine()) != null) {
                if (!lines.isEmpty()) {
                    String[] section = lines.split("Сколько животных – ");
                    for (String rule : section) {
                        rule = rule.trim();
                        if (!rule.isEmpty()) {
                            rules.add(new Rule(rule));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File load exception " + e);
        }
        return rules;
    }
}
