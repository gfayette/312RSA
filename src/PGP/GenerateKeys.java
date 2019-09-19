package PGP;

import java.math.BigInteger;

/**
 * MTH312PGP
 * GenerateKeys Class
 *
 * This class uses the extended euclidean algorithm to generate keys for RSA encryption.
 *
 * @author George Fayette
 **/

public class GenerateKeys {
    ExtendedEuclideanAlgorithm e;

    public GenerateKeys() {
        e = new ExtendedEuclideanAlgorithm();
    }

    public BigInteger generatePrivateKey(BigInteger p, BigInteger q, BigInteger pubKey) {
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        return e.extendedEuclidean(pubKey, phi)[2];
    }

    public BigInteger generatePublicKey(BigInteger p, BigInteger q) {
        BigInteger publicKey = BigInteger.ONE;
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger gcf = e.extendedEuclidean(phi, publicKey)[2];
        while (gcf.compareTo(BigInteger.ONE) != 0) {
            publicKey = publicKey.add(BigInteger.ONE);
            gcf = e.extendedEuclidean(phi, publicKey)[2];
        }
        return publicKey;
    }

    public BigInteger[] generateKeyPair(BigInteger p, BigInteger q, BigInteger minPubKeySize) {
        BigInteger publicKey = minPubKeySize;
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger[] result = e.extendedEuclidean(publicKey, phi);

        while (result[0].compareTo(BigInteger.ONE) != 0) {
            publicKey = publicKey.add(BigInteger.ONE);
            result = e.extendedEuclidean(publicKey, phi);
        }

        if (publicKey.compareTo(p.multiply(q)) != -1) {
            throw new IllegalArgumentException();
        }

        BigInteger[] keys = new BigInteger[3];
        keys[0] = publicKey;
        keys[1] = result[2];
        keys[2] = p.multiply(q);

        return keys;
    }
}
