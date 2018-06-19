## Exam - Project Schedule Plan
### I. Instructions
1. To clone repository, you can use the ff. git command in CMD:
```
git clone https://github.com/lagulamarc/project-planner
```

   or simply download the project as ZIP in the project home page (**Clone or Download** button) to your desktop folder

2. Once repository is cloned, import projects in IDE as Maven projects(as code base was developed using Maven project structure). Projects under repository are as follows:
   - project-entities
   - project-services
   - project-console-app
   
3. Run `build-project-planner.bat` to build and install the dependencies on your local repository (to avoid issue in running ConsoleApp). This will also run the automated tests added as part of validation of functionalities of services. Press ENTER key once done.

4. Run `run-console-app.bat` to build and execute ConsoleApp class (will display Project Start/End Date and Total Duration, Task Start/End Date and Total Duration, and SubTasks Start/End Dates and Total Duration per subtasks. Press ENTER key once done.

### II. Assumptions and Considerations
1. Subtasks can no longer have any more subtasks.
2. _Start Date_, _End Date_, and _Project/Task/Subtask Duration_ are calculated/displayed upto **DAYS** only. Hours were not considered in calculating differences in Start and End dates.
3. In ConsoleApp display, Task Start Date displayed is **NOT** the Task start date itself but the earliest Subtask start date. It makes sense to assume that a Task is considered as **started** once a subtask under the Task has been started. Unless the Task has no subtask, then the displayed start date **IS** the task's start date itself. (This is backed by a test scenario in the services project)
4. In ConsoleApp display, Task End Date displayed **MAY NOT** be the Task end date itself but the last end date of the subtasks plus the duration of the general task itself. There can be cases where the final _End Date_ when all subtasks are finished overlaps the _Start Date_ of the general Task. Overlapping was also taken into consideration in calculating _Start_ and _End_ dates of subtasks. Unless the Task has no subtask, then the displayed end date **IS** the task's end date itself. (This is backed by a test scenario in the services project)
5. Idle time was considered in calculating total Project/Task duration. (_e.g._ When a subtask ends on March 1, and the general Task starts on May 1, the days between March 1 and May 1 was included in the calculation of the Task total duration. The assumption was the general Task will not be started even though its subtask is already finished since its own start date is still on a later date than the subtask's end date)
6. With **#3,#4, #5** said, Task total duration calculation is the difference between the earliest Task's subtask start date (OR task start date if no subtasks) and the Task final end date (final subtask end date plus duration of Task itself OR task end date if no subtasks) in **DAYS**.
7. Project **ALWAYS** has a task. Task _CAN HAVE NO SUBTASKS_
8. Project Start Date displayed is the earliest Start Date amongst Tasks
9. Project End Date displayed is the latest End Date amongst Tasks
10. Project Total Duration is the difference between project Start and End date in **DAYS**.


### III. Technologies Used
1. Java SE 1.8
2. Maven
3. GitHub
4. JUnit

Should you have any concern, feel free to contact me thru: lagulamarc@gmail.com
