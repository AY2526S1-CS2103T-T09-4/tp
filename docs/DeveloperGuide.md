---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.
--------------------------------------------------------------------------------------------------------------------

## **Planned Enhancements** 

Team size: 5
1. Prevent GUI from launching off-screen: The application currently relies on external workarounds (deleting a JSON file) when its main GUI window launches off-screen after a multi-monitor configuration change. We plan to enhance the window initialization logic to detect if the saved window position is outside the boundaries of the currently available screen(s). If an off-screen position is detected, the application will automatically reset the window position to the center of the primary screen upon launch. This prevents the user from having to manually delete the preferences file.
2. Restore minimized help window upon subsequent `help` command: If the existing Help Window is minimized, running the help command currently fails to bring it to the foreground. We will tweak the help command's execution so that before attempting to open a new window, it first checks if an existing Help Window is minimized. If so, it will automatically restore and focus the original window, ensuring the user can immediately access the help content without manual intervention.
3. Allow unselection of a contact card: Once a contact card is selected in the list, the current behavior does not provide a mechanism to unselect it, leaving the UI state potentially stale. We will enhance the contact list's selection behavior by allowing a contact card to be unselected. Specifically, clicking on the currently selected contact card a second time will toggle its selection state, causing it to become unselected and returning the list to its initial, unselected state.
4. Expand name field to support special characters: The current name validation rejects special symbols (e.g., `/`) leading to workarounds like spelling out names. We plan to tweak the name validation logic to allow a predefined set of special characters (such as hyphens, apostrophes, and slashes) within the name field to better accommodate official names and job titles. For example, instead of rejecting 's/o', the application will accept it by updating the allowed character set in the name validation rules.
5. Allow single-word aliases for common commands: BrewBook currently requires full command words like `list all` or `sort name`. We will tweak the command parsing to allow the use of single-letter or short aliases for the most frequent commands to improve CLI speed. For example: `ls all` will be an accepted alias for `list all`.
6. Improve input feedback for partially correct commands: When a user is typing a command and pauses, the system can be more helpful. We will tweak the command result to provide immediate, context-sensitive suggestions or reminders based on the partially entered command. For instance, if the user types add customer `n/`, a small hint might appear: (Required: `p/`, `e/`, `a/`).
7. Enhance help to provide command-specific guidance: The current help command opens a windows which link to the user guide. We will tweak the help command parser to accept an optional command name. For example, typing `help list` will display a concise summary of the `list` command's format, parameters and examples directly in the command result box, providing faster, targeted help without external resources.
8. Highlight matching keywords in find results: After a find command, the GUI displays the list of matching contacts, but it does not show why they matched. We will tweak the contact card display so that when a list is populated from a find result, the matching keyword (e.g., "Lee") is highlighted on the contact card wherever it was found (in the name, notes, etc.), providing immediate visual feedback on search relevance.
9. Enhance edit command to support additive/subtractive list updates: The current edit command overwrites all data for list-based fields. For example, editing shifts for a staff member who already has shifts scheduled will delete all previous shifts and replace them with the new input. We will tweak the edit command's syntax to allow for adding or removing specific items from lists like shifts, items, or tags. For example, `edit 1 shifts/add(2026-12-10)` will add the new shift without deleting existing ones, and `edit 1 shifts/remove(2026-12-04)` will remove only that specific shift.
10. Tweak `list` command to default to `list all`: The current list command requires a specific parameter (e.g., `list all`, `list customer`). If a user types just `list` with no arguments, it will result in an "Invalid command format" error. We will tweak the command parser to make the `list` command more intuitive. If the user types only `list`, BrewBook will automatically treat it as an alias for `list all`, immediately displaying all contacts instead of showing an error.

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:
Manager of a small cafe


