package Entities.IslandMap;

import Entities.Animals.Carnivores.*;
import Entities.Animals.Herbivores.*;
import Entities.Animals.Omnivores.*;
import Entities.Animals.Animal;
import SimulationCore.AnimalSimulationTask;
import SimulationCore.PlantGrowthTask;
import UtilityClasses.ProjectSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Location { // Отдельная "локация" острова
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);
    private final int coordinate_X;
    private final int coordinate_Y;
    private volatile double plant; // Растения "локации"
    private final List<Location> neighboringLocations = new ArrayList<>(); // Соседние "локации"
    private final Map<Class<? extends Animal>, Set<Animal>> animals = new ConcurrentHashMap<>(); // Животные "локации"
    private final Lock lock = new ReentrantLock(true);

    public Location(int y, int x) {
        this.coordinate_Y = y;
        this.coordinate_X = x;
    }

    public void start() { // Животные локации "живут", растения "растут"
        lock.lock();
        try {
            for (Class<? extends Animal> animalClass : animals.keySet()) {
                for (Animal animal : animals.get(animalClass)) {
                    threadPool.submit(new AnimalSimulationTask(animal, this));
                }
            }
            threadPool.submit(new PlantGrowthTask(this));
        } finally {
            lock.unlock();
        }
    }

    public void await(int milliseconds) throws InterruptedException {
        threadPool.awaitTermination(milliseconds, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        threadPool.shutdown();
    }

    public void removeAnimal(Animal animal) { // Удаление животного из хранилища this локации
        animals.get(animal.getClass()).remove(animal);
    }

    public void addAnimalToLocation(Animal animal) { // Добавление животного в хранилище this локации
        if (isThereEnoughSpace(animal.getClass())) {
            animals.get(animal.getClass()).add(animal);
        }
    }

    public boolean isThereEnoughSpace(Class<? extends Animal> animalClass) { // Есть ли место для нового животного
        return animals.get(animalClass).size() < ProjectSettings.ANIMAL_PARAMETERS.get(animalClass)[1]; // 1 == maxNumber
    }

    public void plantGrowth() { // Логика роста растений в this локации
        getLock().lock();
        try {
            double plantGrowth = ProjectSettings.GROWTH_OF_PLANT;
            double newPlantValue = this.plant + plantGrowth;
            this.plant = Math.min(newPlantValue, ProjectSettings.MAX_AMOUNT_OF_PLANT_ON_ONE_CELL);
        } finally {
            getLock().unlock();
        }
    }

    @Override
    public String toString() { // Вывод статистики для this локации
        StringBuilder sb = new StringBuilder();
        sb.append("Координаты локации: [").append(coordinate_X).append(", ").append(coordinate_Y).append("]\n");

        // Животные на локации
        sb.append("Животные на локации:\n");
        for (Class<? extends Animal> animalClass : animals.keySet()) {
            int animalCount = animals.get(animalClass).size();
            if (animalCount > 0) {
                String animalName = animalClass.getSimpleName();
                sb.append("\t").append(animalName).append(": ").append(animalCount).append(" ").append(getAnimalEmoji(animalName)).append("\n");
            }
        }

        // Растения на локации
        sb.append("Растения на локации: ").append(String.format("%.2f", plant)).append(" ").append("🌿").append("\n\n");

        return "[" + "🐻" + animals.get(Bear.class).size()
                + "🐍" + animals.get(Snake.class).size()
                + "🐗" + animals.get(Boar.class).size()
                + "🐃" + animals.get(Buffalo.class).size()
                + "🦌" + animals.get(Deer.class).size()
                + "🦊" + animals.get(Fox.class).size()
                + "🐐" + animals.get(Goat.class).size()
                + "🐎" + animals.get(Horse.class).size()
                + "🐁" + animals.get(Mouse.class).size()
                + "🐇" + animals.get(Rabbit.class).size()
                + "🐑" + animals.get(Sheep.class).size()
                + "🐺" + animals.get(Wolf.class).size()
                + "🦆" + animals.get(Duck.class).size()
                + "🦅" + animals.get(Eagle.class).size()
                + "🐛" + animals.get(Caterpillar.class).size()
                + "🌿" + String.format("%.2f", plant) + "]\n"
                + sb;
    }

    private String getAnimalEmoji(String animalName) { // Метод для получения смайлика по "имени" животного
        return switch (animalName) {
            case "Bear" -> "🐻";
            case "Snake" -> "🐍";
            case "Boar" -> "🐗";
            case "Buffalo" -> "🐃";
            case "Deer" -> "🦌";
            case "Fox" -> "🦊";
            case "Goat" -> "🐐";
            case "Horse" -> "🐎";
            case "Mouse" -> "🐁";
            case "Rabbit" -> "🐇";
            case "Sheep" -> "🐑";
            case "Wolf" -> "🐺";
            case "Duck" -> "🦆";
            case "Eagle" -> "🦅";
            case "Caterpillar" -> "🐛";
            default -> "";
        };
    }

    public Lock getLock() {
        return lock;
    }

    public Map<Class<? extends Animal>, Set<Animal>> getAnimals() {
        return animals;
    }

    public List<Location> getNeighboringLocations() {
        return neighboringLocations;
    }

    public double getPlant() {
        return plant;
    }

    public void setPlant(double plant) {
        this.plant = plant;
    }

    public int getCoordinate_X() {
        return coordinate_X;
    }

    public int getCoordinate_Y() {
        return coordinate_Y;
    }
}
