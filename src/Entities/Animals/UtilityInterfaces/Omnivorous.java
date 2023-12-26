package Entities.Animals.UtilityInterfaces;

import Entities.IslandMap.Location;
import Entities.Animals.Animal;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface Omnivorous {
    default void eat(Location location) { // Логика питания "всеядного" животного
        location.getLock().lock();
        Animal omnivorous = (Animal) this;
        boolean isAte = false; // "Поело" ли животное
        double startingWeightOmnivores = omnivorous.weight; // Начальный "вес"
        double maxWeightOmnivores = ProjectSettings.ANIMAL_PARAMETERS.get(omnivorous.aClass)[0]; // 0 == maxWeight
        double satiation = ProjectSettings.ANIMAL_PARAMETERS.get(omnivorous.aClass)[3]; // 3 == maxSaturation
        double differentWeight = maxWeightOmnivores - startingWeightOmnivores;
        try {
            Map<Class<? extends Animal>, Integer> victimsMap = ProjectSettings.FEEDING_CHANCES.get(omnivorous.aClass);
            if (differentWeight > 0) { // Если животное "голодно"
                Iterator<Map.Entry<Class<? extends Animal>, Integer>> victimsMapIterator = victimsMap.entrySet().iterator();
                while (!isAte && victimsMapIterator.hasNext()) {
                    Map.Entry<Class<? extends Animal>, Integer> probabilityPair = victimsMapIterator.next();
                    Class<?> classVictim = probabilityPair.getKey();
                    Integer probability = probabilityPair.getValue();
                    Set<Animal> victims = location.getAnimals().get(classVictim);
                    Iterator<Animal> victimsIterator = victims.iterator();
                    if (ProjectRandomizer.getRandom(probability) && !victims.isEmpty() && victimsIterator.hasNext()) {
                        Animal victim = victimsIterator.next();
                        omnivorous.weight = Math.min(omnivorous.weight + victim.getCurrentWeight(), maxWeightOmnivores);
                        if (omnivorous.weight >= startingWeightOmnivores + satiation || omnivorous.weight == maxWeightOmnivores) {
                            isAte = true; // Есть всех доступных "животных" до насыщения или пока они не кончатся
                        }
                        victimsIterator.remove();
                    }
                }
            }
            satiation = omnivorous.weight - startingWeightOmnivores; // Если животное ВСЁ ЕЩЁ "голодно"
            if (location.getPlant() > satiation) { // Есть все доступные "растения" до насыщения
                location.setPlant(Math.max(location.getPlant() - satiation, 0));
                omnivorous.weight = Math.min(omnivorous.getCurrentWeight() + satiation,
                        ProjectSettings.ANIMAL_PARAMETERS.get(omnivorous.aClass)[0]); // 0 == maxWeight
            } else {
                omnivorous.weight = omnivorous.weight + location.getPlant();
                location.setPlant(0);
            }
        } finally {
            location.getLock().unlock();
        }
    }
}
