package RSA;

import java.math.BigInteger;

public class FactorModulus {

    public BigInteger[] fermats(BigInteger n) {
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ZERO;
        BigInteger bRemainder;
        BigInteger bSquared;

        try {
            if (n.mod(BigInteger.TWO).compareTo(BigInteger.ONE) != 0) {
                throw new IllegalArgumentException();
            }

            a = n.sqrt();

            do{
                a = a.add(BigInteger.ONE);
                bSquared = a.multiply(a).subtract(n);
                bRemainder = bSquared.sqrtAndRemainder()[1];
                b = bSquared.sqrtAndRemainder()[0];
            }while(bRemainder.compareTo(BigInteger.ZERO) != 0);

        }catch(Exception e){
            System.out.println("Error");
        }

        return new BigInteger[] {a.subtract(b), a.add(b)};
    }


    public static void main(String[] args){
        FactorModulus f = new FactorModulus();
        BigInteger n = new BigInteger("50001329807841901");
        BigInteger[] ab = f.fermats(n);
        System.out.println(ab[0]);
        System.out.println(ab[1]);
    }


}

