public class A {
    private int x = 10;

    class B {
        private int x = 20;

        class C {
            private int x = 30;

            public void allTheX() {
                System.out.println(getClass().getName() + ":" + x);
                System.out.println(this.getClass().getName() + ":" + this.x);
                System.out.println(B.this.getClass().getName() + ":" + B.this.x);
                System.out.println(A.this.getClass().getName() + ":" + A.this.x);
            }
        }
    }

    public static void main(String[] args) {
        A a = new A();
        A.B b = a.new B();
        A.B.C c = b.new C();
        c.allTheX();
        System.out.println("=".repeat(100));

        var a2 = new A();
        var b2 = a.new B();
        var c2 = b.new C();
        c.allTheX();
        System.out.println("=".repeat(100));

        new A().new B().new C().allTheX();
    }
}
