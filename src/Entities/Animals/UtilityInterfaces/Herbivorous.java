package Entities.Animals.UtilityInterfaces;

import Entities.IslandMap.Location;
import Entities.Animals.Animal;
import UtilityClasses.ProjectSettings;

public interface Herbivorous { // Логика питания "травоядного" животного
    default void eat(Location location) {
        location.getLock().lock();
        Animal herbivorous = (Animal) this;
        double satiation = ProjectSettings.ANIMAL_PARAMETERS.get(herbivorous.aClass)[3]; // 3 == maxSaturation
        try {
            if (location.getPlant() > satiation) { // Есть все доступные "растения" до насыщения
                location.setPlant(Math.max(location.getPlant() - satiation, 0));
                herbivorous.weight = Math.min(herbivorous.getCurrentWeight() + satiation,
                        ProjectSettings.ANIMAL_PARAMETERS.get(herbivorous.aClass)[0]); // 0 == maxWeight
            } else {
                herbivorous.weight = herbivorous.weight + location.getPlant();
                location.setPlant(0);
            }
        } finally {
            location.getLock().unlock();
        }
    }
}
