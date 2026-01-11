interface StringStart {
    boolean beginningCheck(String prefix);    
}

class StringStartTest {
    public static void main(String[] args) {
        var str = "Zoo";
        StringStart lambda = prefix -> str.startsWith(prefix);
        StringStart methodRef = str::startsWith;

        System.out.println(lambda.beginningCheck("A"));
        System.out.println(methodRef.beginningCheck("A"));
    }
}
