package ru.nsu.g6210.jidkov.calc_methods.lab2;

import ru.jdev.utils.math.matrixs.Matrix;

import java.util.Arrays;
import java.util.Comparator;
import java.math.BigDecimal;

/**
 * User: azhidkov
 * Date: 27.09.2007
 */
public class GeneticsAlgorithm {

    public static void main(String[] args) {
        GeneticsAlgorithm ga = new GeneticsAlgorithm();
        ga.find();
    }

    public double[] find() {
        double[][] pop = getPopulation();
        int i = 0;
        double x;
        double[] leader = pop[0];
        do {
            Arrays.sort(pop, new Comparator<Object>() {
                public int compare(Object o1, Object o2) {
                    double[] d1 = (double[]) o1;
                    double[] d2 = (double[]) o2;
                    double d1q = estimateInd(d1);
                    double d2q = estimateInd(d2);
                    if (Math.abs(d1q) < Math.abs(d2q))
                        return -1;
                    else
                        return 1;
                }
            });

            int j = 1;
            for (; j < pop.length / 2; j += 2) {
                if (Math.random() > 0.5) {
                    double tmp = pop[j][0];
                    pop[j][0] = pop[j + 1][0];
                    pop[j + 1][0] = tmp;

                    tmp = pop[j][1] * (Math.random() - 1) * 0.01;
                    pop[j][1] = pop[j + 1][1] * (Math.random() - 1) * 0.01;
                    pop[j + 1][1] = tmp;
                }
            }
            j--;
            for (; j < pop.length; j++) {
                pop[j] = getIndivid();
            }
            if (leader != pop[0]) {
                x = estimateInd(pop[0]);
                System.out.println(new BigDecimal(x).toPlainString());
                System.out.println(new BigDecimal(pop[0][0]).toPlainString() + ", " + new BigDecimal(pop[0][1]).toPlainString() + ", " + new BigDecimal(pop[0][2]).toPlainString());
                leader = pop[0];
            }
        } while (estimateInd(pop[0]) > 0.0001);
        x = estimateInd(pop[0]);
        System.out.println(new BigDecimal(x).toPlainString());
        System.out.println(new BigDecimal(pop[0][0]).toPlainString() + ", " + new BigDecimal(pop[0][1]).toPlainString() + ", " + new BigDecimal(pop[0][2]).toPlainString());
        return pop[0];
    }

    private double estimateInd(double[] ind) {
        return Math.abs(SimpleIterationMethod.x.multiply(Matrix.buildMatrix(new double[][]{ind})).add(SimpleIterationMethod.b.transponate()).getColSum(0) - 6);
    }

    public double[][] getPopulation() {
        double[][] res = new double[40][3];

        for (int i = 0; i < res.length; i++) {
            res[i] = getIndivid();
        }
        return res;
    }

    private double[] getIndivid() {
        double[] ind = new double[3];
        do {
            ind[0] = Math.random() - 1;
            ind[1] = Math.random() - 1;
            ind[2] = Math.random() - 1;
        } while (checkInd(ind));
        return ind;
    }

    private boolean checkInd(double[] ind) {
        return Math.abs(ind[0]) + Math.abs(ind[1]) + Math.abs(ind[2]) >= 1;
    }

}
