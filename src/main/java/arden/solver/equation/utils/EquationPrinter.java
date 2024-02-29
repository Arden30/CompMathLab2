package arden.solver.equation.utils;

import arden.solver.equation.functions.Function;
import arden.solver.equation.methods.Method;
import arden.solver.equation.models.Solution;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

public class EquationPrinter {
    private EquationPrinter() {

    }

    public static void printString(String s) {
        System.out.println(s);
    }

    public static void printFunctions(List<Function> list) {
        printString("Выберите одну из функций для поиска корней (введите её номер):");
        for (int i = 0; i < list.size(); i++) {
            printString(i + 1 + ": " + list.get(i).function());
        }
    }

    public static void printMethods(Map<String, Method> methods) {
        printString("Введите название метода (до знака двоеточия):");
        for (Map.Entry<String, Method> methodEntry: methods.entrySet()) {
            printString(methodEntry.getKey() + ": " + methodEntry.getValue().description());
        }
    }

    public static String getHeaderForChordMethod() {
        return String.format("%" + 8 + "s", "") + "a"
                + String.format("%" + 13 + "s", "") + "b"
                + String.format("%" + 11 + "s", "") + "x"
                + String.format("%" + 9 + "s", "") + "F(a)"
                + String.format("%" + 9 + "s", "") + "F(b)"
                + String.format("%" + 7 + "s", "") + "|x(k+1) - x(k)|";
    }

    public static String getRowForChordMethod(double a, double b, double xNext, Function function, double x) {
        return String.format("%" + 5 + "s", "") + String.format("%.5f", a)
                + String.format("%" + 5 + "s", "") + String.format("%.5f", b)
                + String.format("%" + 5 + "s", "") + String.format("%.5f", xNext)
                + String.format("%" + 5 + "s", "") + String.format("%.5f", function.value(a))
                + String.format("%" + 5 + "s", "") + String.format("%.5f", function.value(b))
                + String.format("%" + 5 + "s", "") + String.format("%.5f", abs(xNext - x));
    }

    public static String getHeaderForNewtonMethod() {
        return String.format("%" + 10 + "s", "") + "x(k)"
                + String.format("%" + 10 + "s", "") + "f(x(k))"
                + String.format("%" + 10 + "s", "") + "f'(x(k))"
                + String.format("%" + 10 + "s", "") + "x(k+1)"
                + String.format("%" + 10 + "s", "") + "|x(k+1) - x(k)|";
    }

    public static String getRowForNewtonMethod(Function function, double x, double xNext) {
        return String.format("%" + 7 + "s", "") + String.format("%.5f", x)
                + String.format("%" + 10 + "s", "") + String.format("%.5f", function.value(x))
                + String.format("%" + 7 + "s", "") + String.format("%.5f", function.derivative(x))
                + String.format("%" + 10 + "s", "") + String.format("%.5f", xNext)
                + String.format("%" + 10 + "s", "") + String.format("%.5f", abs(xNext - x));
    }

    public static String getHeaderForSimpleIterationsMethod() {
        return String.format("%" + 10 + "s", "") + "x(k)"
                + String.format("%" + 10 + "s", "") + "x(k+1)"
                + String.format("%" + 10 + "s", "") + "f(x(k+1))"
                + String.format("%" + 10 + "s", "") + "|x(k+1) - x(k)|";
    }

    public static String getRowForSimpleIterationsMethod(double x, double xNext, Function function) {
        return String.format("%" + 7 + "s", "") + String.format("%.5f", x)
                + String.format("%" + 10 + "s", "") + String.format("%.5f", xNext)
                + String.format("%" + 10 + "s", "") + String.format("%.5f", function.value(xNext))
                + String.format("%" + 10 + "s", "") + String.format("%.5f", abs(xNext - x));
    }
    public static void printSolutionInConsole(Solution solution) {
        printString(solution.steps());
        printString("Корень: " + String.format("%.5f", solution.x()));
        printString("Значение функции: " + String.format("%.5f", solution.f_x()));
        printString("Число итераций: " + solution.iterations());
    }

    public static void printSolutionInFile(Solution solution, Path path) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(solution.steps());
            bufferedWriter.write("Корень: " + solution.x() + "\n");
            bufferedWriter.write("Значение функции: " + solution.f_x() + "\n");
            bufferedWriter.write("Число итераций: " + solution.iterations() + "\n");
            printString("Результат был успешно выведен в файл!");
        } catch (IOException e) {
            printString("Файла по заданному пути нет, попробуйте ещё раз");
        }
    }
}
