package main;

import main.calculator.IntegralCalculator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.function.DoubleUnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegralTest {
    double delta=1e-6;
    @ParameterizedTest
    @CsvSource({"1,0.54030230586","1.5,0.02095917086","2,-0.05201835456"})
    void testFunction(double x,double expected) {
        double actual=Math.cos(x) / Math.pow(x, 3);
        assertEquals(expected,actual,delta);
    }
    @ParameterizedTest
    @CsvSource({"0.5,1,100_000_000,1.168729","1,2,1000_000,0.0859711","5,10,10_000_000,0.006277"})
    void testIntegralCalculator(double a, double b, int n,double expected) {
        DoubleUnaryOperator f =x->Math.cos(x) / Math.pow(x, 3);
        IntegralCalculator integralCalculator = new IntegralCalculator(a,b,n,f);
        double result = integralCalculator.calculate();
        assertEquals(expected,result,delta);
    }
}
