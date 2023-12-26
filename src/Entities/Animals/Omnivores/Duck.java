package Entities.Animals.Omnivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Omnivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Duck extends Animal implements Omnivorous { // Утка (Всеядное животное)
    public Duck() {
        this.aClass = Duck.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Duck.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Duck.class)[0]); // 0 == maxWeight
    }
}