**Value proposition**: BrewBook helps managers of small cafes to manage different stakeholders, including customers, suppliers and employees (full-time/part-time). It helps cafe managers coordinate with customers, suppliers, and staff by linking roles directly to contacts—so they don’t need to remember who does what.



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                | I want to …​                                                               | So that I can…​                          |
|----------|------------------------|----------------------------------------------------------------------------|------------------------------------------|
| `* * *`  | new user               | add a supplier with name, contact number, what and when they supply        | test adding suppliers                    |
| `* * *`  | new user               | add a staff with name, contact number, and when they're on shift           | test adding staff                        |
| `* * *`  | new user               | add a customer with name, contact number, and their current loyalty points | test adding customers                    |
| `* * *`  | new user               | view all contacts                                                          | get an overview of all my contacts       |
| `* * *`  | semi-experienced user  | delete individual contacts                                                 | remove unwanted contacts                 |
| `* * *`  | semi-experienced user  | view contacts of a certain category                                        | get an overview of individual categories |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `BrewBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Delete contact**

**Preconditions**: User is at the command prompt.

**Guarantee**: Current contact will be deleted.

**MSS**

1.  User requests to list contacts.
2.  BrewBook shows a list of contacts.
3.  User requests to delete a specific contact in the list.
4.  BrewBook deletes the person.
    Use case ends.

**Extensions**

* 2a. The list is empty.
  Use case ends
* 3a. The given index is invalid.
  * 3a1. BrewBook shows an error message and informs the user of the possible valid indices.
    Use case resumes at step 2.

**Use case: UC2 - Delete multiple contacts**

**Preconditions**: User is at the command prompt.

**Guarantee**: Selected contacts will be deleted.

**MSS**

1.  User requests to list persons.
2.  BrewBook shows a list of persons.
3.  User requests to delete multiple contacts in the list.
4.  BrewBook deletes the contacts.
    Use case ends.

**Extensions**

* 2a. The list is empty.
  Use case ends
* 3a. The given index range is invalid.
    * 3a1. BrewBook shows an error message and informs the user of the possible valid indices.
      Use case resumes at step 2.

**Use case: UC3 - Edit contact**

**Preconditions**: User is at the command prompt.

**Guarantee**: Current contact will be edited.

**MSS**

1.  User requests to list persons.
2.  BrewBook shows a list of persons.
3.  User requests to edit a specific person in the list.
4.  BrewBook edits the person.
    Use case ends.

**Extensions**

* 2a. The list is empty.
  Use case ends
* 3a. The given index is invalid.
    * 3a1. BrewBook shows an error message and informs the user of the possible valid indices.
      Use case resumes at step 2.
* 3b. The given format is invalid.
    * 3b1. BrewBook shows an error message and informs the user of the correct format.
      Use case resumes at step 2.

**Use case: UC4 - Set reminder to contact supplier**

**Preconditions**: User is at the command prompt.

**Guarantee**: Reminder to contact supplier will be set.

**MSS**

1.  User requests to list suppliers.
2.  BrewBook shows a list of suppliers.
3.  User requests to add a reminder for a specific supplier in the list.
4.  BrewBook adds the reminder.
    Use case ends.

**Extensions**

* 2a. The list is empty.
  Use case ends
* 3a1. The given index is invalid.
    * 3a1. BrewBook shows an error message and informs the user of the possible valid indices.
      Use case resumes at step 2.
* 3b. The given format is invalid.
    * 3b1. BrewBook shows an error message and informs the user of the correct format.
      Use case resumes at step 2.
* 3c. The given date is invalid.
    * 3c1. BrewBook shows an error message and informs the user of the correct date format.
      Use case resumes at step 2.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should work on both 32-bit and 64-bit environments.
5.  A novice user with minimal technical knowledge should be able to use the core features without requiring external guidance.
6.  All commands must provide usage examples to aid discovery and learning.
7.  Search/filter results should be returned within 500ms.
8.  Adding or editing a contact should complete within 2 seconds, including saving to local.
9.  The app should not lose data under sudden app closure or power loss.
10. Development should adhere to the project schedule, ensuring all milestone requirements are met before their deadlines.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Customer**: A contact who interacts with the café as a buyer or loyalty program member.
* **Supplier**: A contact who provides goods or services to the café (e.g., milk supplier, coffee bean supplier).
* **Staff**: A contact employed by the café, either full-time or part-time.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
