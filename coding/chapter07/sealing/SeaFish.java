sealed class SeaFish permits Salmon, Tuna {
    public static void main(String[] args) {
        SeaFish tunaFish = new Tuna();
        System.out.println(getType(tunaFish));
    }

    public static String getType(SeaFish fish) {
        return switch (fish) {
            case Salmon t -> "Salmon!";
            case Tuna b -> "Tuna!";
            case SeaFish f -> "Sea Fish";
        };
    }

}

final class Salmon extends SeaFish {
}

final class Tuna extends SeaFish {
}
