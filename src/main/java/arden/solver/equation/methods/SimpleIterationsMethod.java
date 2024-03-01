package arden.solver.equation.methods;

import arden.solver.equation.functions.Function;
import arden.solver.equation.models.Solution;
import static java.lang.Math.*;
import static arden.solver.equation.utils.EquationPrinter.*;

public class SimpleIterationsMethod implements Method {
    @Override
    public String description() {
        return "Метод простой итерации";
    }

    @Override
    public Solution solve(Function function, double a, double b, double accuracy) {
        double maxDerivative = maxDerivative(function, a, b);
        double lambda = countLambda(function, a, b, maxDerivative);

        Function phi = getPhi(function, lambda);
        converges(function, a, b, lambda);
        double x = a;
        double xNext = a + 100;
        int iterations = 0;
        StringBuilder steps = new StringBuilder();
        steps.append(getHeaderForSimpleIterationsMethod()).append("\n");

        printString("фи в точке а " + phi.derivative(a));
        printString("фи в точке b " + phi.derivative(b));

        while (abs(xNext - x) > accuracy || abs(function.value(xNext)) > accuracy) {
            if (xNext != a + 100) {
                x = xNext;
            }

            xNext = phi.value(x);

            steps.append(getRowForSimpleIterationsMethod(x, xNext, function)).append("\n");
            iterations++;

        }

        return new Solution(xNext, function.value(xNext), iterations, steps.toString());
    }

    private double maxDerivative(Function function, double a, double b) {
        double max = -1;
        for (double i = a; i < b; i += 0.01) {
            if (abs(function.derivative(i)) > max) {
                max = abs(function.derivative(i));
            }
        }
        return max;
    }

    private double countLambda(Function function, double a, double b, double max) {
        double sign;
        if (function.derivative(a) != 0) {
            sign = function.derivative(a) / abs(function.derivative(a));
        } else {
            sign = function.derivative(a + 0.1) / abs(function.derivative(a + 0.1));
        }

        for (double i = a; i < b; i += 0.01) {
            if (function.derivative(i) * sign < 0) {
                printString("Функция не монотонна на данном отрезке, значение лямбды не гарантировано быть правильным");
                return sign * (1 / max);
            }
        }

        return (-1) * sign * (1 / max);
    }

    private Function getPhi(Function function, double lambda) {
        return new Function() {
            @Override
            public String function() {
                return null;
            }

            @Override
            public double value(double x) {
                return x + lambda * (function.value(x));
            }

            @Override
            public double derivative(double x) {
                return 1 + lambda * function.derivative(x);
            }

            @Override
            public double secondDerivative(double x) {
                return 0;
            }
        };
    }

    private void converges(Function function, double a, double b, double lambda) {
        Function phi = getPhi(function, lambda);
        double max = maxDerivative(phi, a, b);
        if (max < 1) {
            printString("Достаточное условие сходимости выполняется, скорость сходимости высокая");
        } else {
            printString("Достаточное условие сходимости не выполняется, сходимость непредсказуема, ответ может быть неверным");
        }
    }
}
