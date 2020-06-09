package com.ocrapi.myapp;

import com.ocrapi.myapp.services.DomainSpecificContentService;
import com.ocrapi.myapp.services.ImageTagService;
import com.ocrapi.myapp.services.OcrService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Begin of function calls");
        DomainSpecificContentService.domainSpecificContentCalls();
        ImageTagService.tagImageCalls();
        System.out.println("End of function calls ");
    }

}
