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
    private bitwiseXOR bitwiseXOR;

    public RSA(String n) {
        name = n;
        generatePrimes = new GeneratePrimes();
        generateKeys = new GenerateKeys();
        factorModulus = new FactorModulus();
        exponentMod = new ExponentMod();
        convertNumeric = new ConvertNumeric();
        bitwiseXOR = new bitwiseXOR();
    }

    public RSA(BigInteger pubKey, BigInteger priKey, BigInteger mod) {
        generatePrimes = new GeneratePrimes();
        generateKeys = new GenerateKeys();
        factorModulus = new FactorModulus();
        exponentMod = new ExponentMod();
        convertNumeric = new ConvertNumeric();
        bitwiseXOR = new bitwiseXOR();

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

    public void setSharedKey(String k) {

        sharedKey = convertNumeric.convertToNumeric(k);
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


    public BigInteger encryptRSA(BigInteger message, BigInteger pubKey,
                                 BigInteger mod) {
        System.out.println(name + " is encrypting with RSA...");
        BigInteger N = exponentMod.exponentMod(message, pubKey, mod);
        System.out.println(message);
        System.out.println("Encrypts to");
        System.out.println(N);
        System.out.println();
        return N;
    }

    public BigInteger encryptStringRSA(String messageString,
                                       BigInteger pubKey,
                                       BigInteger mod) {
        System.out.println(name + " is converting the shared key \"" +
                messageString + "\" to numeric...");

        BigInteger message =
                convertNumeric.convertToNumeric(messageString);

        System.out.println(
                "The numeric representation of \"" + messageString +
                        "\" is");
        System.out.println(message);
        System.out.println();

        return encryptRSA(message, pubKey, mod);
    }

    public BigInteger decryptRSA(BigInteger cipherText) {
        System.out.println(name + " is decrypting with RSA...");
        BigInteger M = exponentMod
                .exponentMod(cipherText, privateKey, modulus);
        System.out.println(cipherText);
        System.out.println("Decrypts to");
        System.out.println(M);
        System.out.println();
        sharedKey = M;
        return M;
    }

    public String decryptStringRSA(BigInteger cipherText) {
        BigInteger M = decryptRSA(cipherText);
        System.out.println(name + " is converting " + M + " back to a" +
                " String...");

        String messageString = convertNumeric.convertToString(M);

        System.out.println("The shared key is");
        System.out.println("\"" + messageString + "\"");
        System.out.println();

        return messageString;
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

    public BigInteger encryptXOR(BigInteger message) {
        System.out.println(name + " is encrypting with bitwise XOR...");
        BigInteger N = bitwiseXOR.encrypt(message, sharedKey);
        System.out.println(message);
        System.out.println("Encrypts to");
        System.out.println(N);
        System.out.println();
        return N;
    }

    public BigInteger encryptStringXOR(String messageString) {
        System.out.println(name + " is converting the message \"" +
                messageString + "\" to numeric...");

        BigInteger message =
                convertNumeric.convertToNumeric(messageString);

        System.out.println(
                "The numeric representation of \"" + messageString +
                        "\" is");
        System.out.println(message);
        System.out.println();

        return encryptXOR(message);
    }

    public BigInteger decryptXOR(BigInteger ciphertext) {
        System.out.println(name + " is decrypting with bitwise XOR...");
        BigInteger N = bitwiseXOR.decrypt(ciphertext, sharedKey);
        System.out.println(ciphertext);
        System.out.println("Decrypts to");
        System.out.println(N);
        System.out.println();
        return N;
    }

    public String decryptStringXOR(BigInteger cipherText) {
        BigInteger M = decryptXOR(cipherText);
        System.out.println(name + " is converting " + M + " back to a" +
                " String...");

        String messageString = convertNumeric.convertToString(M);

        System.out.println("The message is");
        System.out.println("\"" + messageString + "\"");
        System.out.println();

        return messageString;
    }


    public static void main(String[] args) {
        RSA alice = new RSA("Alice");
        RSA bob = new RSA("Bob");
        alice.keysFromMinVal(new BigInteger("12345678901234"),
                new BigInteger("1234567894"));
        bob.keysFromMinVal(new BigInteger("2234567890123"),
                new BigInteger("123456789"));


        String sharedKey = "Hello World";


        BigInteger cipherText =
                alice.encryptStringRSA(sharedKey, bob.getPublicKey(),
                        bob.getModulus());
        BigInteger signature = alice.signMessage(cipherText);


        bob.decryptStringRSA(cipherText);
        bob.verifySignature(signature, cipherText, alice.getPublicKey(),
                alice.getModulus());
    }
}