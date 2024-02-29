package arden.solver.equation.functions;
import static java.lang.Math.*;

public class SquareEquation implements Function {
    @Override
    public String function() {
        return "x^2 - 5x + 4";
    }

    @Override
    public double value(double x) {
        return pow(x, 2) - 5 * x + 4;
    }

    @Override
    public double derivative(double x) {
        return 2 * x - 5;
    }

    @Override
    public double secondDerivative(double x) {
        return 2;
    }
}
