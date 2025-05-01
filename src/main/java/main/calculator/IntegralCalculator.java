package main.calculator;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

public class IntegralCalculator {
    private double a;
    private double b;
    private int n;
    private DoubleUnaryOperator f;
    public IntegralCalculator(double a, double b, int n, DoubleUnaryOperator f) {
        this.a = a;
        this.b = b;
        this.n = n;
        this.f = f;
    }
    public double calculate() {
        double h=(b-a)/n;
        double sum = (a+b)/2*h;
        for (int i = 1; i < n; i++) {
            double xi = a + i * h;
            sum += f.applyAsDouble(xi)*h;
        }
        return sum;
    }
}
