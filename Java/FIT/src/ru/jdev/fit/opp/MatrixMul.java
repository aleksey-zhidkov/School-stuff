package ru.jdev.fit.opp;

/**
 * @Author: jdev
 * Date: 17.05.2007
 */
public class MatrixMul
{

    private static final int MATRIX_SIZE = 4;

    private static byte[][] A = {{1, 0, 0, 0},
                                   {0, 1, 0, 0},
                                   {0, 0, 1, 0},
                                   {0, 0, 0, 1}};

    private static byte[][] B = {{3, 3, 3, 4},
                                   {3, 3, 3, 4},
                                   {3, 3, 3, 4},
                                   {3, 3, 3, 4}};

    private static byte[][] C = new byte[MATRIX_SIZE][MATRIX_SIZE];

    private static byte[][] a;
    private static byte[][] b;
    private static int id;
    private static int pCount;

    public static void main(String[] args)
    {
        MPIClient c = new MPIClient();
        c.init();

        id = c.getId();
        pCount = c.getPCount();

        if (id == 0)
        {
            a = getMtrRow(A, MATRIX_SIZE, 0);
            b = getMtrCol(B, MATRIX_SIZE, 0);
            for (int i = 1; i < pCount; i++)
            {
                c.send(getByteArray(getMtrRow(A, MATRIX_SIZE, i), getMtrCol(B, MATRIX_SIZE, i), (byte) i));
            }

            for (int i = 0; i < pCount; i++)
            {
                byte[][] cm = mtrMul(a, b);
                fillMatrix(cm,i,0);
                byte[] r = c.recive();
                processInput(r);
            }

            for (int i = 0; i < pCount - 1; i++)
            {
                byte[] r = c.recive();
            }
        }
        else
        {

        }

        c.close();
    }

    private static void processInput(byte[] r)
    {
        int k = 0;
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[0].length; j++)
            {
                a[i][j] = r[k++];
            }
        }
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[0].length; j++)
            {
                b[i][j] = r[k++];
            }
        }
    }

    private static byte[] getByteArray(byte[][] a, byte[][] b, byte id)
    {
        byte[] res = new byte[a.length*a[0].length+ b.length* b[0].length + 1];
        res[0] = id;
        int k = 1;
        for (byte[] anA : a)
        {
            for (int j = 0; j < a[0].length; j++)
            {
                res[k++] = anA[j];
            }
        }

        for (byte[] aB : b)
        {
            for (int j = 0; j < b[0].length; j++)
            {
                res[k++] = aB[j];
            }
        }
        return res;
    }

    public static byte[][] mtrMul(byte[][] a, byte[][] b)
    {
        byte[][] res = new byte[MATRIX_SIZE][MATRIX_SIZE];

        for (int i = 0; i < MATRIX_SIZE; i++)
        {
            for (int j = 0; j < MATRIX_SIZE; j++)
            {
                byte sum = 0;

                for (int k = 0; k < MATRIX_SIZE; k++)
                {
                    sum+= a[i][k] * b[k][j];
                }

                res[i][j] = sum;
            }
        }

        return res;
    }

    public static byte[][] getMtrRow(byte[][] a, int rowLength, int rowIdx)
    {
        byte[][] res = new byte[rowLength][a[0].length];
        for (int i = 0; i < rowLength; i++)
        {
            for (int j = 0; j < a[0].length; j++)
            {
                res[i][j] = a[(rowIdx*rowLength) + i][j];
            }
        }
        return res;
    }

    public static byte[][] getMtrCol(byte[][] a, int colLength, int colIdx)
    {
        byte[][] res = new byte[a.length][colLength];
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < colLength; j++)
            {
                res[i][j] = a[i][(colIdx*colLength) + j];
            }
        }
        return res;
    }

    private static void fillMatrix(byte[][] a, int iStart, int jStart)
    {
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[0].length; j++)
            {
                C[iStart + i][jStart + j] = a[i][j];
            }
        }
    }

}
