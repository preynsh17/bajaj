package com.bajaj.service;

import com.bajaj.dto.SolutionRequest;
import com.bajaj.dto.WebhookRequest;
import com.bajaj.dto.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {
    
    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);
    
    @Value("${bajaj.webhook.generate.url:https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA}")
    private String generateWebhookUrl;
    
    @Value("${bajaj.webhook.submit.url:https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA}")
    private String submitSolutionUrl;
    
    @Value("${bajaj.candidate.name:John Doe}")
    private String candidateName;
    
    @Value("${bajaj.candidate.regNo:REG12347}")
    private String candidateRegNo;
    
    @Value("${bajaj.candidate.email:john@example.com}")
    private String candidateEmail;
    
    private final RestTemplate restTemplate;
    private final SqlProblemSolver sqlProblemSolver;
    
    public WebhookService(RestTemplate restTemplate, SqlProblemSolver sqlProblemSolver) {
        this.restTemplate = restTemplate;
        this.sqlProblemSolver = sqlProblemSolver;
    }
    
    public void executeCompleteFlow() {
        try {
            logger.info("Starting Bajaj Finserv Health qualifier process...");
            
            // Step 1: Generate webhook
            WebhookResponse webhookResponse = generateWebhook();
            if (webhookResponse == null) {
                logger.error("Failed to generate webhook. Exiting process.");
                return;
            }
            
            logger.info("Webhook generated successfully: {}", webhookResponse.getWebhook());
            
            // Step 2: Solve SQL problem based on registration number
            String sqlSolution = sqlProblemSolver.solveSqlProblem(candidateRegNo);
            logger.info("SQL problem solved. Solution: {}", sqlSolution);
            
            // Step 3: Submit solution using the webhook and JWT token
            boolean submissionSuccess = submitSolution(webhookResponse.getWebhook(), 
                                                   webhookResponse.getAccessToken(), 
                                                   sqlSolution);
            
            if (submissionSuccess) {
                logger.info("Solution submitted successfully!");
            } else {
                logger.error("Failed to submit solution.");
            }
            
        } catch (Exception e) {
            logger.error("Error during execution of complete flow", e);
        }
    }
    
    private WebhookResponse generateWebhook() {
        try {
            WebhookRequest request = new WebhookRequest(candidateName, candidateRegNo, candidateEmail);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
            
            logger.info("Sending webhook generation request to: {}", generateWebhookUrl);
            logger.info("Request payload: {}", request);
            
            ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
                generateWebhookUrl, entity, WebhookResponse.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.info("Webhook generation successful");
                return response.getBody();
            } else {
                logger.error("Webhook generation failed with status: {}", response.getStatusCode());
                return null;
            }
            
        } catch (Exception e) {
            logger.error("Error generating webhook", e);
            return null;
        }
    }
    
    private boolean submitSolution(String webhookUrl, String accessToken, String sqlSolution) {
        try {
            SolutionRequest request = new SolutionRequest(sqlSolution);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            
            HttpEntity<SolutionRequest> entity = new HttpEntity<>(request, headers);
            
            logger.info("Submitting solution to webhook: {}", webhookUrl);
            logger.info("Solution: {}", sqlSolution);
            
            ResponseEntity<String> response = restTemplate.postForEntity(
                submitSolutionUrl, entity, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Solution submission successful");
                return true;
            } else {
                logger.error("Solution submission failed with status: {}", response.getStatusCode());
                return false;
            }
            
        } catch (Exception e) {
            logger.error("Error submitting solution", e);
            return false;
        }
    }
}
