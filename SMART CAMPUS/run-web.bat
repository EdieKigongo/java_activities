@echo off
REM ==============================================================================
REM KING'S AND QUEEN'S UNIVERSITY (KQU) - SMART CAMPUS MANAGEMENT SYSTEM
REM Web Server Edition Runner (HTML, CSS, JavaScript, Java, and SQLite Database)
REM ==============================================================================

echo =================================================================
echo  KING'S AND QUEEN'S UNIVERSITY (KQU) - STARTING WEB APPLICATION
echo  Stack: HTML5 + CSS3 + Vanilla JS + Java SE + SQLite Database
echo =================================================================

if not exist bin mkdir bin
if not exist bin\web mkdir bin\web
if not exist bin\database mkdir bin\database

echo [1/3] Compiling Java Backend and Web Server...
javac -d bin src\main\java\com\kqu\*.java src\main\java\com\kqu\*\*.java

echo [2/3] Copying Web Assets...
xcopy /E /I /Y src\main\resources\web\* bin\web\ >nul 2>&1
xcopy /E /I /Y src\main\resources\database\* bin\database\ >nul 2>&1

echo [3/3] Launching Java Web Server on http://localhost:8080 ...
java -cp bin com.kqu.web.WebServer
pause
