package SimulationCore;

import Entities.IslandMap.Location;
import UtilityClasses.ProjectSettings;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WorldLifeCycle { // Жизненный цикл симуляции
    private final WorldInitializer world;
    private final AtomicInteger simulationStepNumber = new AtomicInteger(1);
    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public WorldLifeCycle(WorldInitializer worldGenerator) {
        this.world = worldGenerator;
    }

    private final Runnable lifeTask = new Runnable() { // Симуляция отдельного такта
        @Override
        public void run() {
            Location[][] locations = world.getIslandField().getLocations();
            startingTheSimulation();
            int numberOfSimulationSteps = ProjectSettings.NUMBER_OF_SIMULATION_STEPS;
            if (simulationStepNumber.get() >= numberOfSimulationSteps) { // Работает, пока не пройдут все такты симуляции
                service.shutdown();
                for (int y = 0; y < locations[y].length; y++) {
                    for (Location[] location : locations) {
                        location[y].shutdown();
                    }
                }
                for (int y = 0; y < locations[y].length; y++) {
                    for (Location[] location : locations) {
                        try {
                            location[y].await(500);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace(System.out);
                        }
                    }
                }
                world.getIslandField().output();
            }
        }
    };

    private void startingTheSimulation() { // Старт симуляции каждого такта
        Location[][] locations = world.getIslandField().getLocations();
        simulationStepNumber.incrementAndGet();
        world.getIslandField().output();
        for (int y = 0; y < locations[y].length; y++) {
            for (Location[] location : locations) {
                location[y].start();
            }
        }
    }

    public void start() { // Запуск симуляции "острова"
        service.scheduleAtFixedRate(lifeTask, 1, ProjectSettings.STEP_DURATION, TimeUnit.MILLISECONDS);
    }
}
