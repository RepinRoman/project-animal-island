package SimulationCore;

import Entities.IslandMap.Location;

public class PlantGrowthTask implements Runnable { // Симуляция "роста" растений
    private final Location location;
    public PlantGrowthTask(Location location) {
        this.location = location;
    }

    public void growUp() {
        location.plantGrowth();
    }

    @Override
    public void run() {
        location.getLock().lock();
        try {
            growUp();
        } finally {
            location.getLock().unlock();
        }
    }
}
