package main.calculator;

import main.Main;

import java.util.function.DoubleUnaryOperator;

public class RunnableIntegralCalculator implements Runnable{

    private IntegralCalculator integralCalculator;
    private Main main;

    public RunnableIntegralCalculator(double a, double b, int n, DoubleUnaryOperator f, Main main) {
        integralCalculator=new IntegralCalculator(a,b,n,f);
        this.main=main;
    }

    @Override
    public void run() {
        double value=integralCalculator.calculate();
        main.sendResult(value);
    }
}
