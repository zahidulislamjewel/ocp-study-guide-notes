interface Converter {
    long convert(double num);    
}

class ConverterTest {
    public static void main(String[] args) {
        Converter lambda = num -> Math.round(num);
        Converter methodRef = Math::round;

        System.out.println(lambda.convert(3.141592653589793));
        System.out.println(methodRef.convert(3.141592653589793));
    }
}
