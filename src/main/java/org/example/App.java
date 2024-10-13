package org.example;

import org.example.loaders.AnimalLoader;
import org.example.loaders.RuleLoader;

public class App {
    public static void main(String[] args) {
        String animalPath = "D:\\dev\\PanDev\\AnimalAccountingSystem\\src\\main\\resources\\animal.txt";
        String rulePath = "D:\\dev\\PanDev\\AnimalAccountingSystem\\src\\main\\resources\\rule.txt";

        AnimalLoader animalLoader = new AnimalLoader();
        RuleLoader ruleLoader = new RuleLoader();

        var a = animalLoader.load(animalPath);
        var r = ruleLoader.load(rulePath);

        AnimalFilter findMatches = new AnimalFilter();
        findMatches.applyRules(a, r);
    }
}
