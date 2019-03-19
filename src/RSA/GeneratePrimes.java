package RSA;

import java.math.BigInteger;

public class GeneratePrimes {
    public BigInteger[] generatePrimes(BigInteger min, int numPrimes) {
        if (min.compareTo(BigInteger.ONE) == -1 || numPrimes < 0) {
            throw new IllegalArgumentException();
        }

        BigInteger n = min;
        BigInteger[] primes = new BigInteger[numPrimes];
        int primesFound = 0;

        while (n.compareTo(new BigInteger("4")) ==
                -1 && primesFound < numPrimes) {
            primes[primesFound] = n;
            ++primesFound;
            n = n.add(BigInteger.ONE);
        }

        if (n.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) {
            n = n.add(BigInteger.ONE);
        }

        while (primesFound < numPrimes) {
            if (simpleTest(n)) {
                primes[primesFound] = n;
                ++primesFound;
            }
            n = n.add(BigInteger.TWO);
        }
        return primes;
    }

    public BigInteger generatePrime(BigInteger min) {
        return generatePrimes(min, 1)[0];
    }

    private boolean simpleTest(BigInteger n) {

        if (n.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0 ||
                n.mod(new BigInteger("3")).compareTo(BigInteger.ZERO) ==
                        0) {
            return false;
        }

        BigInteger i = new BigInteger("5");
        while (i.multiply(i).subtract(BigInteger.ONE).compareTo(n) ==
                -1) {
            if (n.mod(i).compareTo(BigInteger.ZERO) == 0 ||
                    n.mod(i.add(BigInteger.TWO))
                            .compareTo(BigInteger.ZERO) == 0) {
                return false;
            }
            i = i.add(new BigInteger("6"));
        }
        return true;
    }


    public static void main(String[] args) {
        GeneratePrimes p = new GeneratePrimes();
        BigInteger[] result = p.generatePrimes(new BigInteger(
                "100000000"), 10);
        for (BigInteger prime : result) {
            System.out.println(prime);
        }
    }

}
