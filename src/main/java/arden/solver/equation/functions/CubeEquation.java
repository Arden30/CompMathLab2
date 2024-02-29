package arden.solver.equation.functions;
import static java.lang.Math.*;

public class CubeEquation implements Function {

    @Override
    public String function() {
        return "x^3 - x + 4";
    }

    @Override
    public double value(double x) {
        return pow(x, 3) - x + 4;
    }

    @Override
    public double derivative(double x) {
        return 3 * pow(x, 2) - 1;
    }

    @Override
    public double secondDerivative(double x) {
        return 6 * x;
    }
}
