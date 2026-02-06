public class Processor {

    public void processData() {
        final int length = 5;
        int width = 10;
        int height = 2;

        class VolumeCalculator {
            public int multiply() {
                return length * width * height;
            }
        }
        // width = 2; // DOES NOT COMPILE, Local variable width is required to be final or effectively final based on its usage inside Local class
        var calculator = new VolumeCalculator();
        int volume = calculator.multiply();
        System.out.println(volume);
    }

    public static void main(String[] args) {
        var processor = new Processor();
        processor.processData();
    }
}
