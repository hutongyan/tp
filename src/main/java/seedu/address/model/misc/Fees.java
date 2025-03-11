package seedu.address.model.misc;

public class Fees {
    private final int value;

    public Fees(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
