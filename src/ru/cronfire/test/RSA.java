package ru.cronfire.test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

public class RSA {

    private static final BigInteger one = BigInteger.ONE;
    private static final Random r = new SecureRandom();
    private static BigInteger N;
    private static BigInteger e;
    private static BigInteger d;

    public static void main(String[] args) throws IOException {
        int bitlength = 1024; // размер в битах
        BigInteger p = BigInteger.probablePrime(bitlength, r);
        BigInteger q = BigInteger.probablePrime(bitlength, r);
        BigInteger phi = p.subtract(one).multiply(q.subtract(one)); // phi = (p - 1) * (q - 1)

        N = p.multiply(q); // p * q
        e = new BigInteger("65537"); // Обычно в качестве e берут простые числа, содержащие небольшое количество
        // единичных бит в двоичной записи, например, простые числа Ферма 17, 257 или 65537. 1 < e < phi
        d = e.modInverse(phi); // остаток от деления числа, обратного e на аргумент phi;

        String teststring;
        System.out.println("Enter the plain text:");
        teststring = new Scanner(System.in).nextLine();
        System.out.println("Encrypting String: " + teststring);
        System.out.println("String in Bytes: " + bytesToString(teststring.getBytes()));
        // encrypt
        byte[] encrypted = encrypt(teststring.getBytes());
        System.out.println("Encrypted String in Bytes: " + bytesToString(encrypted));
        // decrypt
        byte[] decrypted = decrypt(encrypted);
        System.out.println("Decrypted String in Bytes: " + bytesToString(decrypted));
        System.out.println("Decrypted String: " + new String(decrypted));
    }

    private static String bytesToString(byte[] encrypted) {
        String result = "";
        for (byte b : encrypted) {
            result += Byte.toString(b);
        }
        return result;
    }

    public static byte[] encrypt(byte[] message) {
        return (new BigInteger(message)).modPow(e, N).toByteArray(); // остаток от деления message,
        // возведенного в степень e, на N;
    }

    public static byte[] decrypt(byte[] message) {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }

}