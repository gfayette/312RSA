package RSA;

import java.math.BigInteger;
import java.util.*;

public class RSA {
    BigInteger publicKey;
    BigInteger privateKey;
    BigInteger modulus;
    private GeneratePrimes generatePrimes;
    private GenerateKeys generateKeys;
    private FactorModulus factorModulus;
    private ExponentMod exponentMod;

    public RSA() {
        generatePrimes = new GeneratePrimes();
        generateKeys = new GenerateKeys();
        factorModulus = new FactorModulus();
        exponentMod = new ExponentMod();

    }

    public RSA(BigInteger pubKey, BigInteger priKey, BigInteger mod) {
        generatePrimes = new GeneratePrimes();
        generateKeys = new GenerateKeys();
        factorModulus = new FactorModulus();
        exponentMod = new ExponentMod();

        publicKey = pubKey;
        privateKey = priKey;
        modulus = mod;
    }

    public BigInteger[] keysFromPrimes(BigInteger p, BigInteger q,
                                       BigInteger minPubKey) {
        System.out.println(
                "Generating keys with minimum public key size " +
                        minPubKey + "...");

        BigInteger[] keys =
                generateKeys.generateKeyPair(p, q, minPubKey);
        publicKey = keys[0];
        privateKey = keys[1];
        modulus = keys[2];

        System.out.println("Public key: " + keys[0]);
        System.out.println("Private key: " + keys[1]);
        System.out.println("Modulus: " + keys[2]);
        System.out.println();

        return keys;
    }

    public BigInteger[] keysFromMinVal(BigInteger minPrime,
                                       BigInteger minPubKey) {
        System.out
                .println("Generating primes greater than " + minPrime + "...");

        BigInteger p = generatePrimes.generatePrime(minPrime);
        Random r = new Random();
        BigInteger qMin = p.multiply(BigInteger
                .valueOf((long) (100 + 900 * r.nextDouble())));
        BigInteger q = generatePrimes.generatePrime(qMin);

        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println();

        return keysFromPrimes(p, q, minPubKey);
    }

    public BigInteger encrypt(BigInteger message) {
        System.out.println("Encrypting...");
        BigInteger N =
                exponentMod.exponentMod(message, publicKey, modulus);
        System.out.println(message);
        System.out.println("Encrypts to");
        System.out.println(N);
        System.out.println();
        return N;
    }

    public BigInteger encrypt(BigInteger message, BigInteger pubKey,
                              BigInteger mod) {
        System.out.println("Encrypting...");
        BigInteger N = exponentMod.exponentMod(message, pubKey, mod);
        System.out.println(message);
        System.out.println("Encrypts to");
        System.out.println(N);
        System.out.println();
        return N;
    }

    public BigInteger decrypt(BigInteger cipherText) {
        System.out.println("Decrypting...");
        BigInteger M = exponentMod
                .exponentMod(cipherText, privateKey, modulus);
        System.out.println(cipherText);
        System.out.println("Decrypts to");
        System.out.println(M);
        System.out.println();
        return M;
    }

    public BigInteger decrypt(BigInteger cipherText,
                              BigInteger privateKey,
                              BigInteger modulus) {
        System.out.println("Decrypting...");
        BigInteger M = exponentMod
                .exponentMod(cipherText, privateKey, modulus);
        System.out.println(cipherText);
        System.out.println("Decrypts to");
        System.out.println(M);
        System.out.println();
        return M;
    }

    public BigInteger signMessage(BigInteger message) {
        return exponentMod.exponentMod(message, privateKey, modulus);
    }

    public BigInteger signMessage(BigInteger message,
                                  BigInteger privateKey,
                                  BigInteger modulus) {
        return exponentMod.exponentMod(message, privateKey, modulus);
    }

    public BigInteger verifySignature(BigInteger cipherText) {
        return exponentMod.exponentMod(cipherText, publicKey, modulus);
    }

    public BigInteger verifySignature(BigInteger cipherText,
                                      BigInteger publicKey,
                                      BigInteger modulus) {
        return exponentMod.exponentMod(cipherText, publicKey, modulus);
    }

    public BigInteger[] getPrivateKey(BigInteger pubKey,
                                      BigInteger mod) {
        System.out.println("Cracking modulus...");
        long start = System.currentTimeMillis();
        BigInteger[] primes = factorModulus.fermats(mod);
        BigInteger p = primes[0];
        BigInteger q = primes[1];
        BigInteger privateKey =
                generateKeys.generatePrivateKey(p, q, pubKey);
        double elapsedSeconds =
                (System.currentTimeMillis() - start)/1000.0;


        System.out.println("The modulus " + mod + " has factors:");
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("The private key corresponding to the " +
                "public key " + pubKey + " is:");
        System.out.println(privateKey);
        System.out.println("It took " + elapsedSeconds + " seconds to" +
                " find " +
                "the private key");

        BigInteger[] result = {pubKey, privateKey, mod, p, q};
        return result;
    }

    public BigInteger[] crackModulus(BigInteger mod) {
        System.out.println("Cracking modulus...");
        BigInteger[] primes = factorModulus.fermats(mod);
        BigInteger p = primes[0];
        BigInteger q = primes[1];

        System.out.println("The modulus " + mod + " has factors:");
        System.out.println("p = " + primes[0]);
        System.out.println("q = " + primes[1]);

        return primes;
    }


    public static void main(String[] args) {
        // Generate keys given minimum prime number value and minimum
        // public key value. Encrypt and Decrypt message using keys.
        BigInteger minPrimeSize = BigInteger.valueOf(1000000);
        BigInteger minPubKeySize = BigInteger.valueOf(50000);
        RSA r = new RSA();

        r.keysFromMinVal(minPrimeSize, minPubKeySize);
        BigInteger N = r.encrypt(BigInteger.valueOf(1234567890));
        r.decrypt(N);


        r.getPrivateKey(r.publicKey, r.modulus);


    }
}