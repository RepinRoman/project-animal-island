package Entities.Animals;

import Entities.IslandMap.Location;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Animal implements Cloneable { // Общие характеристики и поведение для всех "животных"
    public Class<? extends Animal> aClass;
    public double weight;
    private final Lock lock = new ReentrantLock(true);

    public Animal() {
        this.aClass = getClass();
    }

    public void weightLoss(Location location) { // Каждый такт симуляции животное теряет 1/10 своего веса
        location.getLock().lock();
        try {
            weight = weight - weight / 10;
        } finally {
            location.getLock().unlock();
        }
    }

    public void timeToDie(Location location) { // Каждый такт животное может умереть от голода при весе меньше 1/3
        location.getLock().lock();
        try {
            if (weight < ProjectSettings.ANIMAL_PARAMETERS.get(aClass)[0] / 3) { // 0 == maxWeight
                location.removeAnimal(this);
            }
        } finally {
            location.getLock().unlock();
        }
    }

    public void reproduction(Location location) { // Если вес достаточный и есть "пара", то породить новое животное
        location.getLock().lock();
        try {
            Set<Animal> animals = location.getAnimals().get(aClass);
            if (weight == ProjectSettings.ANIMAL_PARAMETERS.get(aClass)[0] && animals.size() > 1) { // 0 == maxWeight
                Animal clone = this.clone();
                location.addAnimalToLocation(clone);
                weightLoss(location);
            }
        } finally {
            location.getLock().unlock();
        }
    }

    public void move(Location location) { // Если на новой локации есть место, то животное "перемещается"
        location.getLock().lock();
        Location newLocation = choiceOfAvailableLocation(location);
        try {
            if (newLocation.isThereEnoughSpace(this.aClass)) {
                newLocation.addAnimalToLocation(this);
                location.removeAnimal(this);
            }
        } finally {
            location.getLock().unlock();
        }
    }

    private Location choiceOfAvailableLocation(Location location) { // Выбор новой локации из списка доступных
        int steps = getMaxNumberOfStepsAnimal();
        for (int i = steps; i > 0; i--) {
            location = location.getNeighboringLocations()
                    .get(ThreadLocalRandom.current().nextInt(0, location.getNeighboringLocations().size()));
        }
        return location;
    }

    @Override
    public Animal clone() { // "Размножение" животного реализовано через клонирование
        try {
            Animal clone = (Animal) super.clone();
            clone.weight = ProjectRandomizer.getRandom(
                    ProjectSettings.ANIMAL_PARAMETERS.get(aClass)[0] / 1.5,
                    ProjectSettings.ANIMAL_PARAMETERS.get(this.aClass)[0]); // 0 == maxWeight
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new AssertionError(); // Никогда не будет выброшено
        }
    }

    public double getCurrentWeight() {
        return weight;
    }

    private int getMaxNumberOfStepsAnimal() {
        return (int) ProjectSettings.ANIMAL_PARAMETERS.get(this.aClass)[2]; // 2 == maxSpeed
    }

    public Lock getLock() {
        return lock;
    }
}
