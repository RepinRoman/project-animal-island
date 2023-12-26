package Entities.Animals.Omnivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Omnivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Eagle extends Animal implements Omnivorous { // Орёл (Всеядное животное)
    public Eagle() {
        this.aClass = Eagle.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Eagle.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Eagle.class)[0]); // 0 == maxWeight
    }
}
