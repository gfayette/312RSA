package RSA;

import java.math.BigInteger;
import java.util.*;

public class RSA {

    private String name;
    private BigInteger publicKey;
    private BigInteger privateKey;
    private BigInteger modulus;
    private BigInteger sharedKey;
    private GeneratePrimes generatePrimes;
    private GenerateKeys generateKeys;
    private FactorModulus factorModulus;
    private ExponentMod exponentMod;
    private ConvertNumeric convertNumeric;

    public RSA(String n) {
        name = n;
        generatePrimes = new GeneratePrimes();
        generateKeys = new GenerateKeys();
        factorModulus = new FactorModulus();
        exponentMod = new ExponentMod();
        convertNumeric = new ConvertNumeric();
    }

    public RSA(BigInteger pubKey, BigInteger priKey, BigInteger mod) {
        generatePrimes = new GeneratePrimes();
        generateKeys = new GenerateKeys();
        factorModulus = new FactorModulus();
        exponentMod = new ExponentMod();
        convertNumeric = new ConvertNumeric();

        publicKey = pubKey;
        privateKey = priKey;
        modulus = mod;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public void setModulus(BigInteger modulus) {
        this.modulus = modulus;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(BigInteger publicKey) {
        this.publicKey = publicKey;
    }

    public BigInteger getSharedKey() {
        return sharedKey;
    }

    public void setSharedKey(BigInteger k) {
        sharedKey = k;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger[] keysFromPrimes(BigInteger p, BigInteger q,
                                       BigInteger minPubKey) {
        System.out.println(
                "Generating keys for " + name + " with minimum public" +
                        " key size " + minPubKey + "...");

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
        System.out.println(
                "Generating primes greater than " + minPrime + "...");
        long start = System.currentTimeMillis();

        BigInteger p = generatePrimes.generatePrime(minPrime);
        Random r = new Random();
        BigInteger qMin = p.multiply(BigInteger
                .valueOf((long) (100.0 + 900.0 * r.nextDouble())));
        BigInteger q = generatePrimes.generatePrime(qMin);

        double elapsedSeconds =
                (System.currentTimeMillis() - start) / 1000.0;

        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("It took " + elapsedSeconds + " seconds to" +
                " generate these prime numbers");
        System.out.println();

        return keysFromPrimes(p, q, minPubKey);
    }

    public BigInteger encrypt(BigInteger message) {
        System.out.println(name + " is encrypting...");
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
        System.out.println(name + " is encrypting...");
        BigInteger N = exponentMod.exponentMod(message, pubKey, mod);
        System.out.println(message);
        System.out.println("Encrypts to");
        System.out.println(N);
        System.out.println();
        return N;
    }

    public BigInteger encryptString(String messageString,
                                 BigInteger pubKey,
                              BigInteger mod) {
        System.out.println(name + " is converting \"" + messageString +
                "\" to numeric...");

        BigInteger message =
                convertNumeric.convertToNumeric(messageString);

        System.out.println("The numeric representation of \"" + messageString + "\" is");
        System.out.println(message);
        System.out.println();

        return encrypt(message,pubKey,mod);
    }

    public BigInteger decrypt(BigInteger cipherText) {
        System.out.println(name + " is decrypting...");
        BigInteger M = exponentMod
                .exponentMod(cipherText, privateKey, modulus);
        System.out.println(cipherText);
        System.out.println("Decrypts to");
        System.out.println(M);
        System.out.println();
        sharedKey = M;
        return M;
    }

    public String decryptString(BigInteger cipherText) {
        BigInteger M = decrypt(cipherText);
        System.out.println(name + " is converting " + M + " back to a" +
                " String...");

        String messageString = convertNumeric.convertToString(M);

        System.out.println("The String is");
        System.out.println("\"" + messageString + "\"");
        System.out.println();

        return messageString;
    }

    public BigInteger decrypt(BigInteger cipherText,
                              BigInteger privateKey,
                              BigInteger modulus) {
        System.out.println(name + " is decrypting...");
        BigInteger M = exponentMod
                .exponentMod(cipherText, privateKey, modulus);
        System.out.println(cipherText);
        System.out.println("Decrypts to");
        System.out.println(M);
        System.out.println();
        sharedKey = M;
        return M;
    }

    public BigInteger signMessage(BigInteger message) {
        System.out.println(name + " is signing the message ...");
        System.out.println(message);

        BigInteger signature =
                exponentMod.exponentMod(message, privateKey, modulus);

        System.out.println("The signature is");
        System.out.println(signature);
        System.out.println();

        return signature;
    }

    public BigInteger signMessage(BigInteger message,
                                  BigInteger privateKey,
                                  BigInteger modulus) {
        System.out.println(name + " is signing the message ...");
        System.out.println(message);

        BigInteger signature =
                exponentMod.exponentMod(message, privateKey, modulus);

        System.out.println("The signature is");
        System.out.println(signature);
        System.out.println();

        return signature;
    }

    public boolean verifySignature(BigInteger signature,
                                   BigInteger message) {
        if (exponentMod.exponentMod(signature, publicKey, modulus)
                .compareTo(message) == 0) {
            System.out.println(signature);
            System.out.println("Is a valid signature for the message");
            System.out.println(message);
            System.out.println();
            return true;
        } else {
            System.out.println(signature);
            System.out.println(
                    "Is NOT a valid signature for the " + "message");
            System.out.println(message);
            System.out.println();
            return false;
        }
    }

    public boolean verifySignature(BigInteger signature,
                                   BigInteger message,
                                   BigInteger publicKey,
                                   BigInteger modulus) {
        if (exponentMod.exponentMod(signature, publicKey, modulus)
                .compareTo(message) == 0) {
            System.out.println(signature);
            System.out.println("Is a valid signature for the message");
            System.out.println(message);
            System.out.println();
            return true;
        } else {
            System.out.println(signature);
            System.out.println(
                    "Is NOT a valid signature for the " + "message");
            System.out.println(message);
            System.out.println();
            return false;
        }
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
                (System.currentTimeMillis() - start) / 1000.0;


        System.out.println("The modulus " + mod + " has factors:");
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("The private key corresponding to the " +
                "public key " + pubKey + " is:");
        System.out.println(privateKey);
        System.out.println("It took " + elapsedSeconds + " seconds to" +
                " find the private key");
        System.out.println();

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
        System.out.println();

        return primes;
    }


    public static void main(String[] args) {
        // Generate keys given minimum prime number value and minimum
        // public key value. Encrypt and Decrypt message using keys.

        BigInteger minPrimeSize = new BigInteger("393456");
        BigInteger minPubKeySize = new BigInteger("111");
        RSA r = new RSA("Alice");


        r.keysFromMinVal(minPrimeSize, minPubKeySize);


/*        BigInteger sig = r.signMessage(new BigInteger("555"));
        System.out.println(sig);
        r.verifySignature(sig, new BigInteger("555"));


        BigInteger N = r.encrypt(new BigInteger("123456789"));
        r.decrypt(N);

        r.getPrivateKey(r.publicKey, r.modulus);*/


    }
}