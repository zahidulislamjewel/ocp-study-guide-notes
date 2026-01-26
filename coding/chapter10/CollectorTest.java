import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorTest {

    public static void main(String[] args) {
        Stream<String> wordStream = Stream.of("w", "o", "l", "f");

        // StringBuilder wordBuilder = wordStream.collect(() -> new StringBuilder(),
        // (sb, str) -> sb.append(str),
        // (sb1, sb2) -> sb1.append(sb2));
        StringBuilder wordBuilder = wordStream.collect(StringBuilder::new,
                StringBuilder::append, StringBuilder::append);
        System.out.println(wordBuilder);

        Stream<String> anotherWordStream = Stream.of("w", "o", "l", "f");

        // TreeSet<String> anotherWordSet = anotherWordStream.collect(() -> new
        // TreeSet<>(), (ts, str) -> ts.add(str),
        // (ts1, ts2) -> ts1.addAll(ts2));
        TreeSet<String> anotherWordSet = anotherWordStream.collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
        System.out.println(anotherWordSet);

        Stream<String> thirdStream = Stream.of("w", "o", "l", "f");
        TreeSet<String> thirdWordSet = thirdStream.collect(Collectors.toCollection(TreeSet::new)); // sorted
                                                                                                   // lexicographically
        System.out.println(thirdWordSet);

        Stream<String> fourthStream = Stream.of("w", "o", "l", "f");
        Set<String> fourthSet = fourthStream.collect(Collectors.toSet()); // no order or sorting guranteed
        System.out.println(fourthSet);

    }
}