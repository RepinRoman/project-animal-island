package Entities.Animals.Herbivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Herbivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Horse extends Animal implements Herbivorous { // Лошадь (Травоядное животное)
    public Horse() {
        this.aClass = Horse.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Horse.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Horse.class)[0]); // 0 == maxWeight
    }
}
