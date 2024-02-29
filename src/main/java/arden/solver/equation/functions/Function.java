package arden.solver.equation.functions;

import arden.solver.equation.exceptions.RootSegmentException;

public interface Function {
    String function();
    double value(double x);
    double derivative(double x);
    double secondDerivative(double x);
    default String verify(double a, double b) {
        double f_a = value(a);
        double f_b = value(b);

        if (f_a * f_b >= 0) {
            throw new RootSegmentException("На отрезке нет корней или их несколько, уточните границы интервала");
        }

        // если функция больше одного раза сменила знак, то у нее не меньше 2 корней
        double sign = f_a;
        int cnt = 0;
        for (double i = a; i < b; i += 0.01) {
            if (sign * value(i) < 0) {
                sign = value(i);
                cnt++;
            }
            if (cnt > 1) {
                throw new RootSegmentException("На отрезке несколько корней, уточните границы интервала");
            }
        }

        return "Верификация пройдена: на данном отрезке у уравнения есть корень";
    }
}
