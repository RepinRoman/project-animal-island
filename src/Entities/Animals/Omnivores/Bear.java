package Entities.Animals.Omnivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Omnivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Bear extends Animal implements Omnivorous { // Медведь (Всеядное животное)
    public Bear() {
        this.aClass = Bear.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Bear.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Bear.class)[0]); // 0 == maxWeight
    }
}
