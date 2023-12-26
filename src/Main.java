import SimulationCore.WorldInitializer;
import SimulationCore.WorldLifeCycle;

import java.util.Scanner;

import static UtilityClasses.ProjectSettings.*;

public class Main {
    public static void main(String[] args) {
        printWelcomeMessage(); // Приветственное сообщение
        Scanner scanner = new Scanner(System.in);
        printEnter(scanner); // Кнопка "START"
        countdown(3); // Обратный отсчёт

        WorldInitializer world = new WorldInitializer(FIELD_TO_SIZE_Y, FIELD_TO_SIZE_X); // Создание и инициализация "острова"
        new WorldLifeCycle(world).start(); // Запуск симуляции
    }
}
