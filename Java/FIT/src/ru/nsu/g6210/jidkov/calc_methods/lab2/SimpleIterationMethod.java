package ru.nsu.g6210.jidkov.calc_methods.lab2;

import ru.jdev.utils.math.matrixs.MatrixUtils;
import ru.jdev.utils.math.matrixs.Matrix;

/**
 * User: jdev
 * Date: 26.09.2007
 */
public class SimpleIterationMethod {

    private Matrix<Double> A = new Matrix<Double>(new Double[][]{{3.1, 1.5, 1.0},
                                                                 {1.5, 2.5, 0.5},
                                                                 {1.0, 0.5, 4.2}});

    public static Matrix<Double> b = new Matrix<Double>(new Double[][]{{10.83, 9.20, 17.10}}).multiply(0.01);

    public static Matrix<Double> x = new Matrix<Double>(b).multiply(2.0);

    private Matrix<Double> C = new Matrix<Double>(new Double[][]{{-0.21, -0.015, -0.01},
                                                                 {-0.015, -0.015, -0.005},
                                                                 {-0.01, -0.005, -0.032}});

    /*private Matrix<Double> A = new Matrix<Double>(new Double[][]{{1.02, 0.1},
                                                                 {-0.1, 0.98}});

    public static Matrix<Double> b = new Matrix<Double>(new Double[][]{{1.01, -1.08}});

    public static Matrix<Double> x = new Matrix<Double>(b);

    private Matrix<Double> C = new Matrix<Double>(new Double[][]{{-0.02, -0.1},
                                                                 {0.1, 0.02}}); */

    public static void main(String[] args) {
        SimpleIterationMethod sim = new SimpleIterationMethod();
        //System.out.println("E b= "+sim.b.getRowSum(0));
        sim.findX();

        MatrixUtils.printMatrix(sim.A.multiply(sim.x));
    }

    /**
     * Метод нахождения вектора x
     * x (k+1)-ое = C * x k-ое + b
     */
    private void findX() {
        Matrix<Double> matrix = C.multiply(x).transponate();
        matrix.add(b);
        MatrixUtils.printMatrix(matrix);
        int i = 0;
        while (true) {
            //System.out.print("E x" + i + "= " + x.getRowSum(0)+ "; x:");
            //MatrixUtils.printMatrix(x);
            Matrix<Double> xk1 = C.multiply(x);
            xk1.add(b.transponate());
            if (checkQuality(x, xk1.transponate()))
                break;
            x = xk1.transponate();
        }
        //System.out.println("E x" + i + "= " + x.getRowSum(0));
        //MatrixUtils.printMatrix(x);
    }

    private boolean checkQuality(Matrix<Double> x, Matrix<Double> xk1) {
        double alpha = 0.12 / (1 - 0.12);
        double max = 0.0;
        for (int i = 0; i < x.getColCount(); i++)
        {
            if (Math.abs(x.get(0,i) - xk1.get(0,i))*alpha > max)
                max = Math.abs(x.get(0,i) - xk1.get(0,i))*alpha;
        }
        return max < 0.0001;
    }

}
