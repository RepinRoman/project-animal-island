package UtilityClasses;

import UtilityClasses.ProjectExceptions.TryingToAccessAUtilityClassException;

import java.util.concurrent.ThreadLocalRandom;

public class ProjectRandomizer {
    private ProjectRandomizer() { // Защита от рефлексии!
        throw new TryingToAccessAUtilityClassException("Ошибка! Попытка доступа к служебному классу!");
    }

    public static int getRandom(int from, int to) { // используется для задания "начального количества" животных
        return ThreadLocalRandom.current().nextInt(from, to + 1);
    }

    public static double getRandom(double from, double to) { // Используется для задания "начального веса" животных
        return ThreadLocalRandom.current().nextDouble(from, to + 1);
    }

    public static boolean getRandom(int probability) { // Рассчитывает шанс "поедания" животным другого животного
        int i = ThreadLocalRandom.current().nextInt(0, 100);
        return i < probability;
    }

    public static boolean getRandom() { // Используется для (да/нет) первичного помещения на локацию животных/растений
        return ThreadLocalRandom.current().nextBoolean();
    }
}
