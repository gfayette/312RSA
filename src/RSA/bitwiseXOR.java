package RSA;

import java.math.BigInteger;

public class bitwiseXOR {


    public String convertToBinary(BigInteger numeric) {
        BigInteger two = BigInteger.TWO;
        String binary = "";

        while (numeric.compareTo(BigInteger.ZERO) != 0) {
            BigInteger[] divRem = numeric.divideAndRemainder(two);

            if (divRem[1].compareTo(BigInteger.ONE) == 0) {
                binary = "1" + binary;
            } else {
                binary = "0" + binary;
            }

            numeric = divRem[0];
        }
        return binary;
    }

    public BigInteger convertFromBinary(String cText) {
        BigInteger two = BigInteger.TWO;
        BigInteger powersOfTwo = BigInteger.TWO;
        BigInteger numeric = BigInteger.ZERO;

        if (cText.charAt(cText.length() - 1) == '1') {
            numeric = numeric.add(BigInteger.ONE);
        }

        cText = cText.substring(0, cText.length() -1);

        while (cText.length() > 0) {

            if (cText.charAt(cText.length() - 1) == '1') {
                numeric = numeric.add(powersOfTwo);
            }

            powersOfTwo = powersOfTwo.multiply(two);
            cText = cText.substring(0, cText.length() -1);
        }
        return numeric;
    }


    public String xOr(String binary, String key) {
        key = "0" + key;
        while (key.length() < binary.length()) {
            key += key;
        }

        String cipherText = "";

        for (int i = 0; i < binary.length(); ++i) {
            if (binary.charAt(i) == key.charAt(i)) {
                cipherText = cipherText + "0";
            } else {
                cipherText = cipherText + "1";
            }
        }

        return  cipherText;
    }


     public BigInteger encrypt(BigInteger numeric, BigInteger key){
        String binary = convertToBinary(numeric);
        String binaryKey = convertToBinary(key);
        String binaryCipher = xOr(binary,binaryKey);
        return convertFromBinary(binaryCipher);
     }

     public BigInteger decrypt(BigInteger cipherText, BigInteger key){
         String binary = convertToBinary(cipherText);
         String binaryKey = convertToBinary(key);
         String binaryPaintext = xOr(binary,binaryKey);
         return convertFromBinary(binaryPaintext);
     }


    public static void main(String[] args) {
        bitwiseXOR x = new bitwiseXOR();
        BigInteger plainText = new BigInteger(
                "1234567890123456789099999");
        BigInteger key = new BigInteger("123457");
        System.out.println(plainText);

        BigInteger cipherText = x.encrypt(plainText, key);
        System.out.println(cipherText);

        BigInteger plaintext2 = x.decrypt(cipherText, key);
        System.out.println(plaintext2);


    }
}

