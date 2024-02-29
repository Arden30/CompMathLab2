package arden.solver.equation.methods;

import arden.solver.equation.exceptions.RootSegmentException;
import arden.solver.equation.functions.Function;
import arden.solver.equation.models.Solution;
import static arden.solver.equation.utils.EquationPrinter.*;
import static java.lang.Math.abs;

public class NewtonMethod implements Method {
    @Override
    public String description() {
        return "Метод Ньютона";
    }

    @Override
    public Solution solve(Function function, double a, double b, double accuracy) {
        StringBuilder steps = new StringBuilder();
        steps.append(getHeaderForNewtonMethod()).append("\n");

        if (checkEffectiveness(function,a,b)) {
            printString("Производные монотонны, метод будет эффективен");
        }

        double x = approximation(function, a, b);
        double xNext = x + 1;
        int iterations = 0;

        while (abs(xNext - x) > accuracy && abs(function.value(x)) > accuracy) {
            if (xNext != x + 1) {
                x = xNext;
            }

            xNext = countNext(function, x);

            steps.append(getRowForNewtonMethod(function, x, xNext)).append("\n");

            iterations++;
        }

        return new Solution(xNext, function.value(xNext), iterations, steps.toString());
    }

    private boolean checkEffectiveness(Function function, double a, double b) {
        double sign = function.derivative(a);
        for (double i = a; i < b; i += 0.01) {

            if (function.derivative(i) == 0) {
                printString("Производная равна нулю, нет гарантии эффективности");
                return false;
            }
            if (sign * function.derivative(i) < 0 ) {
                printString("Производная меняет знак, нет гарантии эффективности метода");
                return false;
            }
        }

        sign = function.secondDerivative(a);
        for (double i = a; i < b; i += 0.01) {
            if (sign * function.secondDerivative(i) < 0) {
                printString("Вторая производная меняет знак, нет гарантии эффективности метода");
                return false;
            }
        }

        return true;
    }
    private double approximation(Function function, double a, double b) {
        if (function.value(a) * function.secondDerivative(a) > 0 && function.derivative(a) != 0) {
            return a;
        } else if (function.value(b) * function.secondDerivative(b) > 0 && function.derivative(b) != 0) {
            return b;
        } else {
            printString("Сходимость метода не гарантирована, в качестве приближения будет выбрана середина интервала");
            return (a + b) / 2;
        }
    }

    private double countNext(Function function, double x) {
        return x - (function.value(x) / function.derivative(x));
    }
}
