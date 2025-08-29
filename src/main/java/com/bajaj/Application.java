package com.bajaj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Component
    public static class ApplicationStartupRunner {
        
        private final WebhookService webhookService;
        
        public ApplicationStartupRunner(WebhookService webhookService) {
            this.webhookService = webhookService;
        }
        
        @EventListener(ApplicationReadyEvent.class)
        public void runOnStartup() {
            System.out.println("Application started. Running webhook generation and SQL solving process...");
            webhookService.executeCompleteFlow();
        }
    }
}
