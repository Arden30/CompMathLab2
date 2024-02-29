package arden.solver.system.algorithm;

import arden.solver.system.models.SolutionSystem;
import arden.solver.system.systems.Function;
import arden.solver.system.systems.SystemOfEquations;

import static arden.solver.equation.utils.EquationPrinter.*;
import static java.lang.Math.abs;
import static arden.solver.system.utils.SystemPrinter.*;
public class SimpleIterations {

    public SolutionSystem solve(SystemOfEquations systemOfEquations, double x, double y, double accuracy) {
        StringBuilder steps = new StringBuilder();
        steps.append(getHeaderForSimpleIterationsSystemMethod()).append("\n");

        Function varX = getX(systemOfEquations.first());
        Function varY = getY(systemOfEquations.second());
        converges(varX, varY, x, y);

        double inaccuracyX;
        double inaccuracyY;
        int iterations = 0;
        double xNext = 0;
        double yNext = 0;

        do {
            if (xNext != 0) {
                x = xNext;
                y = yNext;
            }

            xNext = varX.value(x, y);
            yNext = varY.value(x, y);

            inaccuracyX = abs(xNext - x);
            inaccuracyY = abs(yNext - y);

            steps.append(getRowForSimpleIterationsSystemMethod(xNext, x, yNext, y)).append("\n");

            iterations++;
        } while (abs(xNext - x) > accuracy && abs(yNext - y) > accuracy);

        return new SolutionSystem(x, y, iterations, inaccuracyX, inaccuracyY, steps.toString());
    }

    private void converges(Function varX, Function varY, double x, double y) {


        double first = abs(varX.derivativeX(x, y)) + abs(varX.derivativeY(x, y));
        double second = abs(varY.derivativeX(x, y)) + abs(varY.derivativeY(x, y));

        if (first <= 1 && second <= 1) {
            printString("Достаточное условие сходимости выполнено, решение гарантировано");
        } else {
            printString("Достаточное условие сходимости не выполнено, исход неизвестен");
        }
    }

    private Function getX(Function function) {
        return (x, y) -> x + countLambdaX(function, x, y) * function.value(x, y);
    }

    private Function getY(Function function) {
        return (x, y) -> y + countLambdaY(function, x, y) * function.value(x, y);
    }

    private double countLambdaX(Function function, double x, double y) {
        return - 1 / function.derivativeX(x, y);
    }

    private double countLambdaY(Function function, double x, double y) {
        return - 1 / function.derivativeY(x, y);
    }
}
