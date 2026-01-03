abstract sealed class Fish permits Trout, Bass {
    public static void main(String[] args) {
        Fish troutFish = new Trout();
        System.out.println(getType(troutFish));
    }

    public static String getType(Fish fish) {
        return switch (fish) {
            case Trout t -> "Trout!";
            case Bass b -> "Bass!";
        };
    }

}

final class Trout extends Fish {
}

final class Bass extends Fish {
}
