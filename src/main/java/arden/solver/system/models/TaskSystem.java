package arden.solver.system.models;

import arden.solver.system.systems.SystemOfEquations;

public record TaskSystem(SystemOfEquations systemOfEquations, double x, double y, double accuracy) {
}
