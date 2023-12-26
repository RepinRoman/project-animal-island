package SimulationCore;

import Entities.IslandMap.Location;
import Entities.Animals.Animal;
import Entities.Animals.UtilityInterfaces.Herbivorous;
import Entities.Animals.UtilityInterfaces.Omnivorous;
import Entities.Animals.UtilityInterfaces.Carnivorous;

public class AnimalSimulationTask implements Runnable { // Симуляция "жизни" животного
    private final Animal animal;
    private final Location location;

    public AnimalSimulationTask(Animal animal, Location location) {
        this.animal = animal;
        this.location = location;
    }

    @Override
    public void run() {
        animal.getLock().lock();
        try { // Разные виды животных "едят" по разному
            if (animal instanceof Carnivorous carnivore) {
                carnivore.eat(location);
            } else if (animal instanceof Herbivorous herbivore) {
                herbivore.eat(location);
            } else if (animal instanceof Omnivorous omnivore) {
                omnivore.eat(location);
            } else {
                throw new IllegalArgumentException("Ошибка! Неизвестный тип животного: " + animal.getClass().getName() + "!");
            }
        } catch (Exception ex) {
            System.err.println("Ошибка при моделировании \"жизни\" животного: " + ex.getMessage());
            ex.printStackTrace(System.out);
        } finally {
            animal.getLock().unlock();
        }

        animal.reproduction(location); // Все животные "размножаются" одинаково
        animal.weightLoss(location); // Все животные "теряют вес" одинаково
        animal.timeToDie(location); // Все животные "умирают" одинаково

        try {
            animal.move(location); // Все животные "перемещаются" одинаково
        } catch (Exception ex) {
            System.err.println("Ошибка при \"перемещении\" животного: " + ex.getMessage());
            ex.printStackTrace(System.out);
        } finally {
            animal.getLock().unlock();
        }
    }
}
