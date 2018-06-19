:: Display console app
:: Project and Task/Subtask properties are inside ConsoleApp.class
:: All property values were provided
cd project-console-app
call mvn clean install
call mvn exec:java -Dexec.mainClass=com.vlocity.exam.console.app.ConsoleApp

PAUSE