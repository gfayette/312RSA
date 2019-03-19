package RSA;

import java.math.BigInteger;
import java.util.*;

public class ExtendedEuclideanAlgorithm {

    public BigInteger[] extendedEuclidean(BigInteger a, BigInteger n) {

        BigInteger multiplier;
        BigInteger[] vector1 =
                {n, BigInteger.ONE, BigInteger.ZERO};
        BigInteger[] vector2 =
                {a, BigInteger.ZERO, BigInteger.ONE};
        BigInteger[] vectorResult = new BigInteger[3];

        while (vector1[0].mod(vector2[0])
                .compareTo(BigInteger.ZERO) != 0) {
            multiplier = vector1[0].divide(vector2[0]);
            for (int i = 0; i < 3; ++i) {
                vectorResult[i] = vector1[i]
                        .subtract(multiplier.multiply(vector2[i]));
                vector1[i] = vector2[i];
                vector2[i] = vectorResult[i];
            }
        }

        while (vector2[2].compareTo(n) == 1) {
            vector2[2] = vector2[2].subtract(n);
            vector2[1] = vector2[1].add(a);
        }
        while (vector2[2].compareTo(new BigInteger("0")) == -1) {
            vector2[2] = vector2[2].add(n);
            vector2[1] = vector2[1].subtract(a);
        }

        return vector2;
    }

    private void displayMultiplicativeInverse(BigInteger a,
                                             BigInteger n) {
        try {
            BigInteger[] result = extendedEuclidean(a, n);

            if (result[0].compareTo(new BigInteger("1")) == 0) {
                System.out.println(
                        a + " has a multiplicative inverse " +
                                "modulo " + n);
                System.out.println(
                        "The multiplicative inverse is " + result[2]);
                System.out.println(
                        result[1] + " * " + n + " + " + result[2] +
                                " * " + a + " = " +
                                (n.multiply(result[1])
                                        .add(a.multiply(result[2]))));
            } else {
                System.out.println(
                        a + " does not have a multiplicative inverse " +
                                "modulo " + n);
                System.out.println(
                        "The greatest common fermats of " + n +
                                " and " + a + " is " + result[0]);
            }
        } catch (Exception e) {
            System.out.println("Invalid Input");
        }
        System.out.println();

    }

    private void displayMultiplicativeInverse() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the multiplier");
        BigInteger a = scanner.nextBigInteger();
        System.out.println("Enter the modulus");
        BigInteger n = scanner.nextBigInteger();
        System.out.println();

        displayMultiplicativeInverse(a, n);
    }

    private void displayInverseEquation(BigInteger a, BigInteger b,
                                       BigInteger modulus) {
        try {
            BigInteger[] result = extendedEuclidean(a, modulus);

            System.out.println("The function:");
            System.out.println(
                    "f(x) = ( " + a + "X + " + b + " ) mod " + modulus);

            if (result[0].compareTo(BigInteger.ONE) == 0) {
                System.out.println("Has an inverse function:");
                System.out.println("g(x) = ( " + result[2] + "X + " +
                        (result[2].multiply((modulus.subtract(b)))
                                .mod(modulus) + " ) mod " + modulus));
            } else {
                System.out.println("Does not have an inverse function");
            }
        } catch (Exception e) {
            System.out.println("Invalid Input");
        }
    }

    private void displayInverseEquation() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the modulus");
        BigInteger modulus = scanner.nextBigInteger();
        System.out.println("Enter the multiplier");
        BigInteger a = scanner.nextBigInteger();
        System.out.println("Enter the constant");
        BigInteger b = scanner.nextBigInteger();
        System.out.println();

        displayInverseEquation(a, b, modulus);
    }

    public static void main(String[] args) {
        ExtendedEuclideanAlgorithm e = new ExtendedEuclideanAlgorithm();
        e.displayMultiplicativeInverse();
        e.displayInverseEquation();
    }
}
