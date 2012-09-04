package ru.nsu.g6210.jidkov.calc_methods.lab2;

import ru.jdev.utils.math.matrixs.MatrixUtils;
import ru.jdev.utils.math.matrixs.Matrix;


/**
 *
 * Класс нахождения решения СЛАУ методом квадратных корней
 *
 * A = {3.1, 1.5, 1.0}
 *     {1.5, 2.5, 0.5}
 *     {1.0, 0.5, 4.2}
 *
 * b = {10.83}
 *     { 9.2 }
 *     {17.1 }
 *
 * x = {1.3}
 *     {2.2}
 *     {3.5}
 *
 * User: jdev
 * Date: 26.09.2007
 *
 */
public class SquareRootsMethod {

    private Matrix<Double> A = new Matrix<Double>(new Double[][]{{3.1, 1.5, 1.0},
                                                                 {1.5, 2.5, 0.5},
                                                                 {1.0, 0.5, 4.2}});

    private Matrix<Double> b = new Matrix<Double>(new Double[][]{{10.83,
                                                                   9.2,
                                                                  17.1}});

    // матрица T
    private Matrix<Double> T = new Matrix<Double>(new Double[3][3]);
    // матрица T' (транспонированная)
    private Matrix<Double> T_ = new Matrix<Double>(new Double[3][3]);

    public static void main(String[] args)
    {
        SquareRootsMethod srm = new SquareRootsMethod();
        srm.findTMatrixs();
        Matrix x = srm.findX(srm.findY());

        MatrixUtils.printMatrix(srm.A.multiply(x));
        MatrixUtils.printMatrix(x);
    }

    /**
     * Метод нахождения матриц T и T' (T' * T = A)
     */
    private void findTMatrixs()
    {
        // вычисление элементов t11 .. t13 матрицы T
        double t11 = Math.sqrt(A.get(0, 0));
        double t12 = A.get(0,1) / t11;
        double t13 = A.get(0,2) / t11;

        // вычисление элементов t22, t23 матрицы T
        double t22 = Math.sqrt(A.get(1,1) - Math.pow(t12,2));
        double t23 = (A.get(2,1) - t12*t13) / t22;

        // вычисление элемента t33 матрицы T
        double t33 = Math.sqrt(A.get(2,2) - t13*t13 - t23*t23);

        // задание элементов матрцы T
        T.set(0, 0, t11);
        T.set(0, 1, t12);
        T.set(0, 2, t13);

        T.set(1, 1, t22);
        T.set(1, 2, t23);

        T.set(2, 2, t33);

        // задание элементов матрцы T'
        T_.set(0, 0, t11);
        T_.set(1, 0, t12);
        T_.set(2, 0, t13);

        T_.set(1, 1, t22);
        T_.set(2, 1, t23);

        T_.set(2, 2, t33);
    }

    /**
     * Метод нахождения вектора y (T'*y = b)
     */
    private Matrix<Double> findY()
    {
        double y1 = b.get(0,0) / T_.get(0, 0);
        double y2 = (b.get(0,1) - y1*T_.get(1,0))/ T_.get(1, 1);
        double y3 = (b.get(0,2) - y1*T_.get(2, 0) - y2*T_.get(2, 1)) / T_.get(2, 2);
        return new Matrix<Double>(new Double[][]{{y1, y2, y3}});
    }

    /**
     * Метод нахождения вектора x (T*x = y)
     */
    private Matrix<Double> findX(Matrix<Double> y)
    {
        double x3 = y.get(0, 2) / T.get(2, 2);
        double x2 = (y.get(0, 1) - x3*T.get(1, 2))/ T.get(1, 1);
        double x1 = (y.get(0, 0) - x2*T.get(0, 1) - x3*T.get(0, 2)) / T.get(0, 0);
        return new Matrix<Double>(new Double[][]{{x1, x2, x3}});
    }

}
