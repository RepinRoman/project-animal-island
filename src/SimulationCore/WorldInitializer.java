package SimulationCore;

import Entities.IslandMap.Island;
import Entities.IslandMap.Location;
import Entities.Animals.Animal;
import UtilityClasses.ProjectSettings;
import UtilityClasses.ProjectRandomizer;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WorldInitializer { // Инициализатор "острова"
    private final Island islandField; // Сам "остров"

    public WorldInitializer(int y, int x) { // Инициализация "острова"
        this.islandField = new Island(y, x);
        initializeLocation();
        generateLocations();
    }

    private void initializeLocation() { // Инициализация "локаций" (координатами)
        Location[][] locations = this.islandField.getLocations();
        for (int y = 0; y < locations[y].length; y++) {
            for (int x = 0; x < locations.length; x++) {
                locations[x][y] = new Location(y, x);
            }
        }
    }

    private void generateLocations() { // Заполнение "локаций"
        Location[][] locations = this.islandField.getLocations();
        for (int y = 0; y < locations[y].length; y++) {
            for (Location[] location : locations) {
                setNeighboringLocations(location[y]); // Соседними локациями
                generationAnimals(location[y]); // Животными
                generationPlants(location[y]); // Растениями
            }
        }
    }

    private void setNeighboringLocations(Location location) { // Заполнение списка соседних локаций для каждой локации
        int yMin = Math.max(location.getCoordinate_Y() - 1, 0);
        int yMax = Math.min(location.getCoordinate_Y() + 1, ProjectSettings.FIELD_TO_SIZE_Y - 1);
        int xMin = Math.max(location.getCoordinate_X() - 1, 0);
        int xMax = Math.min(location.getCoordinate_X() + 1, ProjectSettings.FIELD_TO_SIZE_X - 1);

        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                if (location.getCoordinate_Y() != y || location.getCoordinate_X() != x) {
                    location.getNeighboringLocations().add(islandField.getLocation(y, x));
                }
            }
        }
    }

    private void generationAnimals(Location location) { // Заполнение животными каждой локации
        for (Class<? extends Animal> classAnimal : ProjectSettings.ANIMAL_CLASSES) {
            Set<Animal> set = ConcurrentHashMap.newKeySet();
            location.getAnimals().put(classAnimal, set);

            if (isCreateEntityType()) { // Будет ли первично помещён на локацию данный вид животных? (да/нет)
                int numberOfAnimalType = ProjectRandomizer.getRandom(0,
                        (int) ProjectSettings.ANIMAL_PARAMETERS.get(classAnimal)[1]); // 1 == maxNumber
                for (int i = 0; i < numberOfAnimalType; i++) {
                    Animal animal = tryCreateAnimal(classAnimal); // ***
                    set.add(animal);
                }
            }
        }
    }

    private void generationPlants(Location location) { // Заполнение растениями каждой локации
        if (isCreateEntityType()) { // Будет ли первично помещены на локацию растения? (да/нет)
            location.setPlant(ProjectRandomizer.getRandom(0, (int) ProjectSettings.MAX_AMOUNT_OF_PLANT_ON_ONE_CELL));
        }
    }

    private <T> T tryCreateAnimal(Class<T> tClass) { // *** Рефлексия для заполнения животными локаций
        try {
            return tClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException("Ошибка при создании животного: " + ex.getMessage(), ex);
        }
    }

    private boolean isCreateEntityType() { // Для первичного заполнения локаций животными/растениями
        return ProjectRandomizer.getRandom();
    }

    public Island getIslandField() { // Возвращает уже проинициализированный "остров"
        return islandField;
    }
}
