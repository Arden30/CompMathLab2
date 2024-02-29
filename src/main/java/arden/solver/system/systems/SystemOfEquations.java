package arden.solver.system.systems;

public interface SystemOfEquations {
    String system();
    Function first();
    Function second();
}
