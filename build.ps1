Write-Host "Bajaj Finserv Health Qualifier - Build Script" -ForegroundColor Green
Write-Host "=============================================" -ForegroundColor Green
Write-Host ""

Write-Host "Checking for Java..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1
    Write-Host "Java found!" -ForegroundColor Green
    Write-Host $javaVersion[0] -ForegroundColor Cyan
} catch {
    Write-Host "ERROR: Java is not installed or not in PATH" -ForegroundColor Red
    Write-Host "Please install Java 17 or higher and add it to your PATH" -ForegroundColor Yellow
    Write-Host "Download from: https://adoptium.net/" -ForegroundColor Cyan
    Read-Host "Press Enter to continue"
    exit 1
}

Write-Host ""
Write-Host "Checking for Maven..." -ForegroundColor Yellow

try {
    $mvnVersion = mvn -version 2>&1
    Write-Host "Maven found!" -ForegroundColor Green
    Write-Host "Building project..." -ForegroundColor Yellow
    mvn clean package -DskipTests
} catch {
    Write-Host "Maven not found in PATH" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Attempting to use Maven wrapper..." -ForegroundColor Yellow
    
    if (Test-Path "mvnw.cmd") {
        Write-Host "Using Maven wrapper..." -ForegroundColor Green
        & .\mvnw.cmd clean package -DskipTests
    } else {
        Write-Host "Maven wrapper not found" -ForegroundColor Red
        Write-Host ""
        Write-Host "Please install Maven or download the Maven wrapper:" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "Option 1: Install Maven" -ForegroundColor Cyan
        Write-Host "- Download from: https://maven.apache.org/download.cgi" -ForegroundColor White
        Write-Host "- Extract to a directory and add bin folder to PATH" -ForegroundColor White
        Write-Host ""
        Write-Host "Option 2: Use Maven wrapper" -ForegroundColor Cyan
        Write-Host "- Download mvnw.cmd and mvnw from a Maven project" -ForegroundColor White
        Write-Host "- Place them in this directory" -ForegroundColor White
        Write-Host ""
        Read-Host "Press Enter to continue"
        exit 1
    }
}

Write-Host ""
Write-Host "Build completed!" -ForegroundColor Green
Write-Host "Check the target/ directory for the JAR file" -ForegroundColor Cyan
Read-Host "Press Enter to continue"
