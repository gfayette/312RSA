package RSA;

import java.math.BigInteger;
import java.util.ArrayList;

public class ExponentMod {
    public long exponentMod(long base, long exponent, long modulus) {
        ArrayList<Long> powersOfTwo = new ArrayList<>();
        ArrayList<Long> powersOfTwoModTerms = new ArrayList<>();

        long currentExponent = 1;
        long previousMod = base % modulus;

        powersOfTwo.add(currentExponent);
        powersOfTwoModTerms.add(previousMod);

        while (exponent >= currentExponent * 2) {
            currentExponent *= 2;
            previousMod = previousMod * previousMod % modulus;

            powersOfTwo.add(currentExponent);
            powersOfTwoModTerms.add(previousMod);
        }

        long[] binary = new long[powersOfTwo.size()];
        long e = exponent;
        long finalTerm = 1;

        while (e > 0) {
            for (int i = powersOfTwo.size() - 1; i >= 0; --i) {
                if (powersOfTwo.get(i) <= e) {
                    binary[i] = 1;
                    finalTerm = finalTerm * powersOfTwoModTerms.get(i) %
                            modulus;
                    e -= powersOfTwo.get(i);
                } else {
                    binary[i] = 0;
                }
            }
        }

        long result = finalTerm % modulus;
        return result;

    }

    public BigInteger exponentMod2(BigInteger base, BigInteger exponent,
                             BigInteger modulus) {

        BigInteger result = BigInteger.ONE;
        while (exponent.compareTo(BigInteger.ZERO) == 1) {
            if ((exponent.mod(BigInteger.TWO).compareTo(BigInteger.ONE) == 0)) {
                result = (result.multiply(base)).mod(modulus);
            }
            base = (base.multiply(base)).mod(modulus);
            exponent = exponent.divide(BigInteger.TWO);
        }
        return result;

    }

    public static void main(String args[]) {
        ExponentMod e = new ExponentMod();

        System.out.println(e.exponentMod(123456, 234567, 345678));
        BigInteger b = new BigInteger("123456");
        BigInteger ex = new BigInteger("234567");
        BigInteger m = new BigInteger("345678");
        System.out.println(e.exponentMod2(b, ex, m));
    }
}
