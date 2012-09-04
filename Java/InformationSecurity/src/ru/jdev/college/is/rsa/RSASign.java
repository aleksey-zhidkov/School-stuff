package ru.jdev.college.is.rsa;

/**
 * @author Alexey Zhidkov
 *
 */

public class RSASign {

    /**
     * ƒанный метод служит дл€ шифровани€/дешифровани€ некоторого сообщени€
     *
     * @param exp Ч показатель степени дл€
     *            ключа RSA используемого дл€ данного
     *            преобразовани€
     * @param n   Ч показатель модул€ дл€ вычислений
     * @param mes Ч сообщение которое должно быть зашифрованно/дешифрованно
     * @return Ч результат преобразовани€
     */
    private static int _sign(int exp, int n, int mes) {
        return modPow(mes, exp, n);
    }

    /**
     * ¬ данном методе просто происходит вызов
     * метода _sign и выполн€ющего всю черновую работу
     */
    public static int decrypt(RSAKey privateKey, int mes) {
        return _sign(privateKey.getExp(), privateKey.getN(), mes);
    }

    /**
     * ƒанный метод практически идентичен ранее приведенному
     * отличаетс€ только используемым показателем степени
     */
    public static int crypt(RSAKey publicKey, int mes) {
        return _sign(publicKey.getExp(), publicKey.getN(), mes);
    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage:");
            System.out.println("       java ru.jdev.college.is.rsa.RSASign \"message\"");
            return;
        }

        String message = args[0];
        System.out.println("Message: " + message + "\n");

        RSAKeyGenerator kg = new RSAKeyGenerator();

        RSAKey privKey = kg.getPrivateKey();
        RSAKey publKey = kg.getPublicKey();

        System.out.println(privKey);
        System.out.println(publKey + "\n");

        byte[] bytes = message.getBytes();
        int[] crypted = new int[bytes.length];
        System.out.println("Crypted: ");
        for (int i = 0; i < bytes.length; i++) {
            crypted[i] = crypt(publKey, bytes[i]);
            System.out.println(crypted[i]);
        }

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) decrypt(privKey, crypted[i]);
        }
        System.out.println("\nDecrypted: " + new String(bytes));
    }

    public static int modPow(int x, int y, int n) {
        int tmp;
        if (y == 1)
            return (x % n);
        if ((y % 2 == 0) && (y != 0)) {
            tmp = modPow(x, y / 2, n);
            return ((tmp * tmp) % n);
        } else {
            tmp = modPow(x, (y - 1) / 2, n);
            tmp = (tmp * tmp) % n;
            tmp = (tmp * x) % n;
            return (tmp);
        }
    }
}

