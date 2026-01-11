interface StringParamChecker {
    boolean check(String text);    
}

class StringParamCheckerTest {
    public static void main(String[] args) {
        StringParamChecker lambda = s -> s.isEmpty();
        StringParamChecker methodRef = String::isEmpty;

        System.out.println(lambda.check(""));       // true
        System.out.println(methodRef.check("Zoo")); // false
    }
}
