# Build Instructions for Bajaj Finserv Health Qualifier

## Prerequisites

### 1. Install Java 17 or Higher
- Download from: https://adoptium.net/
- Install and add to your system PATH
- Verify installation: `java -version`

### 2. Install Maven 3.6 or Higher
- Download from: https://maven.apache.org/download.cgi
- Extract to a directory (e.g., `C:\Program Files\Apache\maven`)
- Add the `bin` folder to your system PATH
- Verify installation: `mvn -version`

## Building the Project

### Option 1: Using Maven Command Line
```bash
# Navigate to project directory
cd bajaj

# Clean and package
mvn clean package -DskipTests

# The JAR file will be created in target/ directory
# File: target/bajaj-finserv-health-1.0.0.jar
```

### Option 2: Using Build Scripts
- **Windows**: Double-click `build.bat` or run `.\build.bat` in PowerShell
- **PowerShell**: Run `.\build.ps1` in PowerShell

### Option 3: Using IDE
- Open the project in IntelliJ IDEA, Eclipse, or VS Code
- Use the built-in Maven integration to build

## Running the Application

### Option 1: Using Java Command
```bash
java -jar target/bajaj-finserv-health-1.0.0.jar
```

### Option 2: Using Maven
```bash
mvn spring-boot:run
```

## Project Structure After Build
```
bajaj/
├── src/                           # Source code
├── target/                        # Build output
│   ├── classes/                   # Compiled classes
│   ├── generated-sources/         # Generated sources
│   ├── maven-archiver/           # Maven metadata
│   ├── maven-status/             # Build status
│   └── bajaj-finserv-health-1.0.0.jar  # Final JAR file
├── pom.xml                       # Maven configuration
├── README.md                     # Project documentation
├── BUILD_INSTRUCTIONS.md         # This file
├── build.bat                     # Windows build script
└── build.ps1                     # PowerShell build script
```

## Troubleshooting

### Common Issues

1. **"mvn is not recognized"**
   - Maven is not in your PATH
   - Reinstall Maven and ensure bin folder is in PATH

2. **"java is not recognized"**
   - Java is not in your PATH
   - Reinstall Java and ensure it's in PATH

3. **Build fails with compilation errors**
   - Ensure you have Java 17 or higher
   - Check that all dependencies are available

4. **Permission denied errors**
   - Run as Administrator (Windows)
   - Check file permissions

### Verification Steps

1. **Check Java Version**
   ```bash
   java -version
   # Should show Java 17 or higher
   ```

2. **Check Maven Version**
   ```bash
   mvn -version
   # Should show Maven 3.6 or higher
   ```

3. **Check Project Structure**
   ```bash
   dir
   # Should show pom.xml and src/ directory
   ```

4. **Verify Dependencies**
   ```bash
   mvn dependency:tree
   # Should show all required dependencies
   ```

## Next Steps

After successful build:
1. The JAR file will be in `target/` directory
2. You can run the application using `java -jar`
3. The application will automatically execute the Bajaj Finserv Health qualifier process
4. Check logs for execution details

## Support

If you encounter issues:
1. Check the troubleshooting section above
2. Verify all prerequisites are met
3. Check the application logs for detailed error information
4. Ensure network connectivity for webhook API calls
