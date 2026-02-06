record BigFish(Object value) {
}

class Veterinarian {
    public static void main(String[] args) {
        BigFish f1 = new BigFish("Nemo");
        BigFish f2 = new BigFish(Integer.valueOf(1));

        if (f1 instanceof BigFish(Object t)) {
            System.out.print("Mathc1-");
        }

        if (f1 instanceof BigFish(String t)) {
            System.out.print("Mathc2-");
        }

        if (f1 instanceof BigFish(Integer t)) {
            System.out.print("Mathc3-");
        }

        if (f2 instanceof BigFish(Object t)) {
            System.out.print("Mathc4-");
        }

        if (f2 instanceof BigFish(String t)) {
            System.out.print("Mathc5-");
        }

        if (f2 instanceof BigFish(Integer t)) {
            System.out.print("Mathc6");
        }
    }
}
