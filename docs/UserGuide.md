---
  layout: default.md
  title: "HappyEverAfter User Guide"
  pageNav: 3
---

# HappyEverAfter User Guide


Welcome to HappyEverAfter - a quick, robust, and intuitive Wedding Planner designed to help wedding organisers keep track of their weddings. HappyEverAfter provides a plethora of features, such as the creation of weddings, and the adding of members with roles to each wedding. Wedding Planning can get hectic, especially for a busy planner like you, and with just some typing commands, HappyEverAfter can help you get organised and sorted in no time!


<!-- * Table of Contents -->
# Table of Contents
1. [Quick Start](#quick-start)
2. [Command Formats](#feature-details)
3. [Features](#features)
    - [Wedding Management](#wedding-management)
        - [Creating a new wedding](#creating-a-wedding-add)
        - [Opening a wedding](#opening-a-wedding--open)
        - [Closing a wedding](#closing-the-current-wedding-close)
        - [Listing all weddings](#listing-all-weddings-list)
        - [Sorting weddings by date](#sorting-weddings-by-date-sort)
        - [Deleting a wedding](#deleting-a-wedding-delete)
    - [Member Management](#member-management)
        - [Adding a person to a wedding](#adding-a-person-to-a-wedding-add)
        - [Searching for members of weddings](#finding-people-findperson)
        - [Filtering for members by tags](#filtering-by-tag-filter)
        - [Removing a person](#removing-a-person-remove)
        - [Editing a person](#editing-a-person-editperson)
    - [System Commands](#system-commands)
        - [Viewing help](#viewing-help--help)
        - [Exiting the program](#exiting-the-program-exit)
4. [Data Storage](#data-storage)
5. [FAQ](#faq)
7. [Command Summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   - [Java Installation](https://www.java.com/en/download/help/download_options.html)
   - **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
2. Download the latest version of HappyEverAfter
   - Click the following link [here](https://github.com/AY2425S2-CS2103T-W09-4/tp/releases) and download the file `happyeverafter.jar`.
3. Move the file to the folder
    - Copy the file to the folder you want to use as the _home folder_ for your HappyEverAfter.
4. Running HappyEverAfter
    - Open the "Command Prompt" (for Windows) or "Terminal" (for Mac/Linux).
    - Enter `cd` followed by the folder location where you saved the HappyEverAfter file. For example:
        - On Windows: `cd C:\Users\JohnDoe\Desktop\HappyEverAfter`
        - On Mac/Linux: `cd /Users/JohnDoe/Desktop/HappyEverAfter`
    - Then run this command to launch HappyEverAfter:
      `java -jar happyeverafter.jar`
   - A Graphical User Interface (GUI) similar to the image below should appear in a few seconds. Note how the app contains some sample data.

      ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it.
   - See [features](#features) for the comprehensive list of all possible commands to execute and [command summary](#command-summary) for a brief overview.



[Back to Table of Contents](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Feature-Details

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by you, the user.<br>
  e.g. in `new n/WEDDING_NAME`, you should replace `WEDDING_NAME` with the actual name, like `new n/John & Mary`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or simply as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be provided in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* The `DATE` Parameter has to be in the format `DDMMYYYY`
  * e.g `25122025` for the 25th December 2025

* The `EMAIL` Parameter has to be a valid email address
    * e.g `e\hrishikeshsathyian2002@gmail.com`
</box>

* Special role tags to identify the bride and groom:
    * Use `t/bride` to designate a person as the bride
    * Use `t/groom` to designate a person as the groom
    * A wedding must have both a bride and groom to be valid
  
[Back to Table of Contents](#table-of-contents)

# Features

## Wedding Management

### Listing all weddings: `list`

You can view a list of all weddings in the wedding planner.

Format: `list`

![sample result from the list command](images/listCommand.png)

### Creating a wedding: `add`

You can add a wedding to the wedding planner.

Format: `new n/WEDDING_NAME d/DATE`

Examples:
* `new n/John & Mary d/25122025`

<div markdown="block" class="alert alert-primary">
Note:

* HappyEverAfter will prompt you immediately to enter the details of the bride and the groom to confirm
  the wedding.
* See [Adding a person to a wedding](#adding-a-person-add) for more details
</div>

### Opening a wedding : `open`

You can open a wedding from the Wedding Planner to view its people and edit its people.

Format: `open INDEX`

Examples:
* `open 1` (Opens the first wedding in the list)
* `open 3` (Opens the third wedding in the list)

After a weddings is open, you will be able to see its members on the right:

![An open wedding](images/openWedding.png)

### Closing the current wedding: `close`

You can close the currently open wedding project to return to the main view.

Format: `close`

### Sorting weddings by date: `sort`

You can view the displayed weddings by chronological order - with the earliest wedding at the top.

Format: `sort`

### Deleting a wedding: `delete`

You can delete a wedding from the wedding planner based on the provided index.

Format: `delete INDEX`

Examples:
* `delete 1` (Deletes the first wedding in the list)
* `delete 3` (Deletes the third wedding in the list)

[Back to Table of Contents](#table-of-contents)

## Member Management

### Adding a person to a wedding: `add`

You can add a person to the **active** Wedding Planner.
You can use tags to specify if the person is a bride, groom, or other wedding party member.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="block" class="alert alert-primary">
Note:

* Active refers to the wedding associated with the last `open` or `new` command
* Each wedding must have exactly one bride and one groom.
* Use `t/bride` to designate a person as the bride.
* Use `t/groom` to designate a person as the groom.
* Other people added without these special tags will be considered as wedding party members.
* A person can have any number of tags (including 0)
</div>

Examples:
* `add n/Mary Muller p/98765432 e/mary@example.com a/123 Kentridgr St t/bride`
* `add n/John Danny p/89989788 e/john@example.com a/456 UTR Ave t/groom`
* `add n/Harry Kane p/13701978 e/kane@example.com a/789 NUS Rd t/bridesmaid`

### Finding people: `findperson`

You can view all weddings with people that match the provided search terms.
You can search for any number of search terms, and HappyEverAfter will list all weddings with members that
match any of the terms provided. 
Remember to use list command to view all the weddings.

Format: `findperson [SEARCH TERMS]`

Examples:
* `findperson Sun`
* `findperson Sun Hrishi`

### Filtering by tag: `filter`

You can filter the list of the currently [opened](#opening-a-wedding--open) wedding to display all members with
the specified tag.

_Note: The tags are case-sensitive and use filter alone to display all members_

Format: `filter [TAGS}`

Examples:
* `filter bride` (Displays only the bride)
* `filter groom` (Displays only the groom)
* `filter` (Displays all members without filtering)

### Removing a person: `remove`

You can remove a person from the currently active wedding based on the provided index.

Format: `remove INDEX`

Examples:
* `remove 3` (Removes the third person in the list)

<div markdown="block" class="alert alert-primary">
Note:

* You cannot remove a bride or groom from a wedding. Every wedding must maintain both roles.
* The index is based on the display order of people in the active wedding view.
</div>

### Editing a person: `editperson`

You can edit the details of a person in the currently active wedding.

Format: `editperson INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...`

Examples:
* `editperson 1 p/91234567 e/newemail@example.com` (Updates phone and email of the first person)
* `editperson 2 n/New Name a/New Address` (Updates name and address of the second person)

<div markdown="block" class="alert alert-primary">
Note:

* Active refers to the wedding associated with the last `open` or `new` command
* You cannot edit the bride/groom tags of a person.
* At least one field must be provided for editing.
</div>

[Back to Table of Contents](#table-of-contents)

## System commands

### Viewing help : `help`

You can view a message explaining how to access the User Guide.

![help message](images/helpMessage.png)

Format: `help`

### Exiting the program: `exit`

You can exit the HappyEverAfter application.

Format: `exit`

[Back to Table of Contents](#table-of-contents)

## Data Storage

### Saving the data

HappyEverAfter data is saved in the hard disk automatically after any command that changes the data. There
is no need to save manually.

### Editing the data file

HappyEverAfter data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`.
Advanced users
are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, HappyEverAfter will discard all data and start
with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause HappyEverAfter to behave in unexpected ways (e.g., if a value entered is
outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

[Back to Table of Contents](#table-of-contents)
## FAQ

**Q**: How do I transfer my wedding planner data to another computer?
**A**: Install HappyEverAfter on the other computer and replace the empty data file it creates with the file from your original installation (located at `/data/weddingplanner.json`).

**Q**: Can I add multiple weddings at once?
**A**: No, you need to create weddings one at a time using the `new` command. Alternatively, for advanced users: you may update the data file yourself. Look through the ["Editing the data file" section](#editing-the-data-file) for more support.

**Q**: What happens if I try to add a second bride or groom to a wedding?
**A**: The system will display an error message as the current implementation of HappyEverAfter supports only one bride and one groom per wedding.

**Q**: How many people can I add to a wedding?
**A**: As of the most recent version, you can add at most 100 members for one wedding.


--------------------------------------------------------------------------------------------------------------------
[Back to Table of Contents](#table-of-contents)

## Command summary

| Action                    | Format, Examples
|---------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
| **Help**                  | `help` |
| **Create Wedding**        | `new n/WEDDING_NAME d/DATE`<br>e.g., `new n/John & Mary d/25122025` |
| **Open Wedding**          | `open INDEX`<br>e.g., `open 2` |
| **Close Wedding**         | `close` |
| **List Weddings**         | `list` |
| **Sort Weddings by Date** | `sort` |
| **Delete Wedding**        | `delete INDEX`<br>e.g., `delete 2` |
| **Add Person**            | `add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]...`<br>e.g., `add n/Mary Smith p/98765432 e/mary@example.com a/123 Wedding St t/bride` |
| **Find Person**           | `findperson [SEARCH TERMS]`<br>e.g., `findperson John Doe` |
| **Filter Members by Tag** | `filter [TAG]`<br>e.g., `filter groom` |
| **Remove Person**         | `remove INDEX`<br>e.g., `remove 3` |
| **Edit Person**           | `editperson INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...`<br>e.g., `editperson 1 p/91234567 e/newemail@example.com` |
| **Exit**                  | `exit` |
