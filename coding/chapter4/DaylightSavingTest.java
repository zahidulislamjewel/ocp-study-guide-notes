import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DaylightSavingTest {
    public static void main(String[] args) {
        var zone = ZoneId.of("US/Eastern");
        
        var springForwardDate = LocalDate.of(2025, Month.MARCH, 9);
        var springForwardTime = LocalTime.of(1, 30);
        var springForwardDateTime = ZonedDateTime.of(springForwardDate, springForwardTime, zone);
        System.out.println("Before DST change: " + springForwardDateTime);
        System.out.println(springForwardDateTime.getHour());
        System.out.println(springForwardDateTime.getOffset());

        springForwardDateTime = springForwardDateTime.plusHours(1);
        System.out.println("After adding 1 hour (DST change occurs): " + springForwardDateTime);
        System.out.println(springForwardDateTime.getHour());
        System.out.println(springForwardDateTime.getOffset());
        System.out.println("=".repeat(100));

        var fallBackDate = LocalDate.of(2025, Month.NOVEMBER, 2);
        var fallBackTime = LocalTime.of(1, 30);
        var fallBackDateTime = ZonedDateTime.of(fallBackDate, fallBackTime, zone);
        System.out.println("Before DST change: " + fallBackDateTime);
        System.out.println(fallBackDateTime.getHour());
        System.out.println(fallBackDateTime.getOffset());
        fallBackDateTime = fallBackDateTime.plusHours(1);
        System.out.println("After adding 1 hour (DST change occurs): " + fallBackDateTime);
        System.out.println(fallBackDateTime.getHour());
        System.out.println(fallBackDateTime.getOffset());

        fallBackDateTime = fallBackDateTime.plusHours(1);
        System.out.println("After adding another hour: " + fallBackDateTime);
        System.out.println(fallBackDateTime.getHour());
        System.out.println(fallBackDateTime.getOffset());
        System.out.println("=".repeat(100));

        var anotherDate = LocalDate.of(2025, Month.MARCH, 9);
        var anotherTime = LocalTime.of(2, 30); // Non-existent time due to DST spring forward from 2 AM to 3 AM
        var anotherDateTime = ZonedDateTime.of(anotherDate, anotherTime, zone);
        System.out.println("Non-existent time due to DST spring forward: " + anotherDateTime);
    }
}
