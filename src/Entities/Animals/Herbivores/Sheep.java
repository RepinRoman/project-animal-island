package Entities.Animals.Herbivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Herbivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Sheep extends Animal implements Herbivorous { // Овца (Травоядное животное)
    public Sheep() {
        this.aClass = Sheep.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Sheep.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Sheep.class)[0]); // 0 == maxWeight
    }
}
