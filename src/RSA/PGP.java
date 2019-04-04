package RSA;

import java.math.BigInteger;

public class PGP {

    public void exchangeKey(RSA alice, RSA bob, String sharedKey) {
        alice.setSharedKey(sharedKey);

        BigInteger cipherTextRSA =
                alice.encryptStringRSA(sharedKey, bob.getPublicKey(),
                        bob.getModulus());
        BigInteger signature = alice.signMessage(cipherTextRSA);

        bob.decryptStringRSA(cipherTextRSA);
        bob.verifySignature(signature, cipherTextRSA,
                alice.getPublicKey(), alice.getModulus());
    }

    public void sendMessage(RSA alice, RSA bob, String message) {
        BigInteger cipherTextXOR = alice.encryptStringXOR(message);
        BigInteger signature = alice.signMessage(cipherTextXOR);

        bob.decryptStringXOR(cipherTextXOR);
        bob.verifySignature(signature, cipherTextXOR,
                alice.getPublicKey(), alice.getModulus());
    }

    public static void main(String[] args) {
        RSA alice = new RSA("Alice");
        RSA bob = new RSA("Bob");
        alice.keysFromMinVal(new BigInteger("12345678901234"),
                new BigInteger("1234567894"));
        bob.keysFromMinVal(new BigInteger("2234567890123"),
                new BigInteger("123456789"));
        String sharedKey = "Hello World";
        String message = "Secret!";

        PGP p = new PGP();
        p.exchangeKey(alice, bob,sharedKey);
        p.sendMessage(alice, bob, message);
    }
}
