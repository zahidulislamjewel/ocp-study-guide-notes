import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

public class PeriodTest {
    public static void main(String[] args) {
        var annuually = Period.ofYears(1);
        var quarterly = Period.ofMonths(3);
        var biWeekly = Period.ofWeeks(2);
        var everyThreeWeeks = Period.ofWeeks(3);
        var daily = Period.ofDays(1);
        var everyOtherDay1 = Period.of(0, 0, 2);
        var everyOtherDay2 = Period.ofDays(2);
        var everyYearAndWeek = Period.of(1, 0, 7);
        var curstomPeriod = Period.of(1, 2, 3); // 1 year, 2 months, 3 days
        System.out.println(annuually);
        System.out.println(quarterly);
        System.out.println(biWeekly);
        System.out.println(everyThreeWeeks);
        System.out.println(daily);
        System.out.println(everyOtherDay1);
        System.out.println(everyOtherDay2);
        System.out.println(everyYearAndWeek);
        System.out.println(curstomPeriod);
        System.out.println("=".repeat(100));

        var xmas = LocalDate.of(2025, 12, 25);
        var newYear = LocalDate.of(2026, 1, 1);
        var untilPeriod = xmas.until(newYear);
        System.out.println(untilPeriod);
        System.out.println(Period.between(xmas, newYear));
        System.out.println(Period.between(newYear, xmas)); // negative period
        var today = LocalDate.now();
        var bday = LocalDate.of(2026, 5, 2);
        System.out.println("Period for Birthday: " + Period.between(today, bday));
        System.out.println("=".repeat(100));

        var date = LocalDate.of(2024, 05, 02);
        var time = LocalTime.of(19, 30);
        var dateTime = LocalDateTime.of(date, time);
        var period = Period.ofYears(6);
        System.out.println(date.plus(period));
        System.out.println(dateTime.plus(period));
        // System.out.println(time.plus(period)); // UnsupportedTemporalTypeException
    }
}
