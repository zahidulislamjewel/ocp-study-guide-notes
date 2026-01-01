import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DurationTest {
    public static void main(String[] args) {
        var daily = Duration.ofDays(1);
        var everyMinute = Duration.ofMinutes(1);
        var hourly = Duration.ofHours(1);
        var everyTwoHours = Duration.ofHours(2);
        var everyThreeHours = Duration.ofHours(3);
        var everyFourHours = Duration.ofHours(4);
        var everyFiveMinutes = Duration.ofMinutes(5);
        var everyTenSeconds = Duration.ofSeconds(10);
        var everyMilli = Duration.ofMillis(1);
        var everyNano = Duration.ofNanos(1);
        var customDuration = Duration.ofHours(1).plusMinutes(30).plusSeconds(45); // 1 hour, 30 minutes, 45 seconds
        System.out.println(daily);
        System.out.println(everyMinute);
        System.out.println(hourly);
        System.out.println(everyTwoHours);
        System.out.println(everyThreeHours);
        System.out.println(everyFourHours);
        System.out.println(everyFiveMinutes);
        System.out.println(everyTenSeconds);
        System.out.println(everyMilli);
        System.out.println(everyNano);
        System.out.println(customDuration);
        System.out.println("=".repeat(100));

        var startTime = LocalTime.of(10, 0);
        var endTime = LocalTime.of(15, 30);
        var untilDuration = Duration.between(startTime, endTime);
        System.out.println(untilDuration);
        System.out.println("=".repeat(100));

        var date = LocalDate.of(2024, 5, 2);
        var time = LocalTime.of(19, 30);
        var dateTime = LocalDateTime.of(date, time);
        var duration = Duration.ofHours(5).plusMinutes(15);
        System.out.println(time.plus(duration));
        System.out.println(dateTime.plus(duration));
        // System.out.println(date.plus(duration)); // UnsupportedTemporalTypeException
        System.out.println("=".repeat(100));

        // Duration factory methods with TemporalUnit interface (ChronoUnit implements TemporalUnit)
        var dailyDuration = Duration.of(1, ChronoUnit.DAYS);
        var hourlyDuration = Duration.of(1, ChronoUnit.HOURS);
        var everyMinuteDuration = Duration.of(1, ChronoUnit.MINUTES);
        var everyTenSecondsDuration = Duration.of(10, ChronoUnit.SECONDS);
        var everyMilliDuration = Duration.of(1, ChronoUnit.MILLIS);
        var everyNanoDuration = Duration.of(1, ChronoUnit.NANOS);

        System.out.println(dailyDuration);
        System.out.println(hourlyDuration);
        System.out.println(everyMinuteDuration);
        System.out.println(everyTenSecondsDuration);
        System.out.println(everyMilliDuration);
        System.out.println(everyNanoDuration);
        System.out.println("=".repeat(100));

        var one = LocalTime.of(10, 0);
        var two = LocalTime.of(15, 30);
        var today = LocalDate.now();
        var anotherDate = LocalDate.of(2026, 5, 2);
        System.out.println(ChronoUnit.HOURS.between(one, two));
        System.out.println(ChronoUnit.MINUTES.between(one, two));
        System.out.println(ChronoUnit.SECONDS.between(one, two));
        System.out.println(ChronoUnit.DAYS.between(today, anotherDate));
        // System.out.println(ChronoUnit.MINUTES.between(one, today)); // java.time.DateTimeException;
        var anotherTime = LocalTime.of(9, 15, 30);
        System.out.println(anotherTime);
        System.out.println(anotherTime.truncatedTo(ChronoUnit.MINUTES));
        System.out.println(anotherTime.truncatedTo(ChronoUnit.HOURS));
    }
}
