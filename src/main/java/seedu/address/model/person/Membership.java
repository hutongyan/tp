package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's membership status in the addressbook.
 */
public enum Membership {
    ACTIVE("ACTIVE"),
    EXPIRED("EXPIRED"),
    NONMEMBER("NON-MEMBER");

    public final static String MESSAGE_CONSTRAINTS = "Membership status can only be: ACTIVE, EXPIRED, NON-MEMBER";

    private final String status;


    private Membership(String status) {
        requireNonNull(status);
        this.status = status;
    }


    public static Membership createMember(String test) throws RuntimeException {
        if (test.equals("ACTIVE")) {
            return ACTIVE;
        } else if (test.equals("EXPIRED")) {
            return EXPIRED;
        } else if (test.equals("NON-MEMBER")) {
            return NONMEMBER;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public String toString() {
        return status;
    }

}
