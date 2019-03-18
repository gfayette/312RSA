package RSA;

import java.math.BigInteger;
import java.util.ArrayList;

public class ExponentMod {
    public BigInteger exponentMod(BigInteger base, BigInteger exponent,
                            BigInteger modulus) {
        ArrayList<BigInteger> powersOfTwo = new ArrayList<>();
        ArrayList<BigInteger> powersOfTwoModTerms = new ArrayList<>();

        BigInteger currentExponent = BigInteger.ONE;
        BigInteger previousMod = base.mod(modulus);

        powersOfTwo.add(currentExponent);
        powersOfTwoModTerms.add(previousMod);

        while (currentExponent.multiply(BigInteger.TWO)
                .compareTo(exponent.add(BigInteger.ONE)) == -1) {
            currentExponent = currentExponent.multiply(BigInteger.TWO);
            previousMod =
                    previousMod.multiply(previousMod).mod(modulus);

            powersOfTwo.add(currentExponent);
            powersOfTwoModTerms.add(previousMod);
        }

        BigInteger e = exponent;
        BigInteger finalTerm = BigInteger.ONE;

        while (e.compareTo(BigInteger.ZERO) == 1) {
            for (int i = powersOfTwo.size() - 1; i >= 0; --i) {
                if (powersOfTwo.get(i)
                        .compareTo(e.add(BigInteger.ONE)) == -1) {
                    finalTerm = finalTerm.multiply(
                            powersOfTwoModTerms.get(i).mod(modulus));
                    e = e.subtract(powersOfTwo.get(i));
                }
            }
        }

        return finalTerm.mod(modulus);

    }

    public BigInteger exponentMod2(BigInteger base, BigInteger exponent,
                                   BigInteger modulus) {

        BigInteger result = BigInteger.ONE;
        while (exponent.compareTo(BigInteger.ZERO) == 1) {
            if ((exponent.mod(BigInteger.TWO)
                    .compareTo(BigInteger.ONE) == 0)) {
                result = (result.multiply(base)).mod(modulus);
            }
            base = (base.multiply(base)).mod(modulus);
            exponent = exponent.divide(BigInteger.TWO);
        }
        return result;

    }

    public static void main(String[] args) {
        ExponentMod e = new ExponentMod();


        BigInteger b = new BigInteger("123456");
        BigInteger ex = new BigInteger("234567");
        BigInteger m = new BigInteger("345678");
        System.out.println(e.exponentMod2(b, ex, m));
        System.out.println(e.exponentMod(b, ex, m));
    }
}
