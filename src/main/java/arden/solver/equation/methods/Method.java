package arden.solver.equation.methods;

import arden.solver.equation.functions.Function;
import arden.solver.equation.models.Solution;

public interface Method {
    String description();
    Solution solve(Function function, double a, double b, double accuracy);
}
