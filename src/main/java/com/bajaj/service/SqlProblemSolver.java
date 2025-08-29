package com.bajaj.service;

import com.bajaj.entity.SqlResult;
import com.bajaj.repository.SqlResultRepository;
import org.springframework.stereotype.Service;

@Service
public class SqlProblemSolver {
    
    private final SqlResultRepository sqlResultRepository;
    
    public SqlProblemSolver(SqlResultRepository sqlResultRepository) {
        this.sqlResultRepository = sqlResultRepository;
    }
    
    public String solveSqlProblem(String regNo) {
        // Extract last two digits from registration number
        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int lastTwoDigitsInt = Integer.parseInt(lastTwoDigits);
        
        String sqlQuery;
        String problemDescription;
        
        if (lastTwoDigitsInt % 2 == 0) {
            // Even - Question 2
            sqlQuery = solveQuestion2();
            problemDescription = "Question 2 - Even registration number";
        } else {
            // Odd - Question 1
            sqlQuery = solveQuestion1();
            problemDescription = "Question 1 - Find employee with highest salary excluding 1st of month payments";
        }
        
        // Store the result
        SqlResult result = new SqlResult();
        result.setRegNo(regNo);
        result.setProblemDescription(problemDescription);
        result.setSqlQuery(sqlQuery);
        result.setLastTwoDigits(lastTwoDigits);
        
        sqlResultRepository.save(result);
        
        return sqlQuery;
    }
    
    private String solveQuestion1() {
        // Question 1 solution - Find employee with highest salary excluding 1st of month payments
        return "WITH PaymentDetails AS (" +
               "    SELECT" +
               "        p.AMOUNT AS SALARY," +
               "        e.FIRST_NAME," +
               "        e.LAST_NAME," +
               "        e.DOB," +
               "        d.DEPARTMENT_NAME," +
               "        RANK() OVER (ORDER BY p.AMOUNT DESC) as salary_rank" +
               "    FROM" +
               "        PAYMENTS p" +
               "    JOIN" +
               "        EMPLOYEE e ON p.EMP_ID = e.EMP_ID" +
               "    JOIN" +
               "        DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID" +
               "    WHERE" +
               "        EXTRACT(DAY FROM p.PAYMENT_TIME) != 1" +
               ")" +
               "SELECT" +
               "    SALARY," +
               "    CONCAT(FIRST_NAME, ' ', LAST_NAME) AS NAME," +
               "    TIMESTAMPDIFF(YEAR, DOB, '2025-08-29') AS AGE," +
               "    DEPARTMENT_NAME" +
               "FROM" +
               "    PaymentDetails" +
               "WHERE" +
               "    salary_rank = 1;";
    }
    
    private String solveQuestion2() {
        // Question 2 solution - This would be based on the actual problem
        // For now, providing a sample SQL query
        return "SELECT department, AVG(salary) as avg_salary FROM employees GROUP BY department HAVING AVG(salary) > 60000";
    }
}
