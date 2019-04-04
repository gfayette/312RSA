package RSA;

import java.math.BigInteger;

public class PGP {

    public void exchangeKey(RSA alice, RSA bob){

    }

    public void sendMessage(RSA alice, RSA bob, String message){


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
                alice.encryptString(sharedKey, bob.getPublicKey(),
                        bob.getModulus());
        BigInteger signature = alice.signMessage(cipherText);

        bob.decryptString(cipherText);
        bob.verifySignature(signature, cipherText, alice.getPublicKey(),
                alice.getModulus());
    }
}
