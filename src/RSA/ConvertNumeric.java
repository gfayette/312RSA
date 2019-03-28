package RSA;

import java.math.BigInteger;
import java.util.ArrayList;

public class ConvertNumeric {
    private BigInteger value128;

    public ConvertNumeric() {
        value128 = BigInteger.valueOf(128);
    }


    public BigInteger convertToNumeric(String message) {

        BigInteger[] chars = new BigInteger[message.length()];
        for (int i = 0; i < chars.length; ++i) {
            chars[i] = BigInteger.valueOf(message.charAt(i));
        }

        BigInteger numeric = BigInteger.ZERO;
        BigInteger multiples128 = BigInteger.ONE;


        for (int i = 0; i < chars.length; ++i) {
            numeric = numeric.add(chars[i].multiply(multiples128));
            multiples128 = multiples128.multiply(value128);
        }

        return numeric;
    }

    public String convertToString(BigInteger numeric) {
        ArrayList<Integer> chars = new ArrayList<>();

        BigInteger[] quotientAndRemainder =
                numeric.divideAndRemainder(value128);
        int i = 0;

        while (quotientAndRemainder[1].compareTo(BigInteger.ZERO) !=
                0) {
            chars.add(quotientAndRemainder[1].intValue());
            numeric = quotientAndRemainder[0];
            quotientAndRemainder = numeric.divideAndRemainder(value128);
            ++i;
        }

        StringBuilder message = new StringBuilder();
        for(i = 0; i < chars.size(); ++i){
            char c = (char)chars.get(i).intValue();
            message.append(c);
        }

        return message.toString();
    }


    public static void main(String[] args) {
        ConvertNumeric c = new ConvertNumeric();
        c.convertToString(c.convertToNumeric("Hello World"));
    }

}
