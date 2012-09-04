package ru.nsu.g6210.jidkov.opt_meths;

/**
 * User: jdev
 * Date: 20.11.2007
 */
public class Ranec {

    private static final int N = 10;
    private static final int A = 635;

    private static int[] costs = {6, 7, 3, 8, 7, 9, 9, 4, 1, 5};
    private static int[] weigths = {828, 105, 690, 617, 29, 125, 859, 114, 124, 321};

    private static int[] x = new int[N];

    private static double min(int k, int alpha) {
        if (alpha / weigths[k] < 1)
            return alpha / weigths[k];
        else
            return 1;
    }

    private static double s(int k, int alpha) {
        if (k == 0) {
            return costs[k] * min(k, alpha);
        } else {
            double max = -1;
            for (int i = 0; i <= min(k, alpha); i++) {
                double cur = s(k - 1, alpha - weigths[k] * i) + costs[k] * i;
                if (cur > max)
                    max = cur;
            }
            return max;
        }
    }

    private static double directRun() {
        double maxValue = -1;
        for (int k = 0; k < N; k++) {
            for (int alpha = 1; alpha <= A; alpha++) {
                double cur = s(k, alpha);
                if (cur > maxValue)
                    maxValue = cur;
            }
        }
        return maxValue;

    }

    private static void inverseTrace() {
        int alpha = A;
        int k = N - 1;
        do {
            double max = -1;
            for (int xk = 0; xk <= min(k, alpha); xk++) {
                double cur = s(k - 1, alpha - weigths[k] * xk) + costs[k] * xk;
                if (cur > max) {
                    max = cur;
                    x[k] = xk;
                }
            }
            alpha = alpha - weigths[k] * x[k];
            k = k - 1;
        } while (k > 0);

        x[0] = alpha / weigths[0] < 1 ? alpha / weigths[0] : 1;
    }

    public static void main(String[] args) {
        double maxCost = directRun();
        System.out.println("Cost: " + maxCost);
        inverseTrace();
        int weigth = 0;
        for (int i = 0; i < N; i++) {
            weigth += x[i] * weigths[i];
        }

        System.out.println("Weigth: " + weigth);
        System.out.print("Load vector: ");
        for (int j = 0; j < N; j++) {
            System.out.print(x[j] + " ");
        }

    }

}
