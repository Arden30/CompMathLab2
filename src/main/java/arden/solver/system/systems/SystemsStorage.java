package arden.solver.system.systems;

import java.util.ArrayList;
import java.util.List;

public class SystemsStorage {
    private final static List<SystemOfEquations> SYSTEM_OF_EQUATIONS = new ArrayList<>();

    public static List<SystemOfEquations> getSystems() {
        setSystems();

        return SYSTEM_OF_EQUATIONS;
    }

    private static void setSystems() {
        SYSTEM_OF_EQUATIONS.add(new PolynomialSystem());
        SYSTEM_OF_EQUATIONS.add(new TrigonometricSystem());
    }
}
