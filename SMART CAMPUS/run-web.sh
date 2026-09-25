#!/usr/bin/env bash
# ==============================================================================
# KING'S AND QUEEN'S UNIVERSITY (KQU) - SMART CAMPUS MANAGEMENT SYSTEM
# Web Server Edition Runner (HTML, CSS, JavaScript, Java, and SQLite Database)
# ==============================================================================

set -e

echo "================================================================="
echo " KING'S AND QUEEN'S UNIVERSITY (KQU) - STARTING WEB APPLICATION"
echo " Stack: HTML5 + CSS3 + Vanilla JS + Java SE + SQLite Database"
echo "================================================================="

# Create output folder
mkdir -p bin

echo "[1/3] Compiling Java Backend & Web Server..."
javac -d bin $(find src/main/java -name "*.java")

# Copy web assets to bin so ClassLoader can find them
mkdir -p bin/web bin/database
cp -r src/main/resources/web/* bin/web/ 2>/dev/null || true
cp -r src/main/resources/database/* bin/database/ 2>/dev/null || true

echo "[2/3] Checking SQLite Driver..."
# If sqlite-jdbc jar is present in lib or maven repo, include it
CP="bin"
if [ -f "lib/sqlite-jdbc.jar" ]; then
    CP="bin:lib/sqlite-jdbc.jar"
fi

echo "[3/3] Launching Java Web Server on http://localhost:8080 ..."
java -cp "$CP" com.kqu.web.WebServer
