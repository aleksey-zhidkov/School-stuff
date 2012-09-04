package ru.jdev.college.is.rsa;

/**
 * @author Alexey Zhidkov
 *  
 */

public class RSAKeyGenerator {

    /**
     * �����-��������� �������� � �������� ������
     */

    private int n;
    private int e;
    private int d;

    /**
     * ������������� ���������� � ��������� ������
     */
    public RSAKeyGenerator() {
        // ��������� ���������� p [2,1000]
        int p = getSimple(2, 1000);
        // ��������� ���������� q [2,1000]
        int q = getSimple(2, 1000);
        /*
         * ��������������, ��� ���� n = p*q, ��� p � q �������� �������� �������� �������, �� 
         * ����������� ���� ��� ��������� ��������� M ����� ��������� n, �.�. �� ����� ������� 
         * ������� � ������ ������, ����� ���������� ���� � �� ����� ���������� 
         */
        int n = p*q;
        /*
         * ��� �������� n �������� ������� ������ ����� n-1 
         * ���������� �������� ������� ������:
         * fi(n) = 
         * fi(p*q) = 
         * fi(p)*fi(q) = 
         * (p-1)*(q-1)
         */
        int fi = (p-1)*(q-1);

        /*
         * ����� ���������� e, ������, ��� ��� e � fi(n) = 1
         */

        int e;
        do {
            e = (int) (Math.random() * fi + 2);
        }while (vz_simple(fi,e)!=1);

        int d = 2;
        while((d * e) % fi !=1 || (e == d)) {
            d++;
        }

        this.n = n;
        this.e = e;
        this.d = d;
    }

    public RSAKey getPrivateKey() {
        return new RSAKey(d, n);
    }

    public RSAKey getPublicKey() {
        return new RSAKey(e, n);
    }

    private int getSimple(int from, int to) {
        while (true) {
            int n = (int) (Math.random() * (to - from) + from);
            int i = 2;
            for (; i < n / 2; i++) {
                if (n % i == 0)
                    break;
            }
            if (i == n / 2)
                return n;
        }
    }

    private int vz_simple(int r, int d) {
        int g = d;
        while (r > 0) {
            g = r;
            r = d % r;
            d = g;
        }
        if (g == 1) return 1;
        else return 0;
    }
}

