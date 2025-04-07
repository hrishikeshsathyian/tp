---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# HappyEverAfter Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S2-CS2103T-W09-4/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S2-CS2103T-W09-4/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S2-CS2103T-W09-4/tp/tree/master/src/main/java/seedu/address/ui)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S2-CS2103T-W09-4/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S2-CS2103T-W09-4/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `WeddingModel` data so that the UI can be updated with the modified data.
- In particular, it listens for changes to the `UniqueWeddingList`, as well as the `UniquePersonList` of
    the currently open wedding.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S2-CS2103T-W09-4/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `WeddingPlannerParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a wedding).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `WeddingPlannerParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddWeddingCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddWeddingCommand`) which the `WeddingPlannerParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddWeddingCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/AY2425S2-CS2103T-W09-4/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

- stores the wedding planner data i.e., all `Wedding` objects (which are contained in a `UniqueWeddingList` object) and all `Person` objects (which are contained in each `Wedding` object's `UniquePersonList`)
- exposes the data to the outside as `ReadOnlyWeddingPlanner` objects that can be 'observed' e.g. the UI can be bound to this list so that whe each wedding is selected, the UI updates to show the selected Wedding's data i.e., all the `Person` objects within it
- stores the currently 'selected' `Wedding` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Wedding>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

#### Key Model Classes

- **WeddingPlanner**: The central data structure that holds all weddings
- **Wedding**: Represents a wedding event with the following properties:
  - `date`: The scheduled date for the wedding
  - `title`: The name/title of the wedding
  - `bride`: A reference to a Person representing the bride
  - `groom`: A reference to a Person representing the groom
  - `members`: A UniquePersonList of other people involved in the wedding
- **WeddingModel**: Extends the base Model interface with wedding-specific operations
- **WeddingModelManager**: The implementation of the WeddingModel interface that:
  - Manages the current wedding context (the active wedding being edited)
  - Handles draft wedding creation and commitment
  - Provides operations for adding, editing, and removing people from weddings

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `WeddingPlanner`, which `Person` references. This allows `WeddingPlanner` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S2-CS2103T-W09-4/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

- can save both wedding planner data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `WeddingPlannerStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

<!-----

 ## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

- `VersionedAddressBook#commit()` — Saves the current address book state in its history.
- `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
- `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations

**Aspect: How undo & redo executes:**

- **Alternative 1 (current choice):** Saves the entire address book.

  - Pros: Easy to implement.
  - Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Individual command knows how to undo/redo by
    itself.
  - Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  - Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_ -->

<!-- ### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_ -->

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- has a need to manage a significant number of contacts over a variety of different weddings
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**: manage wedding tasks faster and clearer than a typical mouse/GUI driven app

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                      | So that I can…​                                                           |
| -------- | ------------------------------------------ | ------------------------------------------------- | ------------------------------------------------------------------------- |
| `* * *`  | new user                                   | see usage instructions                            | refer to instructions when I forget how to use the App                    |
| `* * *`  | user                                       | create a new wedding folder with a unique name    | organize wedding details separately                                       |
| `* * *`  | user                                       | delete a wedding    | remove unwanted weddings                                       |
| `* * *`  | user                                       | open a wedding                        | manage its details                                                        |
| `* * *`  | user                                       | close an open wedding                             | open a different wedding                                                  |
| `* * *`  | user                                       | sort weddings by date                             | easily view upcoming weddings in chronological order and plan accordingly |
| `* * *`  | user                                       | add a new person’s contact details to a wedding   | track attendees and their information                                     |
| `* *`    | user                                       | find a person by name                             | quickly locate their details                                              |
| `*`      | user                                       | search using partial name matching                | find people even if I don’t remember their full name                      |
| `* *`    | user                                       | filter search results by guests, staff, or couple | narrow down results                                                       |
| `* * *`  | user                                       | delete a person from a wedding                    | remove incorrect or outdated entries                                      |
| `*`      | user                                       | be asked for confirmation before deletion         | avoid deleting someone by mistake                                         |
| `* *`    | user                                       | have my wedding data saved automatically          | avoid losing my progress                                                  |
| `* * *`  | user                                       | retrieve my saved data when restarting the app    | continue managing weddings from where I left off                          |
| `* * *`  | user                                       | have data persist even after closing the app      | ensure my information remains intact                                      |

### Use cases

(For all use cases below, the **System** is the `HappyEverAfter` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Creating a new wedding**

**MSS**

1. User creates a new wedding
2. HappyEverAfter provides confirmation that the wedding has been created

    Use case ends.

**Extensions**

- 1a. There exists a wedding with the same name.

    1ai.HappyEverAfter shows an error message.

    Use case resumes at step 1.

    Use case ends.

- 1b. Wedding name provided is in an invalid format.

    1bi. HappyEverAfter shows an error message.

    Use case resumes at step 2.

**Use case: Adding a person to a wedding**

**MSS**

1. User requests to list weddings
2. HappyEverAfter shows a list of weddings
3. User opens the wedding they want
4. HappyEverAfter provides confirmation that the wedding has been opened
5. User adds contact information of person
6. HappyEverAfter acts the person to that wedding.

    Use case ends.

**Extensions**

- 2a. The list is empty.

    Use case ends.

- 5a. Contact information provided is in an invalid format.

    5ai. HappyEverAfter shows an error message.

    Use case resumes at step 2.

**Use case: Delete a person from a wedding**

**MSS**

1. User requests to list weddings
2. HappyEverAfter shows a list of weddings
3. User opens the wedding they want
4. HappyEverAfter provides confirmation that the wedding has been opened
5. User requests to list persons associated with that wedding
6. HappyEverAfter shows a list of persons
7. User requests to delete a specific person in the list
8. HappyEverAfter deletes the person

    Use case ends.

**Extensions**

- 2a. The list is empty.

    Use case ends.

- 5a. The list is empty.

    Use case ends.

- 7a. The given index is invalid.

  - 7a1. HappyEverAfter shows an error message.

        Use case resumes at step 6.

_{More to be added}_

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 weddings without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Data loss should not occur even in the event of an unexpected shutdown (e.g., GUI crash).
5. The system should be modular, allowing for easy addition or removal of features without affecting existing functionality.
6. All data should be stored locally on the user's device, and users should be able to access and modify their data without any delays caused by network connectivity.
7. The system should perform input validation and generate a response within 500 milliseconds for each user input to ensure a smooth and responsive user experience.


### Glossary

- **Mainstream OS**: Windows, Linux, Unix, MacOS
- **Client**: A couple or individual using the wedding planner’s services to organize their wedding
- **Vendor**: A service provider (e.g., caterers, florists, photographers) who collaborates with the wedding planner
- **Invitation**: A digital or physical wedding invitation managed and tracked within the system
- **Event Timeline**: A structured schedule outlining key wedding milestones and deadlines
- **Vendor Coordination**: The process of managing communication and contracts with wedding vendors
- **Reminder Notifications**: Automated alerts to keep planners on schedule with tasks
- **Error Message**: A message displayed when an invalid operation occurs

---

## **Appendix: Instructions for Manual Testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions provide a starting point for testers; testers are expected to perform additional _exploratory_ testing.

</box>

### Launch and Shutdown

1. **Initial Launch**

    1. **Prerequisites**: Ensure that Java 17 or above is installed on your computer.

    2. **Test Case**: Download the `happyeverafter.jar` file and place it in an empty folder.

        - **Execution**: Double-click the `happyeverafter.jar` file.

        - **Expected Outcome**: The GUI launches displaying a set of sample weddings. The window size may not be optimal.

2. **Saving Window Preferences**

    1. **Test Case**: Resize the application window to an optimal size and move it to a preferred location. Close the application.

        - **Execution**: Re-launch the application by double-clicking the `happyeverafter.jar` file.

        - **Expected Outcome**: The application window retains the most recent size and location.

3. **Exiting the Application**

    1. **Test Case**: Click the close button on the application window or enter the `exit` command in the command box.

        - **Expected Outcome**: The application shuts down gracefully without any error messages.

### Adding a Wedding

1. **Adding a Wedding to HappyEverAfter**

    1. **Prerequisites**: The application must be running.

    2. **Test Case**: Enter the command `new n/John & Mary d/09092027`.

        - **Expected Outcome**: A new wedding named "John & Mary" with the date 09 September 2027 is created. The application prompts for the contact details of the bride and groom.

    3. **Test Case**: Enter the command `new n/John & Mary`.

        - **Expected Outcome**: No wedding is created. An error message is displayed indicating the missing date parameter.

    4. **Test Case**: Enter the command `new n/John & Mary d/31-02-2027`.

        - **Expected Outcome**: No wedding is created. An error message is displayed indicating the invalid date format.

    5. **Other Incorrect `new` Commands to Try**:

        - `new`
        - `new n/John & Mary d/`
        - `new d/09092027`

        - **Expected Outcome**: Similar to previous cases, no wedding is created, and appropriate error messages are displayed.

### Managing Weddings

1. **Listing All Weddings**

    1. **Prerequisites**: At least one wedding has been added to the application.

    2. **Test Case**: Enter the command `list`.

        - **Expected Outcome**: A list of all weddings is displayed, showing their names and dates.

2. **Sorting Weddings by Date**

    1. **Prerequisites**: Multiple weddings with different dates have been added.

    2. **Test Case**: Enter the command `sort`.

        - **Expected Outcome**: The list of weddings is sorted in ascending order by date.

3. **Deleting a Wedding**

    1. **Prerequisites**: At least one wedding exists in the application.

    2. **Test Case**: Enter the command `delete 1`.

        - **Expected Outcome**: The first wedding in the list is deleted. The list updates to reflect the change.

    3. **Test Case**: Enter the command `delete 0` or `delete` with an invalid index.

        - **Expected Outcome**: No wedding is deleted. An error message is displayed indicating the invalid index.

4. **Deleting All Weddings**

    1. **Prerequisites**: Multiple weddings exist in the application.

    2. **Test Case**: Enter the command `clear`.

        - **Expected Outcome**: All weddings are deleted from the application. The list becomes empty.

### Managing Members

1. **Adding a Person to a Wedding**

    1. **Prerequisites**: At least one wedding exists. The wedding is currently open in the application.

    2. **Test Case**: Enter the command `add n/Emily Tan p/91234567 e/emily@example.com r/Bride`.

        - **Expected Outcome**: A person named "Emily Tan" with the specified contact details and role "Bride" is added to the current wedding.

    3. **Test Case**: Enter the command `add n/Emily Tan p/91234567 e/emily@example.com`.

        - **Expected Outcome**: No person is added. An error message is displayed indicating the missing role parameter.

    4. **Other Incorrect `add` Commands to Try**:

        - `add n/Emily Tan e/emily@example.com r/Bride`
        - `add p/91234567 e/emily@example.com r/Bride`
        - `add n/Emily Tan p/91234567 r/Bride`

        - **Expected Outcome**: Similar to previous cases, no person is added, and appropriate error messages are displayed.

2. **Searching for Members of Weddings**

    1. **Prerequisites**: Multiple members have been added to the current wedding.

    2. **Test Case**: Enter the command `find Emily`.

        - **Expected Outcome**: A list of members whose names contain "Emily" is displayed.

    3. **Test Case**: Enter the command `find`.

        - **Expected Outcome**: No members are displayed. An error message is shown indicating the missing search keyword.

3. **Filtering Members by Tags**

    1. **Prerequisites**: Members have been tagged with specific labels (e.g., "family", "friend").

    2. **Test Case**: Enter the command `filter t/family`.

        - **Expected Outcome**: A list of members tagged with "family" is displayed.

    3. **Test Case**: Enter the command `filter t/unknown`.

        - **Expected Outcome**: No members are displayed. A message indicates that no members have the specified tag.

4. **Editing a Person**

    1. **Prerequisites**: At least one member exists in the current wedding.

    2. **Test Case**: Enter the command `edit 1 p/98765432`.

        - **Expected Outcome**: The phone number of the first member in the list is updated to "98765432".

    3. **Test Case**: Enter the command `edit 1`.

        - **Expected Outcome**: No changes are made. An error message is displayed indicating the missing fields to edit.

5. **Removing a Person**

    1. **Prerequisites**: At least one member exists in the current wedding.

    2. **Test Case**: Enter the command `remove 1`.

        - **Expected Outcome**: The first member in the list is removed from the wedding.

    3. **Test Case**: Enter the command `remove 0` or `remove` with an invalid index.

        - **Expected Outcome**: No member is removed and an appropriate error message is displayed.
 
### Saving Data

1. **Dealing with Missing/Corrupted Data Files**

    1. **Simulating a Missing Data File**:
    
        - **Execution**: Navigate to the application's data directory and delete the data file (i.e. data/weddingplanner.json).
        
        - **Test Case**: Launch the application after deleting the data file.
        
            - **Expected Outcome**: The application starts with an sample list of weddings. A new data file by the same name is created upon exiting the application.

    2. **Simulating a Corrupted Data File**:
    
        - **Execution**: Open the data file in a text editor and intentionally break its format (e.g., delete a brace, alter tags, insert random characters).
        
        - **Test Case**: Launch the application after corrupting the file.
        
            - **Expected Outcome**: The application detects the corrupted file and logs that as the datafile could not be loaded, a new and empty file has been created. 
            - User can then manually fix the crropted file or input their wedding details from scratch.

2. **Verifying Data Persistence After Application Restart**

    - **Prerequisites**: The application is running and at least one wedding has been added.

    - **Test Case**: Close the application and re-launch it.

        - **Expected Outcome**: All previously entered data (weddings, people, preferences) are correctly loaded and displayed.
