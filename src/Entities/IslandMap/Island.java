package Entities.IslandMap;

import Entities.Animals.Animal;
import UtilityClasses.ProjectSettings;

import java.util.HashMap;
import java.util.Map;

public class Island { // "Остров"
    private final Location[][] locations; // Массив "локаций"
    private int iterationCount = 1; // Счётчик прошедших итераций симуляции

    public Island(int y, int x) {
        this.locations = new Location[x][y];
    }

    public Location[][] getLocations() {
        return this.locations;
    }

    public Location getLocation(int yPosition, int xPosition) {
        return locations[xPosition][yPosition];
    }

    public void output() { // Выход из симуляции "острова" и вывод общей статистики
        for (int y = 0; y < locations[y].length; y++) {
            for (Location[] location : locations) {
                System.out.print(location[y]);
            }
            System.out.println();
        }

        System.out.println("\n*******************************************************************************************\n");

        // Вывод информации о жизни на острове
        System.out.println("Статистика острова:");

        // Подсчет количества животных каждого вида
        Map<Class<? extends Animal>, Integer> animalCountMap = new HashMap<>();
        for (int y = 0; y < locations[y].length; y++) {
            for (Location[] value : locations) {
                Location location = value[y];
                for (Class<? extends Animal> animalClass : ProjectSettings.ANIMAL_CLASSES) {
                    int animalCount = location.getAnimals().get(animalClass).size();
                    animalCountMap.put(animalClass, animalCountMap.getOrDefault(animalClass, 0) + animalCount);
                }
            }
        }

        // Вывод количества животных каждого вида
        for (Map.Entry<Class<? extends Animal>, Integer> entry : animalCountMap.entrySet()) {
            Class<? extends Animal> animalClass = entry.getKey();
            int count = entry.getValue();
            System.out.println(animalClass.getSimpleName() + ": " + count);
        }

        // Вывод общего количества растений на острове
        int totalPlantCount = 0;
        for (int y = 0; y < locations[y].length; y++) {
            for (Location[] value : locations) {
                Location location = value[y];
                totalPlantCount += location.getPlant();
            }
        }
        System.out.println("Общее количество растений: " + totalPlantCount);

        // Вывод информации о прошедших итерациях симуляции
        System.out.println("Прошедшие дни: " + iterationCount + " из " + ProjectSettings.NUMBER_OF_SIMULATION_STEPS);
        increaseIterationCount();

        System.out.println("\n*******************************************************************************************\n");
    }

    public void increaseIterationCount() { // Увеличение счётчика прошедших итераций симуляции
        iterationCount++;
    }
}
