import java.util.Properties;

public class PropertiesTest {

    public static void main(String[] args) {

        Properties props = new Properties();

        // Simulating Spring Boot style properties
        props.setProperty("server.port", "8080");
        props.setProperty("spring.datasource.url", "jdbc:mysql://localhost:3306/mydb");
        props.setProperty("spring.datasource.username", "root");
        props.setProperty("spring.datasource.password", "password");
        props.setProperty("logging.level.root", "INFO");

        // Retrieve values
        System.out.println("Server Port: " + props.getProperty("server.port"));
        System.out.println("Datasource URL: " + props.getProperty("spring.datasource.url"));
        System.out.println("Username: " + props.getProperty("spring.datasource.username"));

        // Default value example
        System.out.println("Timeout (default 30): "
                + props.getProperty("server.timeout", "30"));

        // get() vs getProperty()
        System.out.println("Using get(): " + props.get("logging.level.root"));
    }
}