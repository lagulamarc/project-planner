:: build entities module
cd project-entities
call mvn clean install

:: build services module and run tests
cd..
cd project-services
call mvn clean install

PAUSE