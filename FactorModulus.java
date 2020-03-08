package PGP;

import java.math.BigInteger;

/**
 * PGP Project
 * FactorModulus Class
 *
 * This class uses Fermat's factoring algorithm to factor numbers which are a product of two primes. This can be used
 * to crack weak RSA encryption.
 *
 * @author George Fayette
 **/

public class FactorModulus {

    public BigInteger[] fermats(BigInteger n) {
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ZERO;
        BigInteger bRemainder;
        BigInteger bSquared;

        try {
            if (n.mod(BigInteger.TWO).compareTo(BigInteger.ONE) != 0) {
                throw new IllegalArgumentException();
            }

            a = n.sqrt();

            do {
                a = a.add(BigInteger.ONE);
                bSquared = a.multiply(a).subtract(n);
                bRemainder = bSquared.sqrtAndRemainder()[1];
                b = bSquared.sqrtAndRemainder()[0];
            } while (bRemainder.compareTo(BigInteger.ZERO) != 0);

        } catch (Exception e) {
            System.out.println("Error");
        }

        return new BigInteger[]{a.subtract(b), a.add(b)};
    }
}

