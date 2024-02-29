package arden.solver.system.reader;

import arden.solver.charts.SystemPlotter;
import arden.solver.system.models.TaskSystem;
import arden.solver.system.systems.SystemOfEquations;
import arden.solver.system.systems.SystemsStorage;

import javax.swing.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import static arden.solver.equation.utils.EquationPrinter.printString;

public class SysReadFromFile implements SysReadable {
    private final List<SystemOfEquations> systemOfEquations;
    private final Path path;

    public SysReadFromFile(Path path) {
        this.path = path;
        systemOfEquations = SystemsStorage.getSystems();
    }
    @Override
    public TaskSystem read() {
        try (Scanner scanner = new Scanner(path)) {
            SystemOfEquations system = systemOfEquations.get(scanner.nextInt() - 1);
            SwingUtilities.invokeLater(() -> {
                SystemPlotter systemPlotter = new SystemPlotter(system);
                systemPlotter.setVisible(true);
            });

            double x = scanner.nextDouble();
            double y = scanner.nextDouble();

            double accuracy = scanner.nextDouble();
            if (accuracy <= 0) {
                printString("Точность должна быть больше 0");
            }

            return new TaskSystem(system, x, y, accuracy);
        } catch (Exception e) {
            printString("Ошибка данных в файле, проверьте, что формат ввода соответствует предложенному и в данных нет ошибок");
            System.exit(1);
        }

        return null;
    }
}
