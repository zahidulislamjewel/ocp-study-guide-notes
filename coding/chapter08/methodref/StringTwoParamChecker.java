interface StringTwoParamChecker {
    boolean check(String text, String prefix);    
}

class StringTwoParamCheckerTest {
    public static void main(String[] args) {
        StringTwoParamChecker lambda = (str, prefix) -> str.startsWith(prefix);
        StringTwoParamChecker methodRef = String::startsWith;

        System.out.println(lambda.check("Zoo", "Z"));       // true
        System.out.println(methodRef.check("Zoo", "Z"));    // true
    }
}
