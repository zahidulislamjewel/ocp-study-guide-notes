import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class MessageFormatTest {

    public static void main(String[] args) {

        Locale us = Locale.US;
        Locale france = Locale.FRANCE;

        double price = 1234.56;
        double discount = 0.15;

        // Convert LocalDate to java.util.Date
        LocalDate localDate = LocalDate.of(2025, Month.OCTOBER, 20);
        Date date = Date.from(
                localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
        );

        String pattern =
                "On {2,date,long}, the price is {0,number,currency} "
              + "with a discount of {1,number,percent}.";

        // US formatting
        MessageFormat usFormat = new MessageFormat(pattern, us);
        System.out.println("US:");
        System.out.println(usFormat.format(new Object[]{price, discount, date}));
        System.out.println();

        // France formatting
        MessageFormat frFormat = new MessageFormat(pattern, france);
        System.out.println("France:");
        System.out.println(frFormat.format(new Object[]{price, discount, date}));
        System.out.println();

        // Parameter reordering
        String reorderedPattern =
                "Discount: {1,number,percent} | Price: {0,number,currency}";

        MessageFormat reordered = new MessageFormat(reorderedPattern, us);
        System.out.println("Reordered:");
        System.out.println(reordered.format(new Object[]{price, discount}));
    }
}