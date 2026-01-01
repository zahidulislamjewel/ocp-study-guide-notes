
public class StringTest {
    public static void main(String[] args) {
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = new String("Hello");
        String s4 = s3.intern();
        System.out.println("s1 == s2: " + (s1 == s2)); // true
        System.out.println("s1 == s3: " + (s1 == s3)); // false
        System.out.println("s1 == s4: " + (s1 == s4)); // true
        System.out.println("=".repeat(100));

        StringBuilder sb1 = new StringBuilder("Hello");
        StringBuilder sb2 = new StringBuilder("Hello");
        System.out.println("sb1 == sb2: " + (sb1 == sb2)); // false
        System.out.println("sb1.toString() == sb2.toString(): " + (sb1.toString() == sb2.toString())); // false
        System.out.println("sb1.toString().equals(sb2.toString()): " + sb1.toString().equals(sb2.toString())); // true
        System.out.println("=".repeat(100));
        String str1 = "Java";
        String str2 = "JAVA";
        System.out.println("str1.equals(str2): " + str1.equals(str2)); // false
        System.out.println("str1.equalsIgnoreCase(str2): " + str1.equalsIgnoreCase(str2)); // true

        System.out.println("=".repeat(100));
        // returns true only if the string has 0 length
        System.out.println("  ".isEmpty());
        System.out.println("".isEmpty());

        // returns true if the string is empty or contains only white space codepoints
        System.out.println("  ".isBlank());
        System.out.println("".isBlank());

        System.out.println("=".repeat(100));
        var name = "James";
        var score = 90.25;
        var total = 100;
        System.out.println("%s:%n   Score: %.2f out of %d".formatted(name, score, total));

        System.out.println("=".repeat(100));
        System.out.println();
        var pi = 3.14159265359;
        System.out.format("[%f]", pi); // [3.141593]
        System.out.format("[%12.8f]", pi); // [  3.14159265]
        System.out.format("[%012f]", pi); // [00003.141593]
        System.out.format("[%12.2f]", pi); // [        3.14]
        System.out.format("[%.3f]", pi); // [3.142]
        System.out.println();

        System.out.println("=".repeat(100));
        StringBuilder sb = new StringBuilder();
        for (char ch = 'a'; ch <= 'z'; ch++) {
            sb.append(ch);
        }
        System.out.println(sb);
        System.out.println(sb.length());
        System.out.println(sb.toString());
        System.out.println("=".repeat(100));
        var builder = new StringBuilder()
                .appendCodePoint(87).append(",")
                .appendCodePoint((char) 87).append(",")
                .append(87).append(",")
                .appendCodePoint(8217);
        System.out.println(builder);
        builder.delete(0, 2);
        builder.delete(0, 100); // no error if the end index exceeds the length
        System.out.println(builder);
        // builder.deleteCharAt(2); // error if the index exceeds the length
        System.out.println(builder);
    }
}
