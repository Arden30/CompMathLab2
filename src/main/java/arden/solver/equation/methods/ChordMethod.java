package arden.solver.equation.methods;

import arden.solver.equation.functions.Function;
import arden.solver.equation.models.Solution;

import static java.lang.Math.*;
import static arden.solver.equation.utils.EquationPrinter.*;
public class ChordMethod implements Method {
    @Override
    public String description() {
        return "Метод хорд";
    }

    @Override
    public Solution solve(Function function, double a, double b, double accuracy) {
        StringBuilder steps = new StringBuilder();
        steps.append(getHeaderForChordMethod()).append("\n");

        double xNext = a + 1;
        double x = a;
        int iterations = 0;

        while (abs(xNext - x) > accuracy) {
            if (xNext != a + 1) {
                x = xNext;
            }
            xNext = countNext(function, a, b);

            steps.append(getRowForChordMethod(a, b, xNext, function, x)).append("\n");

            if (function.value(a) * function.value(xNext) < 0) {
                b = xNext;
            } else if (function.value(b) * function.value(xNext) < 0) {
                a = xNext;
            }
            iterations++;
        }

        return new Solution(xNext, function.value(xNext), iterations, steps.toString());
    }

    private double countNext(Function function, double a, double b) {
        return (a * function.value(b) - b * function.value(a)) / (function.value(b) - function.value(a));
    }
}
