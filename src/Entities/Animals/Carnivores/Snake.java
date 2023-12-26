package Entities.Animals.Carnivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Carnivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Snake extends Animal implements Carnivorous { // Змея (Плотоядное животное)
    public Snake() {
        this.aClass = Snake.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Snake.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Snake.class)[0]); // 0 == maxWeight
    }
}
