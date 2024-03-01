package arden.solver.system.utils;

import arden.solver.system.models.SolutionSystem;
import arden.solver.system.models.TaskSystem;
import arden.solver.system.systems.SystemOfEquations;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static arden.solver.equation.utils.EquationPrinter.*;
import static java.lang.Math.abs;

public class SystemPrinter {
    private SystemPrinter() {

    }

    public static void printSystems(List<SystemOfEquations> list) {
        printString("Выберите одну из систем для поиска корней (введите её номер):");
        for (int i = 0; i < list.size(); i++) {
            printString(i + 1 + ": " + list.get(i).system());
        }
    }

    public static void printSystemSolutionInConsole(SolutionSystem solutionSystem, TaskSystem system) {
        printString(solutionSystem.steps());
        printString("x = " + String.format("%.5f", solutionSystem.x()));
        printString("y = " + String.format("%.5f", solutionSystem.y()));
        printString("Число итераций: " + solutionSystem.iterations());
        printString("Вектор погрешности X: " + String.format("%.5f", solutionSystem.inaccuracyX()));
        printString("Вектор погрешности Y: " + String.format("%.5f", solutionSystem.inaccuracyY()));
        printString("Первая функция: " + String.format("%.15f", system.systemOfEquations().first().value(solutionSystem.x(), solutionSystem.y())));
        printString("ВТорая функция: " + String.format("%.15f", system.systemOfEquations().second().value(solutionSystem.x(), solutionSystem.y())));
    }

    public static void printSystemSolutionInFile(SolutionSystem solutionSystem, Path path) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(solutionSystem.steps());
            bufferedWriter.write("x = " + String.format("%.5f", solutionSystem.x()) + "\n");
            bufferedWriter.write("y = " + String.format("%.5f", solutionSystem.y()) + "\n");
            bufferedWriter.write("Число итераций: " + solutionSystem.iterations() + "\n");
            bufferedWriter.write("Вектор погрешности X: " + String.format("%.5f", solutionSystem.inaccuracyX()) + "\n");
            bufferedWriter.write("Вектор погрешности Y: " + String.format("%.5f", solutionSystem.inaccuracyY()) + "\n");
            printString("Результат был успешно выведен в файл!");
        } catch (IOException e) {
            printString("Файла по заданному пути нет, попробуйте ещё раз");
        }
    }

    public static String getHeaderForSimpleIterationsSystemMethod() {
        return String.format("%" + 10 + "s", "") + "x"
                + String.format("%" + 13 + "s", "") + "y"
                + String.format("%" + 10 + "s", "") + "|x(k+1) - x(k)|"
                + String.format("%" + 3 + "s", "") + "|y(k+1) - y(k)|";
    }

    public static String getRowForSimpleIterationsSystemMethod(double xNext, double x, double yNext, double y) {
        return String.format("%" + 7 + "s", "") + String.format("%.5f", xNext)
                + String.format("%" + 7 + "s", "") + String.format("%.5f", yNext)
                + String.format("%" + 10 + "s", "") + String.format("%.5f", abs(xNext - x))
                + String.format("%" + 10 + "s", "") + String.format("%.5f", abs(yNext - y));
    }
}
