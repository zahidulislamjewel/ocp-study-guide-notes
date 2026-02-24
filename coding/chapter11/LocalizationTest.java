import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class LocalizationTest {

    public static void main(String[] args) throws ParseException {

        // ----- Default Locale -----
        System.out.println("Default Locale: " + Locale.getDefault());

        // ----- Creating Locales -----
        Locale us = Locale.US;
        Locale germany = Locale.GERMANY;
        Locale france = Locale.of("fr", "FR");

        System.out.println("US Locale: " + us);
        System.out.println("Germany Locale: " + germany);
        System.out.println("France Locale: " + france);

        // ----- Number Formatting -----
        int number = 1234567;

        NumberFormat usFormat = NumberFormat.getInstance(us);
        NumberFormat deFormat = NumberFormat.getInstance(germany);

        System.out.println("US format: " + usFormat.format(number));
        System.out.println("Germany format: " + deFormat.format(number));

        // ----- Currency Formatting -----
        double price = 48.75;

        NumberFormat usCurrency = NumberFormat.getCurrencyInstance(us);
        NumberFormat frCurrency = NumberFormat.getCurrencyInstance(france);

        System.out.println("US currency: " + usCurrency.format(price));
        System.out.println("France currency: " + frCurrency.format(price));

        // ----- Percent Formatting -----
        double successRate = 0.85;

        NumberFormat percent = NumberFormat.getPercentInstance(us);
        System.out.println("Percent (US): " + percent.format(successRate));

        // ----- Parsing -----
        String value = "1,234";
        Number parsed = usFormat.parse(value);
        System.out.println("Parsed value: " + parsed);

        // ----- Localized Dates -----
        LocalDateTime dt = LocalDateTime.of(2025, Month.OCTOBER, 20, 15, 30);

        DateTimeFormatter shortUS = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(us);

        DateTimeFormatter shortFR = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(france);

        System.out.println("US DateTime: " + dt.format(shortUS));
        System.out.println("France DateTime: " + dt.format(shortFR));

        // ----- Locale Category -----
        Locale.setDefault(Locale.Category.FORMAT, france);
        System.out.println("Currency with FORMAT locale FR: "
                + NumberFormat.getCurrencyInstance().format(price));
    }
}