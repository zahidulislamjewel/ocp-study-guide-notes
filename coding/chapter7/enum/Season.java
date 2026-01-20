public enum Season {
    SPRING,
    SUMMER,
    FALL,
    WINTER;
}

class EnumTest {
    public static void main(String[] args) {
        for (var season : Season.values()) {
            System.out.println(season.name() + " " + season.ordinal());
        }
        System.out.println("=".repeat(100));

        Season summer = Season.valueOf("SUMMER");
        Season winter = Season.valueOf("WINTER");
        Season spring = Season.valueOf("SPRING");

        System.out.println(Season.SUMMER.equals(summer));
        System.out.println(Season.WINTER.equals(winter));
        System.out.println(Season.SPRING.equals(spring));
    }
}
