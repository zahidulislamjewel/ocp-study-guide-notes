import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CompactNumberFormatTest {

    public static void main(String[] args) throws ParseException {

        NumberFormat shortUS = NumberFormat.getCompactNumberInstance(
                Locale.US, NumberFormat.Style.SHORT);

        NumberFormat longUS = NumberFormat.getCompactNumberInstance(
                Locale.US, NumberFormat.Style.LONG);

        int number = 7_123_456;

        System.out.println("SHORT: " + shortUS.format(number));
        System.out.println("LONG: " + longUS.format(number));

        // Parsing compact values
        System.out.println("Parse '1M': " + shortUS.parse("1M"));
        System.out.println("Parse '7M': " + shortUS.parse("7M"));
    }
}