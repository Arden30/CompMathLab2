package arden.solver.equation.functions;
import static java.lang.Math.pow;

public class Polynomial implements Function {
    @Override
    public String function() {
        return "-1.38x^3 - 5.42x^2 + 2.57x + 10.95";
    }

    @Override
    public double value(double x) {
        return -1.38 * pow(x, 3) - 5.42 * pow(x, 2) + 2.57 * x + 10.95;
    }

    @Override
    public double derivative(double x) {
        return -4.14 * pow(x, 2) - 10.84 * x + 2.57;
    }

    @Override
    public double secondDerivative(double x) {
        return -8.28 * x - 10.84;
    }
}
