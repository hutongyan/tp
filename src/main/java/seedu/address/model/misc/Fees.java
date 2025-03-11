package seedu.address.model.misc;

/**
 * Represents a fee with an integer value.
 */
public class Fees {
    /** The value of the fee. */
    private final int value;

    /**
     * Constructs a {@code Fees} with the given value.
     *
     * @param value The value of the fee.
     */
    public Fees(int value) {
        this.value = value;
    }

    /**
     * Returns the string representation of the fee, which is its value.
     *
     * @return The value of the fee as a string.
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
