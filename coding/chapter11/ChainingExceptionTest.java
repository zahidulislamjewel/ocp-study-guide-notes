class AnimalsOutForAWalk extends RuntimeException {
}

class ExhibitClosed extends RuntimeException {
}

class ExhibitCloseForLunch extends ExhibitClosed {
}

public class ChainingExceptionTest {
    public static void main(String[] args) {
        visitPorcupine();
    }

    public static void visitPorcupine() {
        try {
            seeAnimal();
        } catch (AnimalsOutForAWalk e) {
            System.out.println("try back later");
        } catch (ExhibitCloseForLunch e) {
            System.out.println("lunch break");
        } catch (ExhibitClosed e) {
            System.out.println("not today");
        }
    }

    public static void seeAnimal() {
        throw new ExhibitCloseForLunch();
    }

}
