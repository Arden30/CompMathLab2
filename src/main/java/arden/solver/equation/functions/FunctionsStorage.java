package arden.solver.equation.functions;

import java.util.ArrayList;
import java.util.List;

public class FunctionsStorage {
    private final static List<Function> FUNCTIONS = new ArrayList<>();

    public static List<Function> getFunctions() {
        setFunctions();

        return FUNCTIONS;
    }

    private static void setFunctions() {
        FUNCTIONS.add(new Polynomial());
        FUNCTIONS.add(new Exponent());
        FUNCTIONS.add(new Trigonometric());
        FUNCTIONS.add(new SquareEquation());
        FUNCTIONS.add(new CubeEquation());
    }
}
