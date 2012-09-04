package ru.nsu.g6210.jidkov.calc_methods.lab1;

/**
 *
 *  ласс ахождени€ корн€ уровнени€ x^3 - 10x + 5 = 0 методом хорд
 *
 * x = -3,3876190584754156 
 *
 * User: jdev
 * Date: 25.09.2007
 */
public class ChordsMethod {

    public static void main(String[] args)
    {
        double res = chordsMethod(-3.5,-3.1);
    }

    private static double chordsMethod(double a, double b)
    {
        // x (k - 1)-ое
        double x_k_m1  = a;
        // x k-ое
        double x_k = b;
        // x (k + 1)-ое
        double x_k_p1;
        while (Math.abs(f(x_k) - f(x_k_m1)) > 0.0000000001)
        {
            double f = f(x_k);
            double f_ = (f(x_k) - f(x_k_m1)) / (x_k - x_k_m1);
            x_k_p1 = x_k - f / f_;
            x_k_m1 = x_k;
            x_k = x_k_p1;
        }
        return x_k;
    }

    // f(x) = x^3 - 10x + 5
    private static double f(double x)
    {
        return Math.pow(x,3) - x*10 + 5;
    }

}