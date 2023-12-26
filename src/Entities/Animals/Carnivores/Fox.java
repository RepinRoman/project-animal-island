package Entities.Animals.Carnivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Carnivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Fox extends Animal implements Carnivorous { // Лиса (Плотоядное животное)
    public Fox() {
        this.aClass = Fox.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Fox.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Fox.class)[0]); // 0 == maxWeight
    }
}
