package main;

import main.calculator.RunnableIntegralCalculator;

import java.util.function.DoubleUnaryOperator;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private static double function(double x) {
        return Math.cos(x) / Math.pow(x, 3);
    }

    private void run() {
        double a = 1;
        double b = 2;
        int n = 100_000_000;
        DoubleUnaryOperator f = Main::function;
        long start = System.currentTimeMillis();
        int numberOfThreads = 100;
        double delta = (b-a)/numberOfThreads;
        for (int i = 0; i < numberOfThreads; i++) {
            double ai = a + i * delta;
            double bi = ai + delta;
            int ni = n / numberOfThreads;
            Thread.startVirtualThread(new RunnableIntegralCalculator(ai,bi,ni,f,this));
        }
        try {
            synchronized (this) {
                while (finished < numberOfThreads) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long finish = System.currentTimeMillis();

        System.out.println("v = " + totalSum);
        System.out.println("time = " + (finish - start));
    }

    public synchronized void sendResult(double value) {
        totalSum+=value;
        finished++;
        notify();
    }
    double totalSum=0;
    int finished=0;
}
