package arden.solver.system.systems;

public interface Function {
    double value(double x, double y);

    default double derivativeX(double x, double y) {
        return (value(x + 0.0000000000001, y) - value(x, y)) / 0.0000000000001;
    }

    default double derivativeY(double x, double y) {
        return (value(x, y + 0.0000000000001) - value(x, y)) / 0.0000000000001;
    }
}
