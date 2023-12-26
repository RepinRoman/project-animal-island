package Entities.Animals.Herbivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Herbivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Goat extends Animal implements Herbivorous { // Коза (Травоядное животное)
    public Goat() {
        this.aClass = Goat.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Goat.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Goat.class)[0]); // 0 == maxWeight
    }
}
