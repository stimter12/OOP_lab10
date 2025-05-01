package main.logic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import main.calculator.RunnableIntegralCalculator;

import java.util.function.DoubleUnaryOperator;

public class MainController {
    @FXML
    private TextField numberOfThreads;
    @FXML
    private TextField n;

    public void calculate() {
        int intN=Integer.parseInt(n.getText());
        int intNumberOfThreads=Integer.parseInt(numberOfThreads.getText());
        Pair<Double,Long> pair=run(intN,intNumberOfThreads);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Results");
        alert.setHeaderText(null);
        alert.setContentText("Result of the calculation: " + pair.getKey()+
                "\nTime needed for calculation: "+pair.getValue());
        alert.showAndWait();
    }

    private static double function(double x) {
        return Math.cos(x) / Math.pow(x, 3);
    }

    private Pair<Double,Long> run(int n, int numberOfThreads) {
        double a = 1;
        double b = 2;
        DoubleUnaryOperator f = MainController::function;
        long start = System.currentTimeMillis();
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

        long end = System.currentTimeMillis();
        long time = end - start;
        return new Pair<>(totalSum,time);
    }

    double totalSum=0;
    int finished=0;

    public synchronized void sendResult(double value) {
        totalSum+=value;
        finished++;
        notify();
    }

    public void close() {
        Platform.exit();
    }
    public void info() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("OOP_lab10");
        alert.setContentText("this program develop for OOP_lab10");
        alert.showAndWait();
    }
}
