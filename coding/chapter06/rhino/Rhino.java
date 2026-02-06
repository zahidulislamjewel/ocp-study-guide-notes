package rhino;

abstract class Mammal {
    abstract void showHorn();
    abstract void eatLeaf();
} 

public abstract class Rhino extends Mammal {
    void showHorn() {
        // Inherited from Mammal
        // Overridden here
    }
}

class BlackRhino extends Rhino {
    void eatLeaf() {
        // Inherited from Rhino
        // Overridden
    }
}
