package Entities.Animals.Herbivores;

import Entities.Animals.UtilityInterfaces.Herbivorous;
import Entities.IslandMap.Location;
import Entities.Animals.Animal;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

public class Caterpillar extends Animal implements Herbivorous { // Гусеница (Травоядное животное)
    public Caterpillar() {
        this.aClass = Caterpillar.class;
        this.weight = ProjectRandomizer.getRandom(
                ProjectSettings.ANIMAL_PARAMETERS.get(Caterpillar.class)[0] / 2,
                ProjectSettings.ANIMAL_PARAMETERS.get(Caterpillar.class)[0]); // 0 == maxWeight
    }

    @Override
    public void move(Location location) {
        // Гусеницы не перемещаются!
    }
}
