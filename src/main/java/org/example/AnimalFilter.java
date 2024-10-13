package org.example;

import org.example.entity.Animal;
import org.example.entity.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalFilter {
    /**
     * Применение правил на списке с животными
     * <p>
     * params:
     * <p>
     * List<Animal> animals - список свойств животных
     * List<Rule> rules - список правил
     **/
    public void applyRules(List<Animal> animals, List<Rule> rules) {
        List<Animal> filteredAnimals;
        // Выделяем правило из массива
        for (Rule rule : rules) {
            filteredAnimals = new ArrayList<>(animals);
            String[] parts = rule.getParams().split("(?<=\\w)\\s+(?=и|или|но)");

            for (String part : parts) {
                // Делим правила на ключевые логические слова
                if (part.contains("или")) {
                    String[] orConditions = part.split("или");
                    // Подсчет двух свойств
                    filteredAnimals = filteredAnimals.stream()
                            .filter(animal -> Arrays.stream(orConditions)
                                    .anyMatch(condition -> matchesCriteria(animal, condition.trim())))
                            .collect(Collectors.toList());
                }
                if (part.contains("и они при этом")) {
                    String[] andConditions = part.split("и они при этом");
                    filteredAnimals = filteredAnimals.stream()
                            .filter(animal -> matchesCriteria(animal, andConditions[0].trim()))
                            .collect(Collectors.toList());

                    // Дополнительный фильтр для второго условия
                    filteredAnimals = filteredAnimals.stream()
                            .filter(animal -> matchesCriteria(animal, andConditions[1].trim()))
                            .collect(Collectors.toList());
                }
                if (part.contains(", но не являются")) {
                    String[] negConditions = part.split(", но не являются");
                    String includeCondition = negConditions[0].trim();
                    String excludeCondition = negConditions[1].trim();

                    // Фильтруем животных по включающему условию
                    filteredAnimals = filteredAnimals.stream()
                            .filter(animal -> matchesCriteria(animal, includeCondition))
                            .collect(Collectors.toList());

                    // Убедимся, что удаляем исключенные животные, если они есть в filteredAnimals
                    filteredAnimals.retainAll(filteredAnimals.stream()
                            .filter(animal -> !matchesCriteria(animal, excludeCondition))
                            .collect(Collectors.toList()));
                } else {
                    // Обрабатываем "и" по умолчанию
                    filteredAnimals = filteredAnimals.stream()
                            .filter(animal -> matchesCriteria(animal, part.trim()))
                            .collect(Collectors.toList());
                }
                // Вывод получившегося списка
                System.out.println(rule.getParams() + ": " + filteredAnimals.size());
            }
        }
    }

    /**
     * Сравнение параметров животных с требуемым правилом
     * <p>
     * params:
     * <p>
     * Animal animal - свойства животных
     * String rule - правило
     * return boolean
     **/
    private static boolean matchesCriteria(Animal animal, String rule) {
        boolean isTypeMatch = false;
        boolean isHeightMatch = false;
        boolean isWeightMatch = false;

        String[] words = rule.split("\\s+");
        for (String word : words) {
            if (word.length() > 3) {
                String sub = word.substring(0, word.length() - 3).toLowerCase(); // Приводим к нижнему регистру

                String type = animal.getType().toLowerCase();
                String height = animal.getHeight().toLowerCase();
                String weight = animal.getWeight().toLowerCase();

                if (type.length() > 3) {
                    isTypeMatch = isTypeMatch || type.substring(0, type.length() - 3).startsWith(sub);
                }
                if (height.length() > 3) {
                    isHeightMatch = isHeightMatch || height.substring(0, height.length() - 2).startsWith(sub);
                }
                if (weight.length() > 3) {
                    isWeightMatch = isWeightMatch || weight.substring(0, weight.length() - 2).startsWith(sub);
                }
            }
        }
        // Если хоть один параметр совпадает, то возвращает true
        return isTypeMatch || isHeightMatch || isWeightMatch;
    }
}
