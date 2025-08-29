# Bajaj Finserv Health Qualifier - Spring Boot Application

## Overview
This Spring Boot application is designed for the Bajaj Finserv Health qualifier assessment. The application automatically executes a complete flow on startup:

1. **Webhook Generation**: Sends a POST request to generate a webhook
2. **SQL Problem Solving**: Solves a SQL problem based on registration number (odd/even logic)
3. **Solution Submission**: Submits the SQL solution using the generated webhook and JWT token

## Features
- **Automatic Execution**: Runs on application startup without requiring any controller endpoints
- **Dynamic Problem Assignment**: Automatically determines which SQL problem to solve based on registration number
- **Data Persistence**: Stores SQL solving results in H2 database
- **JWT Authentication**: Uses JWT tokens for secure API communication
- **Comprehensive Logging**: Detailed logging for debugging and monitoring

## Technology Stack
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **RestTemplate** for HTTP operations
- **JWT** for authentication

## Project Structure
```
src/main/java/com/bajaj/
├── Application.java              # Main Spring Boot application
├── config/
│   └── RestTemplateConfig.java  # RestTemplate configuration
├── dto/
│   ├── WebhookRequest.java      # Webhook generation request DTO
│   ├── WebhookResponse.java     # Webhook generation response DTO
│   └── SolutionRequest.java     # Solution submission DTO
├── entity/
│   └── SqlResult.java           # JPA entity for storing results
├── repository/
│   └── SqlResultRepository.java # JPA repository interface
└── service/
    ├── WebhookService.java      # Main orchestration service
    └── SqlProblemSolver.java    # SQL problem solving service
```

## Configuration
The application can be configured through `application.properties`:

```properties
# Webhook URLs
bajaj.webhook.generate.url=https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA
bajaj.webhook.submit.url=https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA

# Candidate Information
bajaj.candidate.name=John Doe
bajaj.candidate.regNo=REG12347
bajaj.candidate.email=john@example.com
```

## How It Works

### 1. Application Startup
When the application starts, the `ApplicationStartupRunner` component automatically triggers the complete flow.

### 2. Webhook Generation
- Sends POST request to the webhook generation endpoint
- Includes candidate name, registration number, and email
- Receives webhook URL and access token

### 3. Problem Assignment
- Extracts last two digits from registration number
- **Odd numbers**: Assigned Question 1
- **Even numbers**: Assigned Question 2

### 4. SQL Problem Solving
- Solves the assigned SQL problem
- Stores the result in the database
- Returns the SQL query solution

### 5. Solution Submission
- Uses the received webhook URL and JWT token
- Submits the SQL solution via POST request
- Includes the solution in the `finalQuery` field

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the Application
```bash
mvn clean package
```

### Run the Application
```bash
java -jar target/bajaj-finserv-health-1.0.0.jar
```

Or using Maven:
```bash
mvn spring-boot:run
```

## Database Access
The application uses H2 in-memory database. You can access the H2 console at:
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Logging
The application provides comprehensive logging at DEBUG level for:
- Webhook generation requests and responses
- SQL problem solving process
- Solution submission attempts
- Database operations

## Customization
To customize the SQL problems:
1. Modify the `solveQuestion1()` and `solveQuestion2()` methods in `SqlProblemSolver.java`
2. Update the problem descriptions as needed
3. Implement the actual SQL logic based on the provided problem statements

## Error Handling
The application includes robust error handling:
- HTTP request failures are logged and handled gracefully
- Database errors are caught and logged
- The application continues to run even if individual steps fail

## Security Notes
- JWT tokens are handled securely using Spring Security's `BearerAuth` header
- No sensitive information is logged
- Database credentials are configurable through properties

## Troubleshooting
1. **Webhook Generation Fails**: Check network connectivity and API endpoint availability
2. **Database Issues**: Verify H2 database configuration in properties
3. **JWT Token Issues**: Ensure the access token is properly formatted and valid
4. **Logging**: Check application logs for detailed error information

## Support
For issues or questions related to this application, please refer to the Bajaj Finserv Health qualifier documentation or contact the development team.
