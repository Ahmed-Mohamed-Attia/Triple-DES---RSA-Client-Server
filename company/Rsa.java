package com.company;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class Rsa {
    static BigInteger ONE = new BigInteger("1");
    static BigInteger ZERO = new BigInteger("0");
    public static BigInteger encrypt(BigInteger p, BigInteger q, String msg) {
        BigInteger n = getN(p, q);
        BigInteger phi = getPhiN(p, q);
        BigInteger e = chooseE(p, q);
//        BigInteger e = new BigInteger("229");
        BigInteger e_big = new BigInteger(String.valueOf(e));
        BigInteger msg_big = new BigInteger(String.valueOf(msg));
        BigInteger t = msg_big.modPow(e_big, n);
//        System.out.println("e = " + e_big);
//        System.out.println("n = " + n);
//        System.out.println("msg = " + msg_big);
//        System.out.println("CypherBig = " + t);
        int c = t.intValue();
        return t;
    }
    public static BigInteger decrypt(BigInteger p, BigInteger q, BigInteger e, BigInteger cipher) {
        BigInteger n = getN(p, q);
        BigInteger phi = getPhiN(p, q);
//        System.out.println("phi = (p-1)*(q-1) = " + phi);
        BigInteger d = modInverse(e, phi); //Math.pow(e, -1) % phi;
//        System.out.println("d = Math.pow(e, -1) % phi = " + d);
        BigInteger d_big = new BigInteger(String.valueOf(d));
        BigInteger cipher_big = cipher;
//        System.out.println("Big Cipher " + cipher_big.toString());
        BigInteger n_big = new BigInteger(String.valueOf(n));
        BigInteger t = cipher_big.modPow(d_big, n_big);
        double m = t.doubleValue();

//        System.out.println("m = Math.pow(cipher, d) % n = " + t);
        return t;
    }
  //  static long gcd(BigInteger e, BigInteger z) {
  //      if (e.equals(0))
  //          return z.longValue();
  //      return gcd(z.mod(e), e);
  //  }
    public static BigInteger chooseE(BigInteger p, BigInteger q) {
        BigInteger n = getN(p, q);
        BigInteger phi = getPhiN(p, q);
        BigInteger e = new BigInteger("2");
        while (e.gcd(phi).longValue() != 1 || e.longValue() <= 1 || e.longValue() >= phi.longValue()) {
            e= e.add(ONE);
 //           System.out.println("ChooseE- E = " + e);
        }
        return e;
    }
    public static BigInteger getN(BigInteger q, BigInteger p)
    {
        return p.multiply(q);
    }
    public static BigInteger getPhiN(BigInteger p, BigInteger q)
    {
        return (p.subtract(new BigInteger("1"))).multiply(q.subtract(new BigInteger("1")));
    }
    public static BigInteger modInverse(BigInteger m, BigInteger n) {
        BigInteger result = BigInteger.ZERO;
        int mod = -1;
        BigInteger temp_m = m.max(n);
        BigInteger temp_n = m.min(n);;
        List<Pair<BigInteger ,BigInteger>> pairList= new ArrayList<Pair<BigInteger ,BigInteger>>();
        pairList.add(new Pair<BigInteger ,BigInteger>(temp_m, ZERO));
        pairList.add(new Pair<BigInteger ,BigInteger>(temp_n, ONE));
        int currentStep = pairList.size() - 1;
        // get the left element of last pair in the array
        while(pairList.get(currentStep).getLeft().compareTo(ONE) > 0)
        {
            BigInteger r = pairList.get(currentStep -1 ).getLeft().mod(pairList.get(currentStep).getLeft());
            BigInteger  qi = pairList.get(currentStep -1 ).getLeft().divide(pairList.get(currentStep).getLeft());
            BigInteger x = pairList.get(currentStep -1 ).getRight().subtract(pairList.get(currentStep).getRight().multiply(qi));
            result = x;
            pairList.add(new Pair<BigInteger ,BigInteger>(r, x));
            currentStep++;
        }
        if (result.compareTo(ZERO) < 0)
        {
            result = result.add(temp_m);
        }
//        System.out.println("DECRYPT_RESULT = " + result);
        return result;
    }


}
