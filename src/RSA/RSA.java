package RSA;

import java.math.BigInteger;

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
                        minPubKey);

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
                .println("Generating primes greater than " + minPrime);

        BigInteger p = generatePrimes.generatePrime(minPrime);
        BigInteger q = generatePrimes
                .generatePrime(p.multiply(BigInteger.valueOf(100)));

        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println();

        return keysFromPrimes(p, q, minPubKey);
    }

    public BigInteger encrypt(BigInteger message) {
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
        return exponentMod.exponentMod(message, pubKey, mod);
    }

    public BigInteger decrypt(BigInteger cipherText) {
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
        return exponentMod.exponentMod(cipherText, privateKey, modulus);
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


    public static void main(String args[]) {
        RSA r = new RSA();
        r.keysFromMinVal(BigInteger.valueOf(1000000),
                BigInteger.valueOf(500000));
        BigInteger N = r.encrypt(BigInteger.valueOf(1234567890));
        r.decrypt(N);

    }
}