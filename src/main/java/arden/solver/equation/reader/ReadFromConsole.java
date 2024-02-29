package arden.solver.equation.reader;

import arden.solver.charts.EquationPlotter;
import arden.solver.equation.exceptions.RootSegmentException;
import arden.solver.equation.functions.Function;
import arden.solver.equation.functions.FunctionsStorage;
import arden.solver.equation.methods.Method;
import arden.solver.equation.methods.MethodsStorage;
import arden.solver.equation.models.Task;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static arden.solver.equation.utils.EquationPrinter.*;

public class ReadFromConsole implements Readable {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Function> functions;
    private final Map<String, Method> methods = MethodsStorage.getMethods();
    private double a;
    private double b;

    public ReadFromConsole() {
        functions = FunctionsStorage.getFunctions();
    }

    @Override
    public Task read() {
        Function function = readFunction();

        SwingUtilities.invokeLater(() -> {
            EquationPlotter equationPlotter = new EquationPlotter(function);
            equationPlotter.setVisible(true);
        });

        a = readLeftBoundary();
        b = readRightBoundary(a);
        checkBoundaries(function);

        double accuracy = readAccuracy();
        Method method = readMethod();

        return new Task(method, function, a, b, accuracy);
    }

    private Function readFunction() {
        while (true) {
            try {
                printFunctions(functions);

                return functions.get(scanner.nextInt() - 1);
            } catch (InputMismatchException e) {
                printString("Ошибка ввода номера функции, попробуйте ещё раз");
                scanner.next();
            } catch (IndexOutOfBoundsException e) {
                printString("Такого номера нет, попробуйте ещё раз");
                scanner.next();
            }
        }
    }

    private double readLeftBoundary() {
        while (true) {
            try {
                printString("Введите левую границу a:");
                return scanner.nextDouble();
            } catch (Exception e) {
                printString("Ошибка ввода границы a, попробуйте ещё раз");
                scanner.next();
            }
        }
    }

    private double readRightBoundary(double a) {
        while (true) {
            try {
                printString("Введите правую границу b:");
                double b = scanner.nextDouble();
                if (a >= b) {
                    printString("Граница b должна быть строго больше границы a, введите ещё раз");
                } else {
                    return b;
                }
            } catch (Exception e) {
                printString("Ошибка ввода границы b, попробуйте ещё раз");
                scanner.next();
            }
        }
    }

    private void checkBoundaries(Function function) {
        while (true) {
            try {
                printString(function.verify(a, b));
                return;
            } catch (RootSegmentException e) {
                printString(e.getMessage());
                a = readLeftBoundary();
                b = readRightBoundary(a);
            }
        }
    }

    private double readAccuracy() {
        while (true) {
            try {
                printString("Введите точность: ");
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

    private Method readMethod() {
        while(true) {
            printMethods(methods);

            String name = scanner.next();

            if (!methods.containsKey(name)) {
                printString("Такого метода нет, попробуйте ещё раз");
            } else {
                return methods.get(name);
            }
        }
    }
}
