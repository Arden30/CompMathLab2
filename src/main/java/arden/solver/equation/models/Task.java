package arden.solver.equation.models;

import arden.solver.equation.functions.Function;
import arden.solver.equation.methods.Method;

public record Task(Method method, Function function, double a, double b, double accuracy) {
}
