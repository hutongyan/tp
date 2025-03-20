package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccessFiltering;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Membership;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listAllUsers_showsFullList() {
        assertCommandSuccess(new ListCommand(
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()
        ), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noMatchingUsers_returnsEmptyList() {
        expectedModel.updateFilteredPersonList(person -> false); // Forces empty result

        assertCommandSuccess(new ListCommand(
                Optional.of("invalid@example.com"), Optional.of("NON-MEMBER"),
                Optional.empty(), Optional.empty(), Optional.empty()
        ), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_filterByActiveMembership_returnsMatchingUsers() {
        assertTrue(model.getFilteredPersonList().stream()
                        .anyMatch(p -> p.getMembership().equals(Membership.ACTIVE)),
                "Test data must contain at least one user with ACTIVE membership");

        model.updateFilteredPersonList(p -> p.getMembership().equals(Membership.ACTIVE));
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs()); // Ensure expectedModel is fresh
        expectedModel.updateFilteredPersonList(p -> p.getMembership().equals(Membership.ACTIVE));
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList(),
                "Filtered lists should match before executing command");

        ListCommand listCommand = new ListCommand(Optional.empty(), Optional.of("ACTIVE"),
                Optional.empty(), Optional.empty(), Optional.empty());

        assertCommandSuccessFiltering(listCommand, model, expectedModel);
    }

    @Test
    public void execute_filterByExpiredMembership_returnsMatchingUsers() {
        expectedModel.updateFilteredPersonList(p -> p.getMembership().equals(Membership.EXPIRED));

        assertCommandSuccessFiltering(new ListCommand(
                Optional.empty(), Optional.of("EXPIRED"), Optional.empty(), Optional.empty(), Optional.empty()
        ), model, expectedModel);
    }

    @Test
    public void execute_filterByEmail_returnsMatchingUser() {
        expectedModel.updateFilteredPersonList(p -> p.getEmail().equals(ALICE.getEmail()));

        assertCommandSuccessFiltering(new ListCommand(
                Optional.of(String.valueOf(ALICE.getEmail())), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty()
        ), model, expectedModel);
    }

    @Test
    public void execute_filterByEmailAndActiveMembership_returnsCorrectUser() {
        expectedModel.updateFilteredPersonList(p ->
                p.getEmail().equals(ALICE.getEmail()) && p.getMembership().equals(Membership.ACTIVE));

        assertCommandSuccessFiltering(new ListCommand(
                Optional.of(String.valueOf(ALICE.getEmail())), Optional.of("ACTIVE"), Optional.empty(),
                Optional.empty(), Optional.empty()
        ), model, expectedModel);
    }

    @Test
    public void execute_filterByEmailAndExpiredMembership_returnsEmptyList() {
        expectedModel.updateFilteredPersonList(p ->
                p.getEmail().equals(ALICE.getEmail()) && p.getMembership().equals(Membership.EXPIRED));

        assertCommandSuccessFiltering(new ListCommand(
                Optional.of(String.valueOf(ALICE.getEmail())), Optional.of("EXPIRED"), Optional.empty(),
                Optional.empty(), Optional.empty()
        ), model, expectedModel);
    }

    @Test
    public void execute_filterByName_returnsMatchingUser() {
        expectedModel.updateFilteredPersonList(p -> p.getName().equals(ALICE.getName()));

        assertCommandSuccessFiltering(new ListCommand(
                Optional.empty(), Optional.empty(), Optional.of(String.valueOf(ALICE.getName())),
                Optional.empty(), Optional.empty()
        ), model, expectedModel);
    }

    @Test
    public void execute_filterByNameAndActiveMembership_returnsMatchingUser() {
        expectedModel.updateFilteredPersonList(p ->
                p.getName().equals(ALICE.getName()) && p.getMembership().equals(Membership.ACTIVE));

        assertCommandSuccessFiltering(new ListCommand(
                Optional.empty(), Optional.of("ACTIVE"), Optional.of(String.valueOf(ALICE.getName())),
                Optional.empty(), Optional.empty()
        ), model, expectedModel);
    }
}
