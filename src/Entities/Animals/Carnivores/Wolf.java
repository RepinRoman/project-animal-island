package Entities.Animals.Carnivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Carnivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Wolf extends Animal implements Carnivorous { // Волк (Плотоядное животное)
    public Wolf() {
        this.aClass = Wolf.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Wolf.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Wolf.class)[0]); // 0 == maxWeight
    }
}
