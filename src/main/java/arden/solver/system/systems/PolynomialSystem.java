package arden.solver.system.systems;

import static java.lang.Math.pow;

public class PolynomialSystem implements SystemOfEquations {
    @Override
    public String system() {
        return """
                0,1x^2 + x + 0,2y^2 - 0,3 = 0
                   0,2x^2 + y + 0,1xy - 0,7 = 0
                """;
    }

    @Override
    public Function first() {
        return (x, y) -> 0.1 * pow(x, 2) + x + 0.2 * pow(y, 2) - 0.3;
    }

    @Override
    public Function second() {
        return (x, y) -> 0.2 * pow(x, 2) + y + 0.1 * x * y - 0.7;
    }
}
