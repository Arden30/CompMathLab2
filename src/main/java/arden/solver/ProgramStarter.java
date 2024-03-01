package arden.solver;

import arden.solver.equation.methods.Method;
import arden.solver.equation.methods.MethodsStorage;
import arden.solver.equation.models.Solution;
import arden.solver.equation.models.Task;
import arden.solver.equation.reader.ReadFromConsole;
import arden.solver.equation.reader.ReadFromFile;
import arden.solver.equation.reader.Readable;
import arden.solver.system.algorithm.SimpleIterations;
import arden.solver.system.models.SolutionSystem;
import arden.solver.system.models.TaskSystem;
import arden.solver.system.reader.SysReadFromConsole;
import arden.solver.system.reader.SysReadFromFile;
import arden.solver.system.reader.SysReadable;

import java.nio.file.Path;
import java.util.Scanner;

import static arden.solver.equation.utils.EquationPrinter.*;
import static arden.solver.system.utils.SystemPrinter.*;

public class ProgramStarter {
    private final Scanner scanner = new Scanner(System.in);
    public ProgramStarter() {
        MethodsStorage.setMethods();
    }
    public void start() {
        while (true) {
            try {
                int sysOrEq = equationOrSystem();
                int format = readFormatInput();

                switch (sysOrEq) {
                    case 1 -> {
                        Readable readable = inputEquation(format);

                        Task task = readable.read();
                        Method method = task.method();
                        Solution solution = method.solve(task.function(), task.a(), task.b(), task.accuracy());

                        outputEquation(readFormatOutput(), solution);
                    }
                    case 2 -> {
                        SysReadable readable = inputSystem(format);
                        TaskSystem taskSystem = readable.read();
                        SimpleIterations simpleIterations = new SimpleIterations();
                        SolutionSystem solution = simpleIterations.solve(taskSystem.systemOfEquations(), taskSystem.x(), taskSystem.y(), taskSystem.accuracy());

                        outputSystem(readFormatOutput(), solution, taskSystem);
                    }
                    default -> throw new Exception("Такого номера нет, попробуйте ещё раз");
                }

                if (scanner.hasNext()) {
                    System.exit(0);
                }

            } catch (Exception e) {
                printString(e.getMessage());
                scanner.next();
            }
        }
    }

    private int equationOrSystem() {
        while (true) {
            try {
                printString("Выберите задачу, которую хотите решить: уравнение методом на выбор (введите 1) или система уравнений методом простых итераций (введите 2)");
                int sysOrEq = scanner.nextInt();

                if (sysOrEq != 1 && sysOrEq != 2) {
                    throw new IllegalArgumentException("Такого номера нет, попробуйте ещё раз");
                }

                return sysOrEq;
            } catch (IllegalArgumentException e) {
                printString(e.getMessage());
            } catch (Exception e) {
                printString("Ошибка ввода формата, попробуйте ещё раз");
                scanner.next();
            }
        }
    }

    private SysReadable inputSystem(int format) throws Exception {
        SysReadable readable;

        switch (format) {
            case 1 -> readable = new SysReadFromConsole();
            case 2 -> {
                printString("""
                                В файле данные должны быть указаны в порядке:
                                1. Номер системы уравнения
                                2. Приближение x
                                3. Приближение y
                                4. Точность поиска решения
                                """);
                readable = new SysReadFromFile(readPath());
            }
            default -> throw new Exception("Такого формата нет, попробуйте ещё раз");
        }

        return readable;
    }

    private Readable inputEquation(int format) throws Exception {
        Readable readable;
        switch (format) {
            case 1 -> readable = new ReadFromConsole();
            case 2 -> {
                printString("""
                                В файле данные должны быть указаны в порядке:
                                1. Номер уравнения
                                2. Левая граница поиска корня (а)
                                3. Правая граница поиска корня (b)
                                4. Точность поиска корня
                                5. Название метода для вычисления корня
                                """);
                readable = new ReadFromFile(readPath());
            }
            default -> throw new Exception("Такого формата нет, попробуйте ещё раз");
        }

        return readable;
    }
    private int readFormatInput() {
        while (true) {
            try {
                printString("Выберите формат ввода: консоль (введите 1) или файл (введите 2)");
                int format = scanner.nextInt();

                if (format != 1 && format != 2) {
                    throw new IllegalArgumentException("Такого номера нет, попробуйте ещё раз");
                }

                return format;
            } catch (IllegalArgumentException e) {
                printString(e.getMessage());
            } catch (Exception e) {
                printString("Ошибка ввода формата, попробуйте ещё раз");
                scanner.next();
            }
        }
    }

    private void outputEquation(int format, Solution solution) throws Exception {
        switch (format) {
            case 1 -> printSolutionInConsole(solution);
            case 2 -> {
                Path path = readPath();
                printSolutionInFile(solution, path);
            }
            default -> throw new Exception("Такого формата нет, попробуйте ещё раз");
        }
    }

    private void outputSystem(int format, SolutionSystem solution, TaskSystem system) throws Exception {
        switch (format) {
            case 1 -> printSystemSolutionInConsole(solution, system);
            case 2 -> {
                Path path = readPath();
                printSystemSolutionInFile(solution, path);
            }
            default -> throw new Exception("Такого формата нет, попробуйте ещё раз");
        }
    }

    private int readFormatOutput() {
        while (true) {
            try {
                printString("Выберите формат вывода: консоль (введите 1) или файл (введите 2)");
                int format = scanner.nextInt();

                if (format != 1 && format != 2) {
                    throw new IllegalArgumentException("Такого номера нет, попробуйте ещё раз");
                }

                return format;
            } catch (IllegalArgumentException e) {
                printString(e.getMessage());
            } catch (Exception e) {
                printString("Ошибка ввода формата, попробуйте ещё раз");
                scanner.next();
            }
        }
    }

    private Path readPath() {
        while (true) {
            try {
                printString("Укажите путь к файлу: ");
                return Path.of(scanner.next());
            } catch (Exception e) {
                printString("Файла по заданному пути нет, попробуйте ещё раз");
            }
        }
    }
}
