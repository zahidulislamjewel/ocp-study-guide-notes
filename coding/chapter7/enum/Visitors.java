interface Visitors {
    void printVisitors();
}

enum SeasonWithVisitors implements Visitors {
    WINTER("Low"), SPRING("Medium"), SUMMER("High"), FALL("Medium");

    private final String visitors;
    private static final String DESCRIPTION = "Weather enum";

    SeasonWithVisitors(String visitors) {
        System.out.println("Constructing enum with value: " + visitors);
        this.visitors = visitors;
    }

    @Override
    public void printVisitors() {
        System.out.println(visitors);
    }
}

enum SeasonWithTimes {
    WINTER {
        public String getHours() {
            return "10am-3pm";
        }
    },
    SPRING {
        public String getHours() {
            return "9am-5pm";
        }
    },
    SUMMER {
        public String getHours() {
            return "9m-7pm";
        }
    },
    FALL {
        public String getHours() {
            return "9am-5pm";
        }
    };

    // This abstract method should be implemented by all enum constants
    // As, the constants are themselves instances of this enum
    public abstract String getHours();
}

class EnumConstructorTest {
    public static void main(String[] args) {
        Visitors summerVisitors = SeasonWithVisitors.SUMMER;
        summerVisitors.printVisitors();
        System.out.println("=".repeat(100));
        var winterVisitors = SeasonWithVisitors.WINTER;
        winterVisitors.printVisitors();
        System.out.println("=".repeat(100));
        SeasonWithVisitors.FALL.printVisitors();
        System.out.println("=".repeat(100));
        String summerHours = SeasonWithTimes.SUMMER.getHours();
        System.out.println(summerHours);
    }
}