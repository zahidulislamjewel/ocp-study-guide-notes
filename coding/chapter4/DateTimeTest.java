import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesProvider;
import java.util.List;

public class DateTimeTest {
    public static void main(String[] args) {
        System.out.println(LocalDate.now());
        System.out.println(LocalDate.of(2020, 5, 15));
        System.out.println(LocalDate.parse("2023-06-15"));
        System.out.println("=".repeat(100));

        System.out.println(LocalTime.now());
        System.out.println(LocalTime.of(14, 30, 15));
        System.out.println(LocalTime.parse("09:45:30"));
        System.out.println("=".repeat(100));

        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.of(2020, 5, 15, 14, 30, 15));
        System.out.println(LocalDateTime.parse("2023-06-15T09:45:30"));
        System.out.println("=".repeat(100));

        System.out.println(ZonedDateTime.now());
        System.out.println(ZonedDateTime.of(2020, 5, 15, 14, 30, 15, 0, ZoneId.systemDefault()));
        System.out.println(ZonedDateTime.of(2020, 5, 15, 14, 30, 15, 0, ZoneId.of("America/New_York")));
        System.out.println(ZonedDateTime.parse("2023-06-15T09:45:30+05:30[Asia/Karachi]"));
        System.out.println(ZoneId.of("Asia/Dhaka"));
        // System.out.println(ZoneId.of("Asia/Unknown")); // throws java.time.zone.ZoneRulesException
        System.out.println("=".repeat(100));

        System.out.println(OffsetDateTime.now());
        System.out.println(OffsetDateTime.now(ZoneOffset.of("+06:00")));
        System.out.println("=".repeat(100));

        ZoneRulesProvider.getAvailableZoneIds()
                .stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("=".repeat(100));

        System.out.println(ZoneRulesProvider.getAvailableZoneIds().size());
        List.copyOf(ZoneRulesProvider.getAvailableZoneIds())
                .stream()
                .sorted()
                .forEach(System.out::println);

        var date1 = LocalDate.of(2025, Month.DECEMBER, 27);
        var date2 = LocalDate.of(2026, 1, 10);
        var date3 = LocalDate.now().plusDays(15).plusMonths(1).plusYears(1);
        var date4 = LocalDate.now().minusYears(54).minusMonths(4).minusDays(3).getDayOfWeek();
        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);
        System.out.println(date4);
    }
}
