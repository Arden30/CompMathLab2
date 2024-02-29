package arden.solver.system.systems;

import static java.lang.Math.*;

public class TrigonometricSystem implements SystemOfEquations {
    @Override
    public String system() {
        return """
                tg(xy + 0,1) - x^2 = 0
                   x^2 + 2y^2 - 1 = 0
                """;
    }

    @Override
    public Function first() {
        return (x, y) -> tan(x * y + 0.1) - pow(x, 2);
    }

    @Override
    public Function second() {
        return (x, y) -> pow(x, 2) + 2 * pow(y, 2) - 1;
    }
}
