class Frog {
    String realName;
    static String name;

    public void hop() {
        System.out.println(name.toLowerCase() + " is hopping");
    }

    public void hop(String anotherName) {
        System.out.println(anotherName.toLowerCase() + " is hopping");
    }

    public void hopAgain() {
        System.out.println(this.realName.toLowerCase() + " is hopping");
    }

    public void hopAgainLocally() {
        String localName = null;
        System.out.println(localName.toLowerCase() + " is hopping");
    }
}

public class NullPointerExceptionTest {
    public static void main(String[] args) {
        // new Frog().hop();
        // new Frog().hop(null);
        // new Frog().hopAgain();
        new Frog().hopAgainLocally();
    }
}
