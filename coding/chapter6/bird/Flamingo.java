package bird;

public class Flamingo {
    protected String color = null;

    public void setColor(String color) {
        // color = color; // The assignment to variable color has no effect due to ambiguity
        this.color = color; // no ambiguity
    }
}