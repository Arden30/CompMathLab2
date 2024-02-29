package arden.solver.system.reader;

import arden.solver.charts.SystemPlotter;
import arden.solver.system.models.TaskSystem;
import arden.solver.system.systems.SystemOfEquations;
import arden.solver.system.systems.SystemsStorage;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static arden.solver.equation.utils.EquationPrinter.printString;
import static arden.solver.system.utils.SystemPrinter.*;

public class SysReadFromConsole implements SysReadable {
    private final Scanner scanner = new Scanner(System.in);

    private final List<SystemOfEquations> systemOfEquations;

    public SysReadFromConsole() {
        systemOfEquations = SystemsStorage.getSystems();
    }

    @Override
    public TaskSystem read() {
        SystemOfEquations systemOfEquations = readSystem();
        SwingUtilities.invokeLater(() -> {
            SystemPlotter systemPlotter = new SystemPlotter(systemOfEquations);
            systemPlotter.setVisible(true);
        });

        double x = readX();
        double y = readY();
        double accuracy = readAccuracy();

        return new TaskSystem(systemOfEquations, x, y, accuracy);
    }

    private double readAccuracy() {
        while (true) {
            try {
                printString("Введите точность решения системы: ");
                double accuracy = scanner.nextDouble();
                if (accuracy <= 0) {
                    printString("Точность должна быть неотрицательным числом");
                } else {
                    return accuracy;
                }
            } catch (Exception e) {
                printString("Ошибка ввода точности, попробуйте ещё раз");
                scanner.next();
            }
        }
    }

    private double readX() {
        while (true) {
            try {
                printString("Введите начальное приближение X:");
                return scanner.nextDouble();
            } catch (Exception e) {
                printString("Ошибка ввода X, попробуйте ещё раз");
                scanner.next();
            }
        }
    }

    private double readY() {
        while (true) {
            try {
                printString("Введите начальное приближение Y:");
                return scanner.nextDouble();
            } catch (Exception e) {
                printString("Ошибка ввода Y, попробуйте ещё раз");
                scanner.next();
            }
        }
    }

    private SystemOfEquations readSystem() {
        while (true) {
            try {
                printSystems(systemOfEquations);

                return systemOfEquations.get(scanner.nextInt() - 1);
            } catch (InputMismatchException e) {
                printString("Ошибка ввода номера системы, попробуйте ещё раз");
                scanner.next();
            } catch (IndexOutOfBoundsException e) {
                printString("Такого номера нет, попробуйте ещё раз");
                scanner.next();
            }
        }
    }
}
