package arden.solver.equation.reader;

import arden.solver.charts.EquationPlotter;
import arden.solver.equation.exceptions.RootSegmentException;
import arden.solver.equation.functions.Function;
import arden.solver.equation.functions.FunctionsStorage;
import arden.solver.equation.methods.Method;
import arden.solver.equation.methods.MethodsStorage;
import arden.solver.equation.models.Task;

import javax.swing.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static arden.solver.equation.utils.EquationPrinter.printString;

public class ReadFromFile implements Readable {
    private final List<Function> functions;

    private final Map<String, Method> methods = MethodsStorage.getMethods();
    private final Path path;

    public ReadFromFile(Path path) {
        this.path = path;
        functions = FunctionsStorage.getFunctions();
    }

    @Override
    public Task read() {
        try (Scanner scanner = new Scanner(path)) {
            Function function = functions.get(scanner.nextInt() - 1);

            SwingUtilities.invokeLater(() -> {
                EquationPlotter equationPlotter = new EquationPlotter(function);
                equationPlotter.setVisible(true);
            });

            double a = scanner.nextDouble();
            double b = scanner.nextDouble();
            printString(function.verify(a, b));

            double accuracy = scanner.nextDouble();
            if (accuracy <= 0) {
                printString("Точность должна быть больше 0");
            }

            String method = scanner.next();
            if (!methods.containsKey(method)) {
                printString("Метода с названием " + method + " нет в списке");
                System.exit(1);
            }

            return new Task(methods.get(method), function, a, b, accuracy);
        } catch (RootSegmentException e) {
            printString(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            printString("Ошибка данных в файле, проверьте, что формат ввода соответствует предложенному и в данных нет ошибок");
            System.exit(1);
        }

        return null;
    }
}
