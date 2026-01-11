interface StringChecker {
    boolean check();    
}

class StringCheckerTest {
    public static void main(String[] args) {
        var str = " ";
        StringChecker lambda = () -> str.isEmpty();
        StringChecker methodRef = str::isEmpty;

        System.out.println(lambda.check());
        System.out.println(methodRef.check());
    }
}
