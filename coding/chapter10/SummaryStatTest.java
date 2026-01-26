import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

public class SummaryStatTest {

    public static int range(IntStream ints) {
        IntSummaryStatistics stats = ints.summaryStatistics();
        if(stats.getCount() == 0) {
            throw new RuntimeException();
        }
        System.out.println(stats);
        return stats.getMax() - stats.getMin();
    }
    
    public static void main(String[] args) {
        IntStream stream = IntStream.of(40, 90, 20, 30, 50);
        System.out.println(range(stream));
    }
}
