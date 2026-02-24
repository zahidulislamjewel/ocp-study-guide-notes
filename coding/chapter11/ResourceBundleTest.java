import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleTest {

    public static void main(String[] args) {

        Locale us = Locale.US;
        Locale france = Locale.FRANCE;

        printMessages(us);
        printMessages(france);
    }

    private static void printMessages(Locale locale) {

        ResourceBundle rb = ResourceBundle.getBundle("Zoo", locale);

        System.out.println("Locale: " + locale);
        System.out.println(rb.getString("hello"));
        System.out.println(rb.getString("open"));
        System.out.println();
    }
}