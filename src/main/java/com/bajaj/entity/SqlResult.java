package com.bajaj.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sql_results")
public class SqlResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "reg_no")
    private String regNo;
    
    @Column(name = "problem_description")
    private String problemDescription;
    
    @Column(name = "sql_query", columnDefinition = "TEXT")
    private String sqlQuery;
    
    @Column(name = "last_two_digits")
    private String lastTwoDigits;
    
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;
    
    public SqlResult() {
        this.createdAt = java.time.LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRegNo() {
        return regNo;
    }
    
    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
    
    public String getProblemDescription() {
        return problemDescription;
    }
    
    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }
    
    public String getSqlQuery() {
        return sqlQuery;
    }
    
    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }
    
    public String getLastTwoDigits() {
        return lastTwoDigits;
    }
    
    public void setLastTwoDigits(String lastTwoDigits) {
        this.lastTwoDigits = lastTwoDigits;
    }
    
    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
