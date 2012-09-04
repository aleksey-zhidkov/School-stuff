package ru.jdev.college.is;

import java.math.BigInteger;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 28.02.2006
 * Time: 21:39:41
 */
public class ElGamal {

    private static final int M = 200;
    private static final int C = 13;
    private static final int G = 7;

    public static void main(String[] args) {
        ElGamal eg = new ElGamal();
        eg.run();
    }

    public void run() {
        System.out.println("M = "+M);
        System.out.println("C = "+C);
        int p = getP(); // simple
        System.out.println("p = "+p);
        System.out.println("G = "+G);
        int k = (int) (Math.random() * (p-3) + 1); // 1 <= k <= p-2
        System.out.println("k = "+k);
        int d = (int) (Math.pow(G,C) % p); // d = G^C mod p
        System.out.println("d = "+d);
        BigInteger r = new BigInteger(G+"").pow(k).mod(new BigInteger(p+"")); // r =G^k mod p
        System.out.println("r = "+r);
        BigInteger e = new BigInteger(M+"").multiply(new BigInteger(d+"").pow(k)).mod(new BigInteger(p+"")); // e = M * d^k mod p
        System.out.println("e = "+e);
        long ms = decrypt(r,e,p);
        System.out.println("ms = "+ms);
    }

    private int getP() {
        int p;
        do {
            p = (int) (M + Math.random() * 100);
        } while (!isSimple(p));
        return p;
    }

    private boolean isSimple(int p) {
        for(int i=2; i < p / 2; i++) {
            if (p % i == 0)
                return false;
        }
        return true;
    }

    private int decrypt(BigInteger b1, BigInteger b2, int p) {
        BigInteger res = b2.multiply(b1.pow(p-1-C)).mod(new BigInteger(p+"")); // m' = e * r^(p-1p-c) mod p
        return res.intValue();
    }
}
