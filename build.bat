@echo off
echo Bajaj Finserv Health Qualifier - Build Script
echo =============================================

echo.
echo Checking for Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher and add it to your PATH
    echo Download from: https://adoptium.net/
    pause
    exit /b 1
)

echo Java found!
echo.

echo Checking for Maven...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven not found in PATH
    echo.
    echo Attempting to use Maven wrapper...
    if exist "mvnw.cmd" (
        echo Using Maven wrapper...
        call mvnw.cmd clean package -DskipTests
    ) else (
        echo Maven wrapper not found
        echo.
        echo Please install Maven or download the Maven wrapper
        echo.
        echo Option 1: Install Maven
        echo - Download from: https://maven.apache.org/download.cgi
        echo - Extract to a directory and add bin folder to PATH
        echo.
        echo Option 2: Use Maven wrapper
        echo - Download mvnw.cmd and mvnw from a Maven project
        echo - Place them in this directory
        echo.
        pause
        exit /b 1
    )
) else (
    echo Maven found!
    echo Building project...
    mvn clean package -DskipTests
)

echo.
echo Build completed!
echo Check the target/ directory for the JAR file
pause
