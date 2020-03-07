package PGP;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * MTH312PGP
 * ExponentMod Class
 *
 * This class is used to efficiently preform modulus operations on exponential expressions.
 *
 * @author George Fayette
 **/

public class ExponentMod {
    public BigInteger exponentMod(BigInteger base, BigInteger exponent, BigInteger modulus) {
        ArrayList<BigInteger> powersOfTwo = new ArrayList<>();
        ArrayList<BigInteger> powersOfTwoModTerms = new ArrayList<>();

        BigInteger currentExponent = BigInteger.ONE;
        BigInteger previousMod = base.mod(modulus);

        powersOfTwo.add(currentExponent);
        powersOfTwoModTerms.add(previousMod);

        while (currentExponent.multiply(BigInteger.TWO).compareTo(exponent.add(BigInteger.ONE)) == -1) {
            currentExponent = currentExponent.multiply(BigInteger.TWO);
            previousMod = previousMod.multiply(previousMod).mod(modulus);

            powersOfTwo.add(currentExponent);
            powersOfTwoModTerms.add(previousMod);
        }

        BigInteger e = exponent;
        BigInteger finalTerm = BigInteger.ONE;

        while (e.compareTo(BigInteger.ZERO) == 1) {
            for (int i = powersOfTwo.size() - 1; i >= 0; --i) {
                if (powersOfTwo.get(i).compareTo(e.add(BigInteger.ONE)) == -1) {
                    finalTerm = finalTerm.multiply(powersOfTwoModTerms.get(i).mod(modulus));
                    e = e.subtract(powersOfTwo.get(i));
                }
            }
        }

        return finalTerm.mod(modulus);

    }

    public BigInteger exponentMod2(BigInteger base, BigInteger exponent, BigInteger modulus) {

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
}
