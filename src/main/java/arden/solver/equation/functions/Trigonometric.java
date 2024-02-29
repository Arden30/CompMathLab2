package arden.solver.equation.functions;
import static java.lang.Math.*;

public class Trigonometric implements Function{
    @Override
    public String function() {
        return "sin(x^2) + cos(x)";
    }

    @Override
    public double value(double x) {
        return sin(pow(x, 2)) + cos(x);
    }

    @Override
    public double derivative(double x) {
        return 2 * x * cos(pow(x, 2)) - sin(x);
    }

    @Override
    public double secondDerivative(double x) {
        return 2 * cos(pow(x, 2)) - 4 * pow(x, 2) * sin(pow(x, 2)) - cos(x);
    }
}
