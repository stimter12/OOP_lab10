package main.calculator;

import main.logic.MainController;

import java.util.function.DoubleUnaryOperator;

public class RunnableIntegralCalculator implements Runnable{

    private IntegralCalculator integralCalculator;
    private MainController mainController;

    public RunnableIntegralCalculator(double a, double b, int n, DoubleUnaryOperator f, MainController mainController) {
        integralCalculator=new IntegralCalculator(a,b,n,f);
        this.mainController=mainController;
    }

    @Override
    public void run() {
        double value=integralCalculator.calculate();
        mainController.sendResult(value);
    }
}
