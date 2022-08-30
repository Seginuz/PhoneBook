package phonebook;

public class PhoneNumber {
    private final int number;
    private final String name;

    public PhoneNumber(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name + " - " + this.number;
    }
}
