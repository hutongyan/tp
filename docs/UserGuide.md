---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# BookVault User Guide

BookVault is a **desktop app for managing library users and books**, optimized for use via a Command Line Interface (CLI), while retaining the advantages of a Graphical User Interface (GUI). It allows librarians to **quickly manage user memberships**, **issue and return books**, **track overdue fees**, and **maintain an up-to-date book catalog**.

<page-nav-print />

---

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.
2. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).
3. Copy the file to your desired _home folder_ for BookVault.
4. In a terminal, `cd` into the folder and run `java -jar addressbook.jar`
5. Start typing commands like `help` or `add_user` to begin using the app.
6. Refer to the [Features](#features) section below for detailed usage.

---

## Features

<box type="info" seamless>

**Notes about the command format:**

* Words in `UPPER_CASE` are parameters provided by the user.
* Square brackets `[]` denote optional arguments.
* Commands are **case-insensitive**, but parameter values are **case-sensitive**.
* Parameters can appear in any order.

</box>

---

### View Help: `help`

Displays general help instructions.

Format: `help`

<img width="741" alt="Screenshot 2025-04-04 at 1 32 50 AM" src="https://github.com/user-attachments/assets/1829909d-d583-4550-8d50-e59572fcaab1" />
---

### Add a User: `add_user`

Adds a new user to the library.

Format:  
`add_user n/NAME p/PHONE a/ADDRESS e/EMAIL m/MEMBERSHIP_STATUS`

Example:  
`add_user n/Alex Tan p/91234567 a/123 Clementi Rd e/alex@example.com m/NON-MEMBER`
![image](https://github.com/user-attachments/assets/23a0f746-02a1-4fdb-9a22-f85ab838953a)

---

### Delete a User: `delete_user`

Deletes a user based on index.

Format:  
`delete_user number`

Example:  
`delete_user 1`
<img width="740" alt="Screenshot 2025-04-04 at 1 39 57 AM" src="https://github.com/user-attachments/assets/63f13c81-5024-4d2d-82f4-8c1e66c49e6d" />

---

### Add Book: `add_book`

Adds a new book to the catalog.

Format:  
`add_book b/BOOK_NAME`

Example:  
`add_book b/Wizard of Oz`
![image](https://github.com/user-attachments/assets/d9558a81-58e3-46e6-98c8-d56584806cfd)

---

### Delete Book: `delete_book`

Deletes a book from the catalog.

Format:  
`delete_book b/BOOK_NAME`

Example:  
`delete_book b/Wizard of Oz`
![image](https://github.com/user-attachments/assets/abc8ddac-e672-44dd-a789-aaa97c23415e)

---

### Issue Book to User: `issue`

Issues a book to a user on a specific date.

Format:  
`issue b/BOOK_NAME to e/EMAIL on d/ISSUE_DATE`

Example:  
`issue b/Percy Jackson to e/alex@example.com on d/20/02/2025`

---

### Return Book: `return`

Marks a borrowed book as returned.

Format:  
`return b/BOOK_NAME on d/RETURN_DATE`

Example:  
`return b/Percy Jackson on d/01/03/2025`

---

### Edit User: `edit`

Edits fields for a user identified by index.

Format:  
`edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [m/MEMBERSHIP_STATUS] [t/TAG]...`

---

### Find Users by Name: `find`

Searches users by partial/full name.

Format:  
`find KEYWORD [MORE_KEYWORDS]`

Example:  
`find John Jane`

---

### List Users: `list_users`

Lists all users or filters them based on:
- `n/NAME`
- `e/EMAIL`
- `m/MEMBERSHIP_STATUS`
- `b/BOOK_NAME`
- `f/MIN_OVERDUE_FEE`
- `t/TAG`

Examples:  
`list_users`  
`list_users m/ACTIVE`  
`list_users f/10`  
`list_users t/Overdue Fee`
![image](https://github.com/user-attachments/assets/d26e0825-6547-4930-8739-c4c470981a32)

---

### List Books: `list_books`

Displays all books in the catalog.

Format: `list_books`
![image](https://github.com/user-attachments/assets/9a23fb2e-67bc-47cf-82bc-7423453d7d0c)

---

### Display Overdue Books: `display_overdue`

Shows books that are overdue and the users who borrowed them.

Format:  
`display_overdue`

---

### Show User Fees: `show_fees`

Displays overdue fee summary for a user.

Format:  
`show_fees p/PHONE_NUMBER`

Example:  
`show_fees p/91234567`

---

### Clear All Entries: `clear`

Removes all users and books.

Format: `clear`

---

### Exit the App: `exit`

Closes the app.

Format: `exit`

---

### Saving and Editing Data

* All changes are **auto-saved** to `[jar folder]/data/addressbook.json`
* You may manually edit the JSON file (with caution).
* Invalid edits may cause the app to reset the file.

<box type="warning" seamless>
**Warning:** Always back up `addressbook.json` before editing it manually.
</box>

---

## Command Summary

| Action             | Format |
|--------------------|--------|
| Add User           | `add_user n/NAME p/PHONE a/ADDRESS e/EMAIL m/STATUS` |
| Delete User        | `delete_user e/EMAIL` |
| Edit User          | `edit INDEX [fields]` |
| Find User          | `find NAME` |
| List Users         | `list_users [filters]` |
| Show Fees          | `show_fees p/PHONE` |
| Add Book           | `add_book b/BOOK_NAME` |
| Delete Book        | `delete_book b/BOOK_NAME` |
| Issue Book         | `issue b/BOOK_NAME to e/EMAIL on d/DATE` |
| Return Book        | `return b/BOOK_NAME on d/DATE` |
| List Books         | `list_books` |
| Display Overdue    | `display_overdue` |
| Help               | `help` |
| Clear All          | `clear` |
| Exit               | `exit` |

---

## FAQ

**Q: How do I move my data to a new computer?**  
A: Copy the `addressbook.json` file from the current computer's data folder to the same location on the new computer.

---

## Known Issues

1. GUI might appear off-screen if used on multiple displays. Delete `preferences.json` to reset window position.
2. Help window may not reopen if minimized. Manually restore it.

---
