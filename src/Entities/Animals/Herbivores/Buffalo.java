package Entities.Animals.Herbivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Herbivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Buffalo extends Animal implements Herbivorous { // Буйвол (Травоядное животное)
    public Buffalo() {
        this.aClass = Buffalo.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Buffalo.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Buffalo.class)[0]); // 0 == maxWeight
    }
}
