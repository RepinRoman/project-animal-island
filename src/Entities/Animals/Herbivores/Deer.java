package Entities.Animals.Herbivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Herbivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Deer extends Animal implements Herbivorous { // Олень (Травоядное животное)
    public Deer() {
        this.aClass = Deer.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Deer.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Deer.class)[0]); // 0 == maxWeight
    }
}
