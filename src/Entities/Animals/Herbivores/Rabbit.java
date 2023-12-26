package Entities.Animals.Herbivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Herbivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Rabbit extends Animal implements Herbivorous { // Кролик (Травоядное животное)
    public Rabbit() {
        this.aClass = Rabbit.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Rabbit.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Rabbit.class)[0]); // 0 == maxWeight
    }
}
