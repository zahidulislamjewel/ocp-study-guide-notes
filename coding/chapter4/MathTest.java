import java.math.BigInteger;
import java.math.BigDecimal;

public class MathTest {
    public static void main(String[] args) {
        System.out.println(Math.min(4, 9));
        System.out.println(Math.max(4, 9));
        System.out.println(Math.abs(-10));
        System.out.println(Math.sqrt(16));
        System.out.println(Math.cbrt(27));
        System.out.println(Math.pow(2, 5));
        System.out.println(Math.exp(3));
        System.out.println(Math.log(10));
        System.out.println(Math.log10(1000));
        System.out.println(Math.log1p(9)); // log(1 + x)
        System.out.println(Math.sin(Math.PI / 2));
        System.out.println(Math.cos(0));
        System.out.println(Math.tan(Math.PI / 4));
        System.out.println(Math.asin(1));
        System.out.println(Math.acos(0));
        System.out.println(Math.atan(1));
        System.out.println(Math.toDegrees(Math.PI)); // radians to degrees
        System.out.println(Math.toRadians(180)); // degrees to radians
        System.out.println(Math.sinh(1));
        System.out.println(Math.cosh(1));
        System.out.println(Math.tanh(1));
        System.out.println(Math.floor(4.7));
        System.out.println(Math.ceil(4.3));
        System.out.println(Math.rint(4.5)); // returns the closest integer as double
        System.out.println(Math.round(4.5f)); // returns int
        System.out.println(Math.round(4.5)); // returns long
        System.out.println(Math.random()); // returns a double value between 0.0 and 1.0
        System.out.println(Math.PI);
        System.out.println("=".repeat(100));

        var bigInt = BigInteger.valueOf(5_000L);
        var bigDecimal = BigDecimal.valueOf(5_000L);
        System.out.println(bigInt);
        System.out.println(bigDecimal);
        System.out.println(bigInt.add(BigInteger.valueOf(2_500L)));
        System.out.println(bigDecimal.add(BigDecimal.valueOf(2_500L)));
        System.out.println(bigInt.subtract(BigInteger.valueOf(1_000L)));
        System.out.println(bigDecimal.subtract(BigDecimal.valueOf(1_000L)));
        System.out.println(bigInt.multiply(BigInteger.valueOf(3L)));
        System.out.println(bigDecimal.multiply(BigDecimal.valueOf(3L)));
        System.out.println(bigInt.divide(BigInteger.valueOf(4L)));
        System.out.println(bigDecimal.divide(BigDecimal.valueOf(4L)));
        System.out.println("=".repeat(100));

        var bigNum = BigInteger.valueOf(5)
                .pow(2)
                .add(BigInteger.valueOf(5))
                .multiply(BigInteger.valueOf(30))
                .divide(BigInteger.TEN)
                .max(BigInteger.valueOf(60))
                .min(BigInteger.valueOf(80));
        System.out.println(bigNum);
    }
}
