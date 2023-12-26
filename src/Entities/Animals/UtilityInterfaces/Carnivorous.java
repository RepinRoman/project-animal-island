package Entities.Animals.UtilityInterfaces;

import Entities.IslandMap.Location;
import Entities.Animals.Animal;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface Carnivorous {
    default void eat(Location location) { // Логика питания "плотоядного" животного
        location.getLock().lock();
        Animal carnivorous = (Animal) this;
        boolean isAte = false; // "Поело" ли животное
        double startingWeightCarnivorous = carnivorous.weight; // Начальный "вес"
        double maxWeightCarnivorous = ProjectSettings.ANIMAL_PARAMETERS.get(carnivorous.aClass)[0]; // 0 == maxWeight
        double satiation = ProjectSettings.ANIMAL_PARAMETERS.get(carnivorous.aClass)[3]; // 3 == maxSaturation
        double differentWeight = maxWeightCarnivorous - startingWeightCarnivorous;
        try {
            Map<Class<?extends Animal>, Integer> victimsMap = ProjectSettings.FEEDING_CHANCES.get(carnivorous.aClass);
            if (differentWeight > 0) { // Если животное "голодно"
                Iterator<Map.Entry<Class<?extends Animal>, Integer>> victimsMapIterator = victimsMap.entrySet().iterator();
                while (!isAte && victimsMapIterator.hasNext()) { // Пробовать "поесть" пока не получится
                    Map.Entry<Class<?extends Animal>, Integer> probabilityPair = victimsMapIterator.next();
                    Class<?> classVictim = probabilityPair.getKey();
                    Integer probability = probabilityPair.getValue();
                    Set<Animal> victims = location.getAnimals().get(classVictim);
                    Iterator<Animal> victimsIterator = victims.iterator();
                    if (ProjectRandomizer.getRandom(probability) && !victims.isEmpty() && victimsIterator.hasNext()) {
                        Animal victim = victimsIterator.next();
                        carnivorous.weight = Math.min(carnivorous.weight + victim.getCurrentWeight(), maxWeightCarnivorous);
                        if (carnivorous.weight >= startingWeightCarnivorous + satiation || carnivorous.weight == maxWeightCarnivorous) {
                            isAte = true; // Есть всех доступных "животных" до насыщения или пока они не кончатся
                        }
                        victimsIterator.remove();
                    }
                }
            }
        } finally {
            location.getLock().unlock();
        }
    }
}
