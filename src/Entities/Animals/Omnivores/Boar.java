package Entities.Animals.Omnivores;

import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Omnivorous;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Boar extends Animal implements Omnivorous { // Кабан (Всеядное животное)
    public Boar() {
        this.aClass = Boar.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Boar.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Boar.class)[0]); // 0 == maxWeight
    }
}
