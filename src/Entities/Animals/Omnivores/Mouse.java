package Entities.Animals.Omnivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Omnivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Mouse extends Animal implements Omnivorous { // Мышь (Всеядное животное)
    public Mouse() {
        this.aClass = Mouse.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Mouse.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Mouse.class)[0]); // 0 == maxWeight
    }
}
