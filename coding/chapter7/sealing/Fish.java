abstract sealed class Fish permits Trout, Bass {
    public static void main(String[] args) {
        BigFish troutFish = new Trout();
        System.out.println(getType(troutFish));
    }

    public static String getType(BigFish fish) {
        return switch (fish) {
            case Trout t -> "Trout!";
            case Bass b -> "Bass!";
        };
    }

}

final class Trout extends BigFish {
}

final class Bass extends BigFish {
}
