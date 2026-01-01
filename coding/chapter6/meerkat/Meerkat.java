package meerkat;

class Carnivore {
    protected boolean hasFur = false;
}

public class Meerkat extends Carnivore {
    protected boolean hasFur = true;

    public static void main(String[] args) {
        Meerkat meerkat = new Meerkat();
        Carnivore carnivore = meerkat;
        System.out.println(meerkat.hasFur); // calls the child class boolean (method hiding)
        System.out.println(carnivore.hasFur); // calls the parent class boolean
    }
}
