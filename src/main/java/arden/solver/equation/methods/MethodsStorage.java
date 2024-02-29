package arden.solver.equation.methods;

import java.util.HashMap;
import java.util.Map;

public class MethodsStorage {
    private final static Map<String, Method> METHODS = new HashMap<>();

    public static Map<String, Method> getMethods() {
        return METHODS;
    }

    public static void setMethods() {
        METHODS.put("chord", new ChordMethod());
        METHODS.put("newton", new NewtonMethod());
        METHODS.put("iterations", new SimpleIterationsMethod());
    }
}
