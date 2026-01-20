import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

public class InstantTest {
    public static void main(String[] args) {
        var firstInstant = Instant.now();
        System.out.println("Current Instant: " + firstInstant);

        var specificInstant = Instant.ofEpochSecond(1_600_000_000L);
        System.out.println("Specific Instant (Epoch Second 1600000000): " + specificInstant);

        var instantFromMillis = Instant.ofEpochMilli(1_600_000_000_000L);
        System.out.println("Instant from Millis (Epoch Milli 1600000000000): " + instantFromMillis);

        var plusDuration = firstInstant.plusSeconds(3600); // adding 1 hour
        System.out.println("Instant after adding 1 hour: " + plusDuration);

        var minusDuration = firstInstant.minusSeconds(3600); // subtracting 1 hour
        System.out.println("Instant after subtracting 1 hour: " + minusDuration);

        var durationBetween = Duration.between(minusDuration, plusDuration);
        System.out.println("Duration between minus and plus instant: " + durationBetween);

        var lastInstant = Instant.now();
        var timeElapsed = Duration.between(firstInstant, lastInstant);
        System.out.println("Time elapsed between two instants: " + timeElapsed.toMillis() + " milliseconds");

        var anotherInstant = ZonedDateTime.now().toInstant();
        System.out.println("Another Instant from ZonedDateTime: " + anotherInstant);
    }
}
