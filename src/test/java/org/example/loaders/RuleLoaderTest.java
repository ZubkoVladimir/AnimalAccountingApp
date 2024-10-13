package org.example.loaders;

import org.example.entity.Rule;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleLoaderTest {

    @Test
    void load() throws IOException {
        Path testDir = Paths.get("D:\\dev\\PanDev\\AnimalAccountingSystem\\src\\test\\resources");
        Files.createDirectories(testDir);
        Path testFile = testDir.resolve("rule.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(testFile)) {
            writer.write("Сколько животных - всеядные\n");
        }

        RuleLoader loader = new RuleLoader();
        List<Rule> actuallyRules = loader.load(testFile.toString());

        assertEquals(1, actuallyRules.size());

        assertEquals("Сколько животных - всеядные", actuallyRules.get(0).getParams());

    }

}