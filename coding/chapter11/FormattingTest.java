import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class FormattingTest {
    public static void main(String[] args) {
        double d = 1234.567;
        double t = 1234;
        NumberFormat f1 = new DecimalFormat("###,###,###.0");
        System.out.println(f1.format(d));
        System.out.println(f1.format(t));

        NumberFormat f2 = new DecimalFormat("000,000,000.0000");
        System.out.println(f2.format(d));

        NumberFormat f3 = new DecimalFormat("Your Balance $#,###,###.##");
        System.out.println(f3.format(d));

        System.out.println("=".repeat(100));

        LocalDate date = LocalDate.of(2026, Month.NOVEMBER, 23);
        LocalTime time = LocalTime.of(11, 12, 34);
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        System.out.println(date.getDayOfWeek());
        System.out.println(date.getDayOfMonth());
        System.out.println(date.getDayOfYear());

        System.out.println(date.getMonth());
        System.out.println(date.getYear());

        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
        System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("M dd, yyyy 'at' hh:mm");
        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("MM dd, yyyy 'at' hh:mm");
        DateTimeFormatter dateTimeFormatter3 = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm");
        DateTimeFormatter dateTimeFormatter4 = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm");
        DateTimeFormatter dateTimeFormatter5 = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a");
        System.out.println(dateTime.format(dateTimeFormatter1));
        System.out.println(dateTime.format(dateTimeFormatter2));
        System.out.println(dateTime.format(dateTimeFormatter3));
        System.out.println(dateTime.format(dateTimeFormatter4));
        System.out.println(dateTime.format(dateTimeFormatter5));

        var dt = LocalDateTime.of(2025, Month.OCTOBER, 20, 14, 15, 30);

        var formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        System.out.println(dt.format(formatter1));

        var formatter2 = DateTimeFormatter.ofPattern("MM_yyyy_-_dd");
        System.out.println(dt.format(formatter2));

        var formatter3 = DateTimeFormatter.ofPattern("hh:mm:z");

        // java.time.DateTimeException: Unable to extract ZoneId from temporal 2025-10-20T14:15:30
        // System.out.println(dt.format(formatter3));  

        System.out.println(formatter1.format(dt));
        System.out.println(dt.format(formatter1));

        System.out.println("=".repeat(100));

        var s1 = DateTimeFormatter.ofPattern("MMMM dd, yyyy ");
        var s2 = DateTimeFormatter.ofPattern(" hh:mm");
        var s3 = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm");

        System.out.println(dt.format(s1) + "at" + dt.format(s2));
        System.out.println(dt.format(s3));
    }
}
