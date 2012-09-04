package ru.jdev.college.is.rsa;

/**
 * @author Alexey Zhidkov
 *  
 */

import java.math.*;

public class RSAKey {    
    /**
     *  ласс представл€ющий открытий или закрытый ключ
     */    
    private int exp;
    private int n;

    public RSAKey(int _exp, int _n) {
        exp = _exp;
        n = _n;
    }

    public boolean equals(Object parm1) {

        if (!(parm1 instanceof RSAKey)){
            return false;
        }
        
        return ((RSAKey) parm1).exp == exp &&
               ((RSAKey) parm1).n == n;
    }

    public String toString() {
        return "RSAKey (e=" + exp + ", n=" + n + ")";
    }

    public int getExp() {
        return exp;
    }

    public int getN() {
        return n;
    }
}

