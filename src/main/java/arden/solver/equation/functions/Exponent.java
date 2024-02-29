package arden.solver.equation.functions;
import static java.lang.Math.*;

public class Exponent implements Function {
    @Override
    public String function() {
        return "e^(x^2) - 4";
    }

    @Override
    public double value(double x) {
        return exp(pow(x, 2)) - 4;
    }

    @Override
    public double derivative(double x) {
        return 2*x*exp(pow(x, 2));
    }

    @Override
    public double secondDerivative(double x) {
        return 4*pow(x, 2)*exp(pow(x, 2)) + 2*exp(pow(x, 2));
    }
}
