package lion;

public class AfricanLion extends Lion {
    protected void setProperties(int age, String n) {
        setAge(age);
        name = n;
    }

    public void roar() {
        System.out.println(name + ", age " + getAge() + ", says: Roar! Roar!");
    }
}
